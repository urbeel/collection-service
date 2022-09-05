package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.RefreshToken;
import by.urbel.finaltask.domain.user.ERole;
import by.urbel.finaltask.domain.user.Role;
import by.urbel.finaltask.domain.user.User;
import by.urbel.finaltask.dto.requests.SignInRequest;
import by.urbel.finaltask.dto.requests.SignUpRequest;
import by.urbel.finaltask.dto.response.LoginResponse;
import by.urbel.finaltask.dto.response.UserResponse;
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
import org.springframework.security.core.context.SecurityContext;
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
    @Value("${CLIENT_URL}")
    private String clientUrl;

    public LoginResponse login(SignInRequest signInRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        User user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(authentication.getName());
        setRefreshTokenToCookies(refreshToken.getToken(), response);
        UserResponse userResponse = userMapper.userToUserResponse(user);
        return new LoginResponse(jwt, userResponse);
    }

    public void register(SignUpRequest signUpRequest) {
        Role roleUser = roleService.saveRoleByName(ERole.ROLE_USER);
        User user = userMapper.signUpRequestToUser(signUpRequest);
        checkExistence(user);
        user.getRoles().add(roleUser);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
    }

    public void logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        refreshTokenService.deleteByUsername(securityContext.getAuthentication().getName());
        securityContext.setAuthentication(null);
    }

    private void checkExistence(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already used.");
        }
    }

    public LoginResponse updateAccessToken(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.verifyExpiration(refreshTokenEntity);
        String accessToken = jwtTokenUtil.generateAccessToken(refreshTokenEntity.getUser());
        UserResponse user = userMapper.userToUserResponse(refreshTokenEntity.getUser());
        return new LoginResponse(accessToken, user);
    }

    private void setRefreshTokenToCookies(String refreshToken, HttpServletResponse response) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .domain(clientUrl.replace("https://",""))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(refreshTokenDurationMs))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }
}
