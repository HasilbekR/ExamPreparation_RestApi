package com.example.exampreparation_restapi.service.question;


import com.example.exampreparation_restapi.dto.request.QuestionRequestDto;
import com.example.exampreparation_restapi.entity.QuestionEntity;
import com.example.exampreparation_restapi.exception.RequestValidationException;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface QuestionService{
    QuestionEntity addQuestion(QuestionRequestDto questionRequestDto, BindingResult bindingResult, Principal principal) throws RequestValidationException;
    List<QuestionEntity> getUserRandomQuestions(Principal principal, Integer limit);
    List<QuestionEntity> findUserQuestionByTheme(String theme, Principal principal);
    List<QuestionEntity> getBaseRandomQuestions(Principal principal, Integer limit);
    List<QuestionEntity> findBaseQuestionsByTheme(String theme, Principal principal);
    QuestionEntity update(QuestionRequestDto questionRequestDto, UUID questionId, Principal principal);
    void deleteQuestion(UUID questionId, Principal principal);


}
