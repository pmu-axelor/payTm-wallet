package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.PaymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wallet_transaction")
public class Transaction {

    @Id
    @UuidGenerator
    private String transactionId;

    @Column(nullable = false)
    private Long walletId;

    @Column(nullable = false)
    private Long receviersId;

    @Column(nullable = false)
    private Long sendersId;

    @Column(nullable = false)
    private double transactionAmount;

    @CreationTimestamp
    private LocalDateTime transactionInitiationTime;

    @UpdateTimestamp
    private LocalDateTime transactionSuccessfulTime;

    private String remark;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Transaction(Long walletId, Long receviersId, Long sendersId, double transactionAmount) {
        this.walletId = walletId;
        this.receviersId = receviersId;
        this.sendersId = sendersId;
        this.transactionAmount = transactionAmount;
        this.paymentStatus = PaymentStatus.PENDING;
    }
}
