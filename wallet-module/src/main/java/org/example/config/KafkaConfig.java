package org.example.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.constants.Topics;
import org.example.dto.InitiateTransactionDto;
import org.example.dto.SuccessfulTransactionDto;
import org.example.dto.UserWalletCreationRequest;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.DataInput;
import java.io.IOException;

@Configuration
@Slf4j
public class KafkaConfig {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WalletService walletService;

    @KafkaListener(topics = Topics.USER_CREATION_TOPIC,groupId = "userWalletCreation")
    public  void pullMessageForWalletCreation(ConsumerRecord<String,String> receivedMessage) throws IOException {
        UserWalletCreationRequest receivedUserWalletCreationRequest = objectMapper.readValue(receivedMessage.value(), UserWalletCreationRequest.class);
        log.info("Received Data : " + String.valueOf(receivedUserWalletCreationRequest));
        walletService.createWalletAccount(receivedUserWalletCreationRequest);

    }

    @KafkaListener(topics = Topics.INITIATE_TRANSACTION,groupId = "walletTransaction")
    public void pullMessageForWalletTransaction(ConsumerRecord<String,String> receivedConsumerRecord) throws JsonProcessingException {
        InitiateTransactionDto initiateTransaction = objectMapper.readValue(receivedConsumerRecord.value(), InitiateTransactionDto.class);
        walletService.updateWalletTransaction(initiateTransaction);
    }

}
