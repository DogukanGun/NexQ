package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.request.quiz.QuestionDto;
import com.dag.nexq_userservice.data.request.quiz.QuestionOptionDto;
import com.dag.nexq_userservice.data.request.quiz.QuizDto;
import com.dag.nexq_userservice.data.response.quiz.QuizResponse;
import com.dag.nexq_userservice.services.interfaces.IQuizGenerationService;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.models.chat.OllamaChatMessage;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.types.OllamaModelType;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizGenerationService implements IQuizGenerationService {
    private final String systemContent = """
                    Now user will send u  content then generate  number of question
                    then return the user the questions that are in json array of the format
                    so you have to respond as this format
                    """
                    + generateExampleFormat() +
                    """
                    \nexample format with example data is shown next. Your must be like this
                    Do not tell anything only generate questions
                    """;

    private final String secondCommand = "take the content and convert it to this example format:\n"
            + generateExampleFormat();
    @Override
    public QuizDto generateQuiz(String content) {
        String host = "http://localhost:11434/";
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(60);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OllamaResult res = ollamaAPI.chat(OllamaModelType.LLAMA2, List.of(
                    new OllamaChatMessage(
                            OllamaChatMessageRole.SYSTEM,
                            systemContent
                    ),
                    new OllamaChatMessage(
                            OllamaChatMessageRole.USER,
                            "My content is here then generate me quiz" +
                                    " and do not tell anything more: \n" + content
                    )
            ));
            res = ollamaAPI.chat(OllamaModelType.LLAMA2, List.of(
                    new OllamaChatMessage(
                            OllamaChatMessageRole.SYSTEM,
                            secondCommand
                    ),
                    new OllamaChatMessage(
                            OllamaChatMessageRole.SYSTEM,
                            res.getResponse()
                    )
            ));
            return objectMapper.readValue(res.getResponse(), QuizDto.class);
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateExampleFormat() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QuizDto sample = new QuizDto();
            QuestionDto question = new QuestionDto();
            question.setAnswers(List.of(
                    QuestionOptionDto.builder().content("Wrong answer 1").isCorrectAnswer(false).build(),
                    QuestionOptionDto.builder().content("Wrong answer 2").isCorrectAnswer(false).build(),
                    QuestionOptionDto.builder().content("Wrong answer 3").isCorrectAnswer(false).build(),
                    QuestionOptionDto.builder().content("Correct answer").isCorrectAnswer(true).build()
            ));
            List<QuestionDto> questionDtos = new ArrayList<>();
            for (int i=0;i<10;i++){
                question.setQuestionBody("Sample question " + i);
                questionDtos.add(question);
            }
            sample.setQuestions(questionDtos);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sample);
        } catch (Exception e) {
            throw new RuntimeException("Error generating example JSON format", e);
        }
    }
}
