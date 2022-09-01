package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.RefreshToken;
import by.urbel.finaltask.domain.user.ERole;
import by.urbel.finaltask.domain.user.Role;
import by.urbel.finaltask.domain.user.User;
import by.urbel.finaltask.dto.requests.SignInRequest;
import by.urbel.finaltask.dto.requests.SignUpRequest;
import by.urbel.finaltask.dto.response.JwtResponse;
import by.urbel.finaltask.exception.EmailAlreadyExistsException;
import by.urbel.finaltask.exception.UsernameAlreadyExistsException;
import by.urbel.finaltask.mapper.UserMapper;
import by.urbel.finaltask.repository.UserRepository;
import by.urbel.finaltask.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.refresh.token.expired}")
    private Long refreshTokenDurationMs;

    public JwtResponse login(SignInRequest signInRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        User user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(authentication.getName());
        setRefreshTokenToCookies(refreshToken.getToken(),response);
        return new JwtResponse(jwt, refreshToken.getToken(), signInRequest.getUsername());
    }

    public void register(SignUpRequest signUpRequest) {
        Role roleUser = roleService.saveRoleByName(ERole.ROLE_USER);
        User user = userMapper.signUpRequestToUser(signUpRequest);
        checkExistence(user);
        user.getRoles().add(roleUser);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
    }

    private void checkExistence(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already used.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already used.");
        }
    }

    public JwtResponse updateAccessToken(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.verifyExpiration(refreshTokenEntity);
        String accessToken = jwtTokenUtil.generateAccessToken(refreshTokenEntity.getUser());
        return new JwtResponse(accessToken, refreshToken, refreshTokenEntity.getUser().getUsername());
    }

    public void setRefreshTokenToCookies(String refreshToken,HttpServletResponse response){
//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
//                .domain("localhost")
//                .path("/")
//                .maxAge(TimeUnit.MILLISECONDS.toSeconds(refreshTokenDurationMs))
//                .build();
        Cookie yourCookie = new Cookie("refreshToken", refreshToken);
        response.addCookie(yourCookie);

//        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }
}
