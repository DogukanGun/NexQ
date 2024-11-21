package com.dag.nexq_userservice.data.request.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionOptionDto {
    private Boolean isCorrectAnswer;
    private String content;

}
