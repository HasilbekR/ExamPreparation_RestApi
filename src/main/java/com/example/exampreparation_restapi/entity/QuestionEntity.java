package com.example.exampreparation_restapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    @Column(unique = true, nullable = false)
    private String question;
    @Column(columnDefinition = "text")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String textAnswer;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String voiceAnswer;
    @Column(nullable = false)
    private String theme;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserEntity user;

}
