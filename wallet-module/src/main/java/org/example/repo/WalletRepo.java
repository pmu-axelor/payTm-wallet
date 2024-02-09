package org.example.repo;

import org.example.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,Long> {
    Wallet findByUserId(Long id);
}
