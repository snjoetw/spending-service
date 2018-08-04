package com.sj.spending.transaction.data.repo;

import com.sj.spending.transaction.data.entity.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.transactionDate >= ?1 AND t.transactionDate < ?2 AND t.deleted = 0 ORDER BY createdAt DESC")
    List<TransactionEntity> findByTransactionDateBetween(Date startDate, Date endDate);

    @Query("SELECT t FROM TransactionEntity t WHERE t.transactionDate >= ?1 AND t.transactionDate < ?2 AND t.modifiedAt > ?3 ORDER BY createdAt DESC")
    List<TransactionEntity> findByTransactionDateBetweenAndModifiedAtAfter(Date startDate, Date endDate, Date modifiedAt);

    @Query("SELECT t FROM TransactionEntity t WHERE t.modifiedAt > ?1 ORDER BY modifiedAt DESC")
    List<TransactionEntity> findByModifiedAtAfter(Date modifiedAt);

}
