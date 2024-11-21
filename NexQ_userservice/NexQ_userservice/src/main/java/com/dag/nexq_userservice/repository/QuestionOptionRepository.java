package com.dag.nexq_userservice.repository;

import com.dag.nexq_userservice.data.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption,Integer> { }
