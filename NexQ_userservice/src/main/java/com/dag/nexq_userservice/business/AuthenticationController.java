package com.dag.nexq_userservice.business;

import com.dag.nexq_userservice.data.request.GoogleSigninRequest;
import com.dag.nexq_userservice.data.request.LoginRequest;
import com.dag.nexq_userservice.data.request.RegisterRequest;
import com.dag.nexq_userservice.data.response.AuthResponse;
import com.dag.nexq_userservice.data.response.RegisterPasskeyResponse;
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

    @PostMapping("/passkey/create")
    public ResponseEntity<RegisterPasskeyResponse> createPasskey(){
        return ResponseEntity.ok(authenticationService.registerPasskey());
    }

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> signinWithgoogle(@RequestBody GoogleSigninRequest googleSigninRequest){
        return ResponseEntity.ok(authenticationService.signinWithGoogle(googleSigninRequest));
    }
}
