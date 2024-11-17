package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.mapper.UserMapper;
import com.dag.nexq_userservice.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserMapper.USER_MAPPER.convertToUserDetail(userService.findUserByUsername(username));
    }
}
