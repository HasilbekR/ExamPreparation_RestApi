package com.example.exampreparation_restapi.repository;

import com.example.exampreparation_restapi.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
        @Query(value = "select t from questions t where t.id = ?1 and t.user.id = ?2")
        Optional<QuestionEntity> findUserQuestionById(UUID questionId, UUID userId);
        @Query(value = "select t from questions t where t.user.role = 'ADMIN'")
        List<QuestionEntity> getBaseQuestions();
        @Query(value = "select t from questions t where  t.user.id = ?1")
        List<QuestionEntity> getUserQuestions(UUID id);
        @Query(value= "select t from questions t where t.theme = ?1 and t.user.id = ?2")
        List<QuestionEntity> findUserQuestionByTheme(String theme, UUID id);
        @Query(value= "select t from questions t where t.theme = ?1 and t.user.role = 'ADMIN'")
        List<QuestionEntity> findBaseQuestionByTheme(String theme);
        Optional<QuestionEntity> findQuestionEntityByQuestion(String question);



}
