package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_wallet_account")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(nullable = false,unique = true)
    private Long userId;

    @Column(nullable = false)
    @Value("${org.example.wallet.balance}")
    private Double balance;

    @Column(nullable = false)
    @Value("${org.example.wallet.dailyLimit}")
    private Long dailyLimit;

    @Column(nullable = false)
    @Value("${org.example.wallet.dailyTransactionLimit}")
    private Integer dailyTransactionLimit;

    @CreationTimestamp
    private LocalDateTime walletCreatedTime;

    @UpdateTimestamp
    private LocalDateTime walletUpdatedTime;

    public Wallet(Long userId) {
        this.userId = userId;
        this.balance = 0.0D;
        this.dailyLimit = 100000L;
        this.dailyTransactionLimit = 10;
    }
}
