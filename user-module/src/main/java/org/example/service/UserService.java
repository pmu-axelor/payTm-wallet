package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.Topics;
import org.example.dto.UserResponseDto;
import org.example.dto.UserWalletCreationRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public UserResponseDto createUserAccount(User user) throws JsonProcessingException {
      User savedUser = userRepository.save(user);
      log.info(String.format("User account is created with userId : %d , userName : %s", savedUser.getUserId(),savedUser.getFullName()));

      UserWalletCreationRequest userWalletCreationRequest = UserWalletCreationRequest.builder()
              .userId(String.valueOf(savedUser.getUserId()))
              .emailId(savedUser.getUserEmailId())
              .name(savedUser.getFullName())
              .build();

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(Topics.USER_CREATION_TOPIC, String.valueOf(savedUser.getUserId()),objectMapper.writeValueAsString(userWalletCreationRequest));

        return UserResponseDto.builder()
              .message("User account is created, Wallet creation is in progress. We will notify you over mail once done")
              .user(savedUser)
              .build();

    }

    public Boolean checkUserExistance(Long id) {
       return userRepository.existsById(id);
    }
}
