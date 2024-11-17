package com.dag.nexq_userservice.data.mapper;

import com.dag.nexq_userservice.data.entity.Question;
import com.dag.nexq_userservice.data.entity.QuestionOption;
import com.dag.nexq_userservice.data.entity.Quiz;
import com.dag.nexq_userservice.data.request.quiz.QuestionOptionDto;
import com.dag.nexq_userservice.data.request.quiz.QuestionDto;
import com.dag.nexq_userservice.data.request.quiz.QuizDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizMapper QUIZ_MAPPER = Mappers.getMapper(QuizMapper.class);

    Quiz convertToQuiz(QuizDto quizDto);

    QuizDto convertToQuizDto(Quiz quiz);

    QuestionDto convertToQuestionDto(Question question);
    Question convertToQuestion(QuestionDto questionDto);

    QuestionOptionDto convertToQuestionOption(QuestionOption questionOption);

    QuestionOption convertToQuestionOption(QuestionOptionDto questionOptionDto);

}
