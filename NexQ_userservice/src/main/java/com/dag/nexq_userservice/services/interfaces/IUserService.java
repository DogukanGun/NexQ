package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.request.LoginRequest;

public interface IUserService {
    User findUserByUsername(String username);
    User saveUser(User user);
}
