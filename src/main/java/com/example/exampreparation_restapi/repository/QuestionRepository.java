package com.example.exampreparation_restapi.repository;

import com.example.exampreparation_restapi.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
        @Query(value = "select t from questions t where t.id = ?1")
        QuestionEntity findQuestionById(UUID id);
        @Query(value = "select t from questions t order by t.createdDate asc ")
        List<QuestionEntity> getAllByData();
        @Query(value = "select t from questions t where  t.user.id = ?1 order by t.createdDate asc")
        List<QuestionEntity> getAllByUserId(UUID id);
        @Query(value= "select t from questions t where t.theme = ?1 and t.user.id = ?2")
        List<QuestionEntity> findQuestionByTheme(String theme, UUID id);
        @Query("UPDATE questions t SET t.question = ?1, t.textAnswer = ?2, t.voiceAnswer = ?3, t.theme = ?4,t.updatedDate = ?5 WHERE t.id = ?6")
        QuestionEntity updateQuestion(String question, String textAnswer, String voiceAnswer, String theme, LocalDateTime updatedDate, UUID id);


}
