package com.github.springdocumentservice.repository;

import com.github.springdocumentservice.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserNickname(String userNickname);
}
