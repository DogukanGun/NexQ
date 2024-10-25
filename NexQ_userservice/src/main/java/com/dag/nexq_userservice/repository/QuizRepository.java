package com.dag.nexq_userservice.repository;

import com.dag.nexq_userservice.data.entity.Quiz;
import com.dag.nexq_userservice.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,String> {
    Set<Quiz> findAllByOwner(User owner);
}
