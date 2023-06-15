package com.example.exampreparation_restapi.service.question;


import com.example.exampreparation_restapi.dto.request.QuestionRequestDto;
import com.example.exampreparation_restapi.entity.QuestionEntity;
import com.example.exampreparation_restapi.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface QuestionService extends BaseService<QuestionEntity, QuestionRequestDto> {
    QuestionEntity addQuestion(QuestionRequestDto questionRequestDto, UUID uuid);
    List<QuestionEntity> getAllByUserId(UUID userId);
    List<QuestionEntity> getAll();
    List<QuestionEntity> findQuestionByTheme(String theme, UUID id);
    QuestionEntity updateTaskEntity(QuestionEntity question);


}
