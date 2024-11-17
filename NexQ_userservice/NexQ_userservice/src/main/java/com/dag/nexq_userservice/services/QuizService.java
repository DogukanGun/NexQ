package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.entity.Quiz;
import com.dag.nexq_userservice.data.entity.User;
import com.dag.nexq_userservice.data.request.quiz.CreateQuizRequest;
import com.dag.nexq_userservice.data.request.quiz.QuizDto;
import com.dag.nexq_userservice.repository.QuizRepository;
import com.dag.nexq_userservice.services.interfaces.IQuizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.util.List;

import static com.dag.nexq_userservice.data.mapper.QuizMapper.QUIZ_MAPPER;

@Service
public class QuizService implements IQuizService<String,Quiz> {

    private final QuizRepository quizRepository;
    private final AuthenticationService authenticationService;

    public QuizService(QuizRepository quizRepository,AuthenticationService authenticationService) {
        this.quizRepository = quizRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    @Transactional
    public String createQuiz(CreateQuizRequest createQuizRequest) {
        User user = authenticationService.getCurrentCustomer();
        if (user == null || user.getId() == null) {
            throw new RuntimeException("User not authenticated or user ID is null");
        }
        Quiz toBeSaved = Quiz.builder()
                .name(createQuizRequest.getName())
                .owner(user)
                .build();
        Quiz savedQuiz = quizRepository.save(toBeSaved);
        return savedQuiz.getId();
    }

    @Override
    public String updateQuizName(String id, String newName) {
        Quiz requestedQuiz = quizRepository.findById(id).orElseThrow();
        requestedQuiz.setName(newName);
        requestedQuiz = quizRepository.save(requestedQuiz);
        return requestedQuiz.getId();
    }

    @Override
    public void deleteQuiz(String id) {
        quizRepository.deleteById(id);
    }

    @Override
    public List<Quiz> getAllQuiz() {
        User user = authenticationService.getCurrentCustomer();
        return quizRepository.findAllByOwner(user).stream().toList();
    }

    @Override
    public Quiz getQuizById(String id) throws AuthenticationException {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        User user = authenticationService.getCurrentCustomer();
        if (user.getId().equals(quiz.getOwner().getId())){
            return quiz;
        }
        throw new AuthenticationException();
    }

    @Override
    public Quiz addQuiz(QuizDto quizDto){
        Quiz quiz = QUIZ_MAPPER.convertToQuiz(quizDto);
        User user = authenticationService.getCurrentCustomer();
        quiz.setOwner(user);
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getMyQuizzes() {
        User user = authenticationService.getCurrentCustomer();
        return quizRepository.findAllByOwner(user).stream().toList();
    }
}
