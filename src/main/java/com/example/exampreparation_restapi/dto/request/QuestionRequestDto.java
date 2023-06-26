package com.example.exampreparation_restapi.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionRequestDto {
    @NotBlank(message = "theme cannot be blank")
    @Column(nullable = false)
    private String theme;
    @NotBlank(message = "question cannot be blank")
    @Column(nullable = false)
    private String question;
    private String textAnswer;
    private String voiceAnswer;
}
