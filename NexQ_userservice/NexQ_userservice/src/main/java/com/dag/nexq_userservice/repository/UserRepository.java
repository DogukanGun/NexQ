package com.dag.nexq_userservice.repository;

import com.dag.nexq_userservice.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
        User findByUsername(String username);
}
