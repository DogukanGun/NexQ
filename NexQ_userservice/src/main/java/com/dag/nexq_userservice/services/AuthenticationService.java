package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.config.GoogleAuthConfig;
import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.request.GoogleSigninRequest;
import com.dag.nexq_userservice.data.request.LoginRequest;
import com.dag.nexq_userservice.data.request.RegisterRequest;
import com.dag.nexq_userservice.data.response.AuthResponse;
import com.dag.nexq_userservice.data.response.RegisterPasskeyResponse;
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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

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
    public static String generateChallenge() {
        // Generate 32 random bytes
        SecureRandom random = new SecureRandom();
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        // Encode in Base64 (URL-safe) format
        return Base64.getUrlEncoder().withoutPadding().encodeToString(challenge);
    }
    @Override
    public RegisterPasskeyResponse registerPasskey() {
        User user = getCurrentCustomer();
        return RegisterPasskeyResponse
                .builder()
                .challenge(generateChallenge())
                .user(RegisterPasskeyResponse.User
                        .builder()
                        .id(user.getId().toString())
                        .name(user.getUsername())
                        .displayName(user.getUsername())
                        .build()
                )
                .rp(RegisterPasskeyResponse.Rp.builder()
                        .name("CredMan App Test")
                        .id("rp.joostd.nl")
                        .build())
                .timeout(1000000)
                .pubKeyCredParams(List.of(
                        RegisterPasskeyResponse.PubKeyCredParam.builder()
                                .type("public-key")
                                .alg(-7)
                                .build(),
                        RegisterPasskeyResponse.PubKeyCredParam.builder()
                                .type("public-key")
                                .alg(-257)
                                .build()
                ))
                .attestation("none")
                .excludeCredentials(List.of(
                        RegisterPasskeyResponse.ExcludeCredential.builder()
                                .id("ghi789")
                                .type("public-key")
                                .build(),
                        RegisterPasskeyResponse.ExcludeCredential.builder()
                                .id("jkl012")
                                .type("public-key")
                                .build()
                ))
                .authenticatorSelection(RegisterPasskeyResponse.AuthenticatorSelection
                        .builder()
                        .residentKey("discouraged")
                        .userVerification("platform")
                        .authenticatorAttachment("platform")
                        .requireResidentKey(true)
                        .build())
                .build();
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
