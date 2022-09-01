package by.urbel.finaltask.controller;

import by.urbel.finaltask.dto.requests.SignInRequest;
import by.urbel.finaltask.dto.requests.SignUpRequest;
import by.urbel.finaltask.dto.response.JwtResponse;
import by.urbel.finaltask.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
        JwtResponse jwtResponse = authService.login(signInRequest, response);
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/refreshjwt")
    public ResponseEntity<?> updateAccessToken(@CookieValue String refreshToken){
        return ResponseEntity.ok(authService.updateAccessToken(refreshToken));
    }
}
