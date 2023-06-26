package com.example.exampreparation_restapi.service.question;

import com.example.exampreparation_restapi.dto.request.QuestionRequestDto;
import com.example.exampreparation_restapi.entity.QuestionEntity;
import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.exception.DataNotFoundException;
import com.example.exampreparation_restapi.exception.RequestValidationException;
import com.example.exampreparation_restapi.exception.UniqueObjectException;
import com.example.exampreparation_restapi.repository.QuestionRepository;
import com.example.exampreparation_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<QuestionEntity> getUserRandomQuestions(Principal principal, Integer limit) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        List<QuestionEntity> userQuestions = questionRepository.getUserQuestions(userEntity.getId());
        Collections.shuffle(userQuestions);
        if(userQuestions.size() >= limit) {
            return userQuestions.subList(0, limit);
        }
        return userQuestions.subList(0, userQuestions.size());
    }


    @Override
    public List<QuestionEntity> findUserQuestionByTheme(String theme, Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        return questionRepository.findUserQuestionByTheme(theme, userEntity.getId());
    }

    @Override
    public List<QuestionEntity> getBaseRandomQuestions(Principal principal, Integer limit) {
        userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        List<QuestionEntity> userQuestions = questionRepository.getBaseQuestions();
        Collections.shuffle(userQuestions);
        if(userQuestions.size() >= limit) {
            return userQuestions.subList(0, limit);
        }
        return userQuestions.subList(0, userQuestions.size());
    }

    @Override
    public List<QuestionEntity> findBaseQuestionsByTheme(String theme, Principal principal) {
        userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        return questionRepository.findBaseQuestionByTheme(theme);
    }

    @Override
    public QuestionEntity update(QuestionRequestDto questionRequestDto, UUID questionId, Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        QuestionEntity question = questionRepository.findUserQuestionById(questionId, userEntity.getId()).orElseThrow(() -> new DataNotFoundException("Question not found"));
        if(questionRequestDto.getQuestion() != null) {
            if (!questionRequestDto.getQuestion().equals(question.getQuestion())) {
                Optional<QuestionEntity> questionEntityByQuestion = questionRepository.findQuestionEntityByQuestion(questionRequestDto.getQuestion());
                if (questionEntityByQuestion.isPresent()) {
                    throw new UniqueObjectException("Question already exists");
                }
                question.setQuestion(questionRequestDto.getQuestion());
            }
        }
        if(questionRequestDto.getTextAnswer() != null) {
            question.setTextAnswer(questionRequestDto.getTextAnswer());
        }
        if(questionRequestDto.getTheme() != null) {
            question.setTheme(questionRequestDto.getTheme());
        }
        if(questionRequestDto.getVoiceAnswer() != null){
            question.setVoiceAnswer(questionRequestDto.getVoiceAnswer());
        }
        question.setUpdatedDate(LocalDateTime.now());
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(UUID questionId, Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("User not found"));
        QuestionEntity question = questionRepository.findUserQuestionById(questionId, userEntity.getId()).orElseThrow(() -> new DataNotFoundException("Question not found"));
        questionRepository.delete(question);
    }


    @Override
    public QuestionEntity addQuestion(QuestionRequestDto questionRequestDto, BindingResult bindingResult, Principal principal) throws RequestValidationException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("user not found"));
        QuestionEntity question = modelMapper.map(questionRequestDto, QuestionEntity.class);
        question.setUser(userEntity);
        return questionRepository.save(question);
    }
}
