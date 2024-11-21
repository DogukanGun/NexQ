package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.entity.User;

public interface IUserService {
    User findUserByUsername(String username);
    User saveUser(User user);
}
