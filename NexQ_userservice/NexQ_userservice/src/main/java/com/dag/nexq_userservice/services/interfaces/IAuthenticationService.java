package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.request.auth.GoogleSigninRequest;
import com.dag.nexq_userservice.data.request.auth.LoginRequest;
import com.dag.nexq_userservice.data.request.auth.RegisterRequest;
import com.dag.nexq_userservice.data.response.auth.AuthResponse;

public interface IAuthenticationService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse signinWithGoogle(GoogleSigninRequest googleSigninRequest);

}
