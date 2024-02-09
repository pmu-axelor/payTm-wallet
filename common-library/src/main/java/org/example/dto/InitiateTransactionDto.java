package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.PaymentStatus;

@Getter
@Setter
@Builder
public class InitiateTransactionDto {

    private Long sendersId;

    private Long receiversId;

    private double transactionAmount;

    private String transactionId;
}
