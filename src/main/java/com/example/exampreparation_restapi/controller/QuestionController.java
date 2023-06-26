package com.example.exampreparation_restapi.controller;

import com.example.exampreparation_restapi.dto.request.QuestionRequestDto;
import com.example.exampreparation_restapi.entity.QuestionEntity;
import com.example.exampreparation_restapi.exception.RequestValidationException;
import com.example.exampreparation_restapi.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<QuestionEntity> addQuestion(
            @RequestBody QuestionRequestDto questionRequestDto,
            BindingResult bindingResult,
            Principal principal
    ) throws RequestValidationException {
        return ResponseEntity.ok(questionService.addQuestion(questionRequestDto, bindingResult, principal));
    }
    @GetMapping("/get-my-questions-by-theme")
    public ResponseEntity<List<QuestionEntity>> getMyQuestionByTheme(
            Principal principal,
            @RequestParam String theme
    ){
        return ResponseEntity.ok(questionService.findUserQuestionByTheme(theme, principal));
    }
    @GetMapping("/get-my-questions-by-random")
    public ResponseEntity<List<QuestionEntity>> getMyQuestionByRandom(
            Principal principal,
            @RequestParam(name = "ranLimit") Integer limit
    ){
        return ResponseEntity.ok(questionService.getUserRandomQuestions(principal, limit));
    }

    @GetMapping("/get-base-questions-by-theme")
    public ResponseEntity<List<QuestionEntity>> getBaseQuestionByTheme(
            Principal principal,
            @RequestParam String theme
    ){
        return ResponseEntity.ok(questionService.findBaseQuestionsByTheme(theme,principal));
    }
    @GetMapping("/get-base-questions-by-random")
    public ResponseEntity<List<QuestionEntity>> getBaseQuestionByRandom(
            Principal principal,
            @RequestParam(name = "ranLimit") Integer limit
    ){
        return ResponseEntity.ok(questionService.getBaseRandomQuestions(principal, limit));
    }
    @PutMapping("/{questionId}/update")
    public ResponseEntity<QuestionEntity> updatedQuestion(
            Principal principal,
            @RequestBody QuestionRequestDto questionRequestDto,
            @PathVariable UUID questionId
            ){
        return ResponseEntity.ok(questionService.update(questionRequestDto, questionId, principal ));
    }
    @DeleteMapping("/{questionId}/delete")
    public ResponseEntity<String> deleteQuestion(
            Principal principal,
            @PathVariable UUID questionId
    ){
        questionService.deleteQuestion(questionId, principal);
        return ResponseEntity.ok("Successfully deleted");
    }
}
