package by.urbel.finaltask.controller;

import by.urbel.finaltask.dto.requests.SignInRequest;
import by.urbel.finaltask.dto.requests.SignUpRequest;
import by.urbel.finaltask.dto.response.LoginResponse;
import by.urbel.finaltask.service.AuthService;
import by.urbel.finaltask.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        authService.register(signUpRequest);
        return ResponseEntity.ok("Successfully registered.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequest signInRequest,
                                   HttpServletResponse response, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        LoginResponse loginResponse = authService.login(signInRequest, response);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(){
        authService.logout();
    }

    @GetMapping("/refresh-jwt")
    public ResponseEntity<?> updateAccessToken(@CookieValue String refreshToken){
        return ResponseEntity.ok(authService.updateAccessToken(refreshToken));
    }
}
