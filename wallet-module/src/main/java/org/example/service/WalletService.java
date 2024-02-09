package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.Topics;
import org.example.dto.InitiateTransactionDto;
import org.example.dto.SendMailNotification;
import org.example.dto.SuccessfulTransactionDto;
import org.example.dto.UserWalletCreationRequest;
import org.example.enums.PaymentStatus;
import org.example.enums.ServiceType;
import org.example.model.Wallet;
import org.example.repo.WalletRepo;
import org.example.templates.MailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WalletService {

    @Autowired
    WalletRepo walletRepo;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void createWalletAccount(UserWalletCreationRequest receivedUserWalletCreationRequest) throws JsonProcessingException {
        String userId = receivedUserWalletCreationRequest.getUserId();
      Wallet walletAccount = new Wallet(Long.parseLong(userId));
        Wallet walletCreated = walletRepo.save(walletAccount);
        log.info(String.format("walletID: %d , Balance : %f and UserID : %d",
                walletCreated.getWalletId(),
                walletCreated.getBalance(),
                walletCreated.getUserId()));

        SendMailNotification sendMailNotification =  SendMailNotification.builder().
         receviersMailId(receivedUserWalletCreationRequest.getEmailId())
                .serviceType(ServiceType.WALLET_SERVICE)
                .mailSubject(MailTemplate.mailTemplate().getMailSubject())
                .mailBody(String.format(MailTemplate.mailTemplate().getMailBody(),receivedUserWalletCreationRequest.getName()))
        .build();

        kafkaTemplate.send(Topics.SEND_MAIL_NOTIFICATION,userId, objectMapper.writeValueAsString(sendMailNotification));

    }

    public Double getWalletBalance(Long userId) {
      Wallet wallet = walletRepo.findByUserId(userId);
     return wallet.getBalance();
    }

    public void updateWalletTransaction(InitiateTransactionDto initiateTransaction) throws JsonProcessingException {
     Wallet sendersWallet  = walletRepo.findByUserId(initiateTransaction.getSendersId());
     sendersWallet.setBalance(sendersWallet.getBalance() - initiateTransaction.getTransactionAmount());
     walletRepo.save(sendersWallet);

        Wallet receiversWallet = walletRepo.findByUserId(initiateTransaction.getReceiversId());
        receiversWallet.setBalance(receiversWallet.getBalance() + initiateTransaction.getTransactionAmount());
        walletRepo.save(receiversWallet);

        SuccessfulTransactionDto successfulTransaction = SuccessfulTransactionDto.builder()
                .transactionId(initiateTransaction.getTransactionId())
                .paymentStatus(PaymentStatus.SUCCESSFUL)
                .build();

        kafkaTemplate.send(Topics.SUCCESSFUL_TRANSACTION,initiateTransaction.getTransactionId(),objectMapper.writeValueAsString(SuccessfulTransactionDto.class));

    }
}
