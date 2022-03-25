package com.rabobank.socle.data.repository;


import com.rabobank.socle.data.entity.StatementRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatementRecordRepository extends JpaRepository<StatementRecord, Long> {
    Optional<StatementRecord> findByTransactionReference(String reference);
}
