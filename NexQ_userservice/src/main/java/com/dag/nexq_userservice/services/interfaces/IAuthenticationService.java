package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.request.LoginRequest;
import com.dag.nexq_userservice.data.request.RegisterRequest;
import com.dag.nexq_userservice.data.response.AuthResponse;

public interface IAuthenticationService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);

}
