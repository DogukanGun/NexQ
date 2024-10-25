package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.entity.Quiz;

public interface IQuizGenerationService {
    String generateQuiz(String content);
}
