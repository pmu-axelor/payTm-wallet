package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.Topics;
import org.example.dto.InitiateTransactionDto;
import org.example.dto.SuccessfulTransactionDto;
import org.example.dto.TransactionRequestDto;
import org.example.dto.TransactionResponseDto;
import org.example.enums.PaymentStatus;
import org.example.model.Transaction;
import org.example.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Format;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) throws JsonProcessingException {
  Boolean checkSendersExits = restTemplate.getForObject("http://localhost:8081/user/details/" + transactionRequestDto.getSendersId(),Boolean.class);
  log.info(String.format("checkSendersExits: " + checkSendersExits));
  if(!checkSendersExits){
      log.info(String.format("senders %d is not exists",transactionRequestDto.getSendersId()));
      throw new RuntimeException(String.format("senders %d is not exists",transactionRequestDto.getSendersId()));
  }

        Boolean checkReceiversExits = restTemplate.getForObject("http://localhost:8081/user/details/" + transactionRequestDto.getReceviersId(),Boolean.class);
      log.info("checkReceiversExits: " + checkReceiversExits);
  if(!checkReceiversExits){
            log.info(String.format("receviers %d is not exists",transactionRequestDto.getReceviersId()));
            throw new RuntimeException(String.format("receviers %d is not exists",transactionRequestDto.getReceviersId()));
        }
 Double balanceInWallet = restTemplate.getForObject("http://localhost:8081/wallet/balance/"+transactionRequestDto.getWalletId(),Double.class);
 if(balanceInWallet < transactionRequestDto.getTransactionAmount()){
     log.info(String.format("You don't have sufficient balance, {} ",balanceInWallet));
     throw new RuntimeException(String.format("You don't have sufficient balance :  " + balanceInWallet));
 }

      Transaction currentTransaction = new Transaction(transactionRequestDto.getWalletId(),transactionRequestDto.getSendersId(),transactionRequestDto.getReceviersId(),transactionRequestDto.getTransactionAmount());
        Transaction savedTransaction = transactionRepository.save(currentTransaction);

        InitiateTransactionDto initiateTransactionDto = InitiateTransactionDto.builder()
                .transactionId(savedTransaction.getTransactionId())
                .sendersId(savedTransaction.getSendersId())
                .receiversId(savedTransaction.getReceviersId())
                .transactionAmount(savedTransaction.getTransactionAmount())
                .build();

        kafkaTemplate.send(Topics.INITIATE_TRANSACTION,savedTransaction.getTransactionId(), objectMapper.writeValueAsString(initiateTransactionDto));

        return TransactionResponseDto.builder()
                .transactionId(savedTransaction.getTransactionId())
                .message("Transaction is in progress")
                .paymentStatus(PaymentStatus.PENDING)
                .paymentIniatiationTime(savedTransaction.getTransactionInitiationTime())
                .build();
    }

    public void updateTransaction(SuccessfulTransactionDto successfulTransactionDto) {
      Transaction transaction = transactionRepository.findById(successfulTransactionDto.getTransactionId()).get();
      transaction.setPaymentStatus(successfulTransactionDto.getPaymentStatus().equals(PaymentStatus.SUCCESSFUL) ? PaymentStatus.SUCCESSFUL : PaymentStatus.FAILED);
      transactionRepository.save(transaction);
    }
}
