package org.example.kafkaConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.constants.Topics;
import org.example.dto.SuccessfulTransactionDto;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Consumer {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TransactionService transactionService;

    @KafkaListener(topics = Topics.SUCCESSFUL_TRANSACTION,groupId = "successfulTransaction")
    public void pullMessageForWalletTransaction(ConsumerRecord<String,String> receivedConsumerRecord) throws JsonProcessingException {
        SuccessfulTransactionDto successfulTransactionDto = objectMapper.readValue(receivedConsumerRecord.value(), SuccessfulTransactionDto.class);
        transactionService.updateTransaction(successfulTransactionDto);
    }

}
