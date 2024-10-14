package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.mapper.UserMapper;
import com.dag.nexq_userservice.data.request.LoginRequest;
import com.dag.nexq_userservice.repository.UserRepository;
import com.dag.nexq_userservice.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }
}
