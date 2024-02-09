package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.PaymentStatus;

@Getter
@Setter
@Builder
public class SuccessfulTransactionDto {
    private String transactionId;
    private PaymentStatus paymentStatus;
}
