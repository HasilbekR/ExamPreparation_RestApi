package com.example.exampreparation_restapi.service.question;

import com.example.exampreparation_restapi.dto.request.QuestionRequestDto;
import com.example.exampreparation_restapi.entity.QuestionEntity;
import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.exception.DataNotFoundException;
import com.example.exampreparation_restapi.repository.QuestionRepository;
import com.example.exampreparation_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    @Override
    public QuestionEntity save(QuestionRequestDto questionRequestDto) {
      return null;
    }


    @Override
    public QuestionEntity update(QuestionRequestDto questionRequestDto, UUID id) {
        QuestionEntity existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Question not found"));

        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<QuestionRequestDto, QuestionEntity> questionMap = new PropertyMap<QuestionRequestDto, QuestionEntity>() {
            @Override
            protected void configure() {
                skip().setId(null);
                skip().setCreatedDate(null);
                skip().setUpdatedDate(null);
            }
        };

        modelMapper.addMappings(questionMap);
        modelMapper.map(questionRequestDto, existingQuestion);

        existingQuestion.setUpdatedDate(LocalDateTime.now());
        return questionRepository.save(existingQuestion);
    }

    @Override
    public void deleteById(UUID id) {
        questionRepository.deleteById(id);
    }

    @Override
    public QuestionEntity getById(UUID id) {
        return questionRepository.findQuestionById(id);
    }



    @Override
    public List<QuestionEntity> getAllByUserId(UUID userId) {
        return questionRepository.getAllByUserId(userId);
    }

    @Override
    public List<QuestionEntity> getAll() {
        return questionRepository.getAllByData();
    }

    @Override
    public List<QuestionEntity> findQuestionByTheme(String theme, UUID id) {
        return questionRepository.findQuestionByTheme(theme, id);
    }

    @Override
    public QuestionEntity updateTaskEntity(QuestionEntity question) {
        return null;
    }

    @Override
    public QuestionEntity addQuestion(QuestionRequestDto questionRequestDto, UUID uuid) {
        UserEntity userEntityById = userRepository.findUserEntityById(uuid).orElseThrow(() ->new DataNotFoundException("user not found"));
        QuestionEntity question = modelMapper.map(questionRequestDto, QuestionEntity.class);
        question.setUser(userEntityById);
        return questionRepository.save(question);
    }
}
