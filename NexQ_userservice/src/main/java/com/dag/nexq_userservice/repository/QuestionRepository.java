package com.dag.nexq_userservice.repository;

import com.dag.nexq_userservice.data.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> { }
