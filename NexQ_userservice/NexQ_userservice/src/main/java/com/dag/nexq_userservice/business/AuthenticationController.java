package com.dag.nexq_userservice.business;

import com.dag.nexq_userservice.data.request.auth.GoogleSigninRequest;
import com.dag.nexq_userservice.data.request.auth.LoginRequest;
import com.dag.nexq_userservice.data.request.auth.RegisterRequest;
import com.dag.nexq_userservice.data.response.auth.AuthResponse;
import com.dag.nexq_userservice.services.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> signinWithgoogle(@RequestBody GoogleSigninRequest googleSigninRequest){
        return ResponseEntity.ok(authenticationService.signinWithGoogle(googleSigninRequest));
    }
}
