package com.sj.spending.transaction.service;

import com.sj.spending.transaction.data.dto.TransactionDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    List<TransactionDto> findTransactions(LocalDate start, LocalDate end);

    List<TransactionDto> findTransactions(LocalDate start, LocalDate end, Long lastUpdateInMillis);

    TransactionDto findTransaction(long transactionId);

    TransactionDto addTransaction(TransactionDto transaction);

    TransactionDto updateTransaction(TransactionDto transaction);

    void deleteTransaction(long transactionId);

    void recoverTransaction(long transactionId);

}
