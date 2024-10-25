package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.services.interfaces.IQuizGenerationService;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.models.chat.OllamaChatMessage;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.types.OllamaModelType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class QuizGenerationService implements IQuizGenerationService {
    private String systemContent = """
                    Now user will send u  content then generate  number of question
                    then return the user the questions that are in json array of the format
                    so you have to respond as this format 
                    {
                        question: [
                            { question:str, correct_answer:str, wrong_answers:[str] }.
                            { question:str, correct_answer:str, wrong_answers:[str] }
                        ]
                    }
                    example format with example data is shown next. Your must be like this
                    Do not tell anything only generate questions
                    """;

    private String secondCommand = """
    take the content and convert it to this example format
                        {
    "question": [
        {
            "question": "What was a positive aspect of the onboarding process mentioned in the feedback?",
            "correct_answer": "Subsections were divided well, making it easy to find what was needed.",
            "wrong_answers": [
                "No code snippets were explained.",
                "The dashboard design was not user-friendly.",
                "The API integration was not documented."
            ]
        },
        {
            "question": "Which SDK function was deprecated in the new version of the Portal SDK?",
            "correct_answer": "CreateWallet",
            "wrong_answers": [
                "RecoverWallet",
                "PortalAPI",
                "BackupMethods"
            ]
        },
    ]
}

    """;
    @Override
    public String generateQuiz(String content) {
        String host = "http://localhost:11434/";
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(60);
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

            return res.getResponse();
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
