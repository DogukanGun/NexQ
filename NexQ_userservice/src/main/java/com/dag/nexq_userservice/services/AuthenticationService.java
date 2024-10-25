package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.mapper.UserMapper;
import com.dag.nexq_userservice.data.request.LoginRequest;
import com.dag.nexq_userservice.data.request.RegisterRequest;
import com.dag.nexq_userservice.data.response.AuthResponse;
import com.dag.nexq_userservice.data.sec.UserDetailsImpl;
import com.dag.nexq_userservice.security.TokenGenerator;
import com.dag.nexq_userservice.services.interfaces.IAuthenticationService;
import com.dag.nexq_userservice.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.dag.nexq_userservice.data.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
