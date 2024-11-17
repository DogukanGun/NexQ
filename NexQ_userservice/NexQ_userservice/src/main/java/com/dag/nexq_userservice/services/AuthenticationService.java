package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.config.GoogleAuthConfig;
import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.request.auth.GoogleSigninRequest;
import com.dag.nexq_userservice.data.request.auth.LoginRequest;
import com.dag.nexq_userservice.data.request.auth.RegisterRequest;
import com.dag.nexq_userservice.data.response.auth.AuthResponse;
import com.dag.nexq_userservice.data.sec.UserDetailsImpl;
import com.dag.nexq_userservice.security.TokenGenerator;
import com.dag.nexq_userservice.services.interfaces.IAuthenticationService;
import com.dag.nexq_userservice.services.interfaces.IUserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.dag.nexq_userservice.data.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthConfig googleAuthConfig;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.findUserByUsername(loginRequest.getUsername());
            String token = tokenGenerator.generateJwtToken(USER_MAPPER.convertToUserDetail(user));

            return AuthResponse.builder()
                    .token(token)
                    .error(false)
                    .build();
        }catch (Exception e){
            System.out.print(e.getMessage());
            return AuthResponse.builder()
                    .token("")
                    .error(true)
                    .build();
        }

    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        User user = USER_MAPPER.createUser(registerRequest);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User savedUser = userService.saveUser(user);
        String token = tokenGenerator.generateJwtToken(USER_MAPPER.convertToUserDetail(savedUser));
        return AuthResponse.builder()
                .token(token)
                .error(false)
                .build();
    }

    @Override
    public AuthResponse signinWithGoogle(GoogleSigninRequest googleSigninRequest) {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(googleAuthConfig.clientKey()))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(googleSigninRequest.getId());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                String name = (String) payload.get("name");
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public User getCurrentCustomer() {
        UserDetailsImpl jwtUserDetails = getCurrentJwtUserDetails();
        User cusCustomer = null;
        if (jwtUserDetails != null){
            cusCustomer = userService.findUserByUsername(jwtUserDetails.getUsername());
        }
        return cusCustomer;
    }

    private UserDetailsImpl getCurrentJwtUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl jwtUserDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl){
            jwtUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        }
        return jwtUserDetails;
    }
}
