package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.PaymentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionResponseDto {

    private String transactionId;

    private String message;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentIniatiationTime;

}
