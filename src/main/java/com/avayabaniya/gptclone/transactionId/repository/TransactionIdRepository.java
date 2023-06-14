package com.avayabaniya.gptclone.transactionId.repository;


import com.avayabaniya.gptclone.transactionId.model.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionIdRepository extends JpaRepository<TransactionId, Long> {
}
