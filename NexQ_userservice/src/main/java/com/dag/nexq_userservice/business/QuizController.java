package com.dag.nexq_userservice.business;

import com.dag.nexq_userservice.data.entity.Quiz;
import com.dag.nexq_userservice.data.request.CreateQuizRequest;
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

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final IQuizGenerationService quizGenerationService;

    @PostMapping("")
    public ResponseEntity<String> createQuiz(@RequestBody CreateQuizRequest createQuizRequest){
        return ResponseEntity.ok(quizService.createQuiz(createQuizRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@PathVariable("id") String id,@RequestParam("name") String name){
        return ResponseEntity.ok(quizService.updateQuizName(id,name));
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable("id") String id){
        quizService.deleteQuiz(id);
    }

    @PostMapping("/all")
    public ResponseEntity<List<Quiz>> getQuizzes(){
        return ResponseEntity.ok(quizService.getAllQuiz());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") String id){
        try {
            return ResponseEntity.ok(quizService.getQuizById(id));
        }catch (AuthenticationException ae){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WebAsyncTask<ResponseEntity<String>> generateQuiz(@RequestParam("file") MultipartFile file) {
        WebAsyncTask<ResponseEntity<String>> task = new WebAsyncTask<>(60000, () -> {
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
