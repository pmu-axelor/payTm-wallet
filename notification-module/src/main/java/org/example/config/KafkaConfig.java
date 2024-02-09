package org.example.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.constants.Topics;
import org.example.dto.SendMailNotification;
import org.example.service.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class KafkaConfig {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SendMail sendMail;

    @KafkaListener(topics = Topics.SEND_MAIL_NOTIFICATION,groupId = "send_notification")
    public void sendMailNotification(ConsumerRecord<String,String> receviedMessage) throws JsonProcessingException {
        SendMailNotification sendMailNotification = objectMapper.readValue(receviedMessage.value(), SendMailNotification.class);
        log.info(String.format("Send mail notification details : %s ", sendMailNotification.getMailBody()));
        sendMail.sendMailNotificationToUsers(sendMailNotification);
    }
}
