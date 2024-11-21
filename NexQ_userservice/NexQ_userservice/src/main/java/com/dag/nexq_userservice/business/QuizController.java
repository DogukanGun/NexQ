package com.dag.nexq_userservice.business;

import com.dag.nexq_userservice.data.request.quiz.CreateQuizRequest;
import com.dag.nexq_userservice.data.request.quiz.QuizDto;
import com.dag.nexq_userservice.data.response.quiz.QuizResponse;
import com.dag.nexq_userservice.services.QuizService;
import com.dag.nexq_userservice.services.interfaces.IQuizGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.dag.nexq_userservice.data.mapper.QuizMapper.QUIZ_MAPPER;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final IQuizGenerationService quizGenerationService;

    @PostMapping("")
    public ResponseEntity<String> createQuiz(@RequestBody CreateQuizRequest createQuizRequest) {
        return ResponseEntity.ok(quizService.createQuiz(createQuizRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@PathVariable("id") String id, @RequestParam("name") String name) {
        return ResponseEntity.ok(quizService.updateQuizName(id, name));
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable("id") String id) {
        quizService.deleteQuiz(id);
    }

    @PostMapping("/all")
    public ResponseEntity<List<QuizDto>> getQuizzes() {
        return ResponseEntity.ok(
                quizService.getAllQuiz()
                        .stream().map(QUIZ_MAPPER::convertToQuizDto).toList()
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    QUIZ_MAPPER.convertToQuizDto(quizService.getQuizById(id))
            );
        } catch (AuthenticationException ae) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<QuizResponse> addQuiz(@RequestBody QuizDto quizDto) {
        var quiz  = quizService.addQuiz(quizDto);
        return ResponseEntity.ok(QuizResponse.builder()
                        .id(quiz.getId())
                        .status(true).build()
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<QuizDto>> getMyQuizzes(){
        return ResponseEntity.ok(
                quizService.getMyQuizzes().stream()
                        .map(QUIZ_MAPPER::convertToQuizDto).toList()
        );
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WebAsyncTask<ResponseEntity<QuizDto>> generateQuiz(@RequestParam("file") MultipartFile file) {
        WebAsyncTask<ResponseEntity<QuizDto>> task = new WebAsyncTask<>(60000, () -> {
            try {
                String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
                return ResponseEntity.ok(quizGenerationService.generateQuiz(fileContent));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return ResponseEntity.status(500).build();
            }
        });
        task.onTimeout(() -> ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build());
        return task;
    }

}
