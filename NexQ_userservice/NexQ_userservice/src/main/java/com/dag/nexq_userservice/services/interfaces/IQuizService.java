package com.dag.nexq_userservice.services.interfaces;

import com.dag.nexq_userservice.data.request.quiz.CreateQuizRequest;
import com.dag.nexq_userservice.data.request.quiz.QuizDto;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface IQuizService<T,K> {
    T createQuiz(CreateQuizRequest createQuizRequest);
    T updateQuizName(T id, String newName);
    void deleteQuiz(T id);
    List<K> getAllQuiz();
    K getQuizById(T id) throws AuthenticationException;
    K addQuiz(QuizDto quizDto);
    List<K> getMyQuizzes();
}
