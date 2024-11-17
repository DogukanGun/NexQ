package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.entity.Quiz;
import com.dag.nexq_userservice.data.request.quiz.QuizDto;

public interface IQuizGenerationService {
    QuizDto generateQuiz(String content);
}
