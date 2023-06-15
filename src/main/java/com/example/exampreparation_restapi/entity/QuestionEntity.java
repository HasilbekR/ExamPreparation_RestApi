package com.example.exampreparation_restapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name ="questions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionEntity extends BaseEntity{
    private String question;
    private String textAnswer;
    private String voiceAnswer;
    private String theme;
    @ManyToOne
    private UserEntity user;
}
