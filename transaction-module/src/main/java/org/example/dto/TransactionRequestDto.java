package org.example.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionRequestDto {

    private Long walletId;

    private Long sendersId;

    private Long receviersId;

    private double transactionAmount;

}
