package com.sj.spending.transaction.service;

import com.sj.spending.logic.data.Transaction;
import com.sj.spending.transaction.data.dto.TransactionDto;
import com.sj.spending.transaction.data.entity.TransactionEntity;
import com.sj.spending.transaction.data.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Function<LocalDate, Date> TO_DATE = local -> Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());

    private final TransactionRepository repository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public List<TransactionDto> findTransactions(LocalDate start, LocalDate end) {
        Date startDate = TO_DATE.apply(start);
        Date endDate = TO_DATE.apply(end);

        return repository.findByTransactionDateBetween(startDate, endDate).stream()
                .map(t -> TransactionDto.from(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findTransactions(LocalDate start, LocalDate end, Long lastUpdateInMillis) {
        if (lastUpdateInMillis == null) {
            return findTransactions(start, end);
        }

        Date startDate = TO_DATE.apply(start);
        Date endDate = TO_DATE.apply(end);
        Date lastUpdateDate = new Date(lastUpdateInMillis);

        return repository.findByTransactionDateBetweenAndModifiedAtAfter(startDate, endDate, lastUpdateDate).stream()
                .map(t -> TransactionDto.from(t))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findTransaction(long transactionId) {
        TransactionEntity transaction = repository.findById(transactionId).get();
        return TransactionDto.from(transaction);
    }

    @Override
    public TransactionDto addTransaction(TransactionDto transaction) {
        if (transaction.getTransactionId() != null) {
            throw new IllegalStateException("Cannot add new transaction with transactionId specified: " + transaction);
        }

        TransactionEntity entity = repository.save(TransactionEntity.from(transaction));
        publisher.publishEvent(new TransactionChangeEvent(this, entity));

        return TransactionDto.from(entity);
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto transaction) {
        TransactionEntity entity = repository.findById(transaction.getTransactionId()).get();
        Transaction.copy(transaction, entity);


        TransactionEntity saved = repository.save(entity);
        publisher.publishEvent(new TransactionChangeEvent(this, saved));
        return TransactionDto.from(saved);
    }

    @Override
    public void deleteTransaction(long transactionId) {
        TransactionEntity transaction = repository.findById(transactionId).get();
        transaction.setDeleted(true);

        TransactionEntity entity = repository.save(transaction);
        publisher.publishEvent(new TransactionChangeEvent(this, entity));
    }

    @Override
    public void recoverTransaction(long transactionId) {
        TransactionEntity transaction = repository.findById(transactionId).get();
        transaction.setDeleted(false);

        TransactionEntity entity = repository.save(transaction);
        publisher.publishEvent(new TransactionChangeEvent(this, entity));
    }

}
