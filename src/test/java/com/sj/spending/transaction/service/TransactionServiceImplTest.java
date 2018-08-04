package com.sj.spending.transaction.service;

import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;
import com.sj.spending.transaction.data.dto.TransactionDto;
import com.sj.spending.transaction.data.entity.TransactionEntity;
import com.sj.spending.transaction.data.repo.TransactionRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.sj.spending.logic.data.Currency.CAD;
import static com.sj.spending.transaction.CommonTestHelper.assertTransaction;
import static com.sj.spending.transaction.CommonTestHelper.createTransactionDto;
import static com.sj.spending.transaction.CommonTestHelper.createTransactionEntity;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    private static final long TRANSACTION_ID = 10L;
    private static TransactionType TRANSACTION_TYPE = TransactionType.DEBIT;
    private static final Date TRANSACTION_DATE = Date.from(Instant.now());
    private static final String TRANSACTION_DESCRIPTION = "Starbucks";
    private static final Money TRANSACTION_AMOUNT = new Money(CAD, 5);
    private static final long CATEGORY_ID = 1L;
    private static final Supplier<TransactionEntity> TRANSACTION_ENTITY_SUPPLIER = () -> createTransactionEntity(
            TRANSACTION_ID,
            TRANSACTION_TYPE,
            TRANSACTION_DATE,
            CATEGORY_ID,
            TRANSACTION_DESCRIPTION,
            TRANSACTION_AMOUNT,
            false);

    private static LocalDate START_LOCAL_DATE = LocalDate.parse("2018-01-01");
    private static LocalDate END_LOCAL_DATE = LocalDate.parse("2018-02-01");

    private static Date FIND_TRANSACTION_START_DATE = Date.from(START_LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());
    private static Date FIND_TRANSACTION_END_DATE = Date.from(END_LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Mock
    private TransactionRepository repository;

    @Mock
    private ApplicationEventPublisher publisher;

    private TransactionService service;

    @Before
    public void setUp() {
        this.service = new TransactionServiceImpl(repository, publisher);
    }

    @Test
    public void findTransactionsShouldReturnTransactions() {
        when(repository.findByTransactionDateBetween(FIND_TRANSACTION_START_DATE, FIND_TRANSACTION_END_DATE)).thenReturn(Lists.newArrayList(TRANSACTION_ENTITY_SUPPLIER.get()));

        List<TransactionDto> transactions = service.findTransactions(START_LOCAL_DATE, END_LOCAL_DATE);

        assertThat(transactions, hasSize(1));
    }

    @Test
    public void findTransactionsWithLastUpdatedTimeShouldReturnTransactions() {
        service.findTransactions(START_LOCAL_DATE, END_LOCAL_DATE, 1L);

        verify(repository).findByTransactionDateBetweenAndModifiedAtAfter(FIND_TRANSACTION_START_DATE, FIND_TRANSACTION_END_DATE, new Date(1L));
    }

    @Test
    public void findTransactionShouldReturnTransaction() {
        when(repository.findById(TRANSACTION_ID)).thenReturn(Optional.of(TRANSACTION_ENTITY_SUPPLIER.get()));

        TransactionDto dto = service.findTransaction(TRANSACTION_ID);
        assertTransaction(dto, TRANSACTION_ID, TRANSACTION_TYPE, TRANSACTION_DATE, CATEGORY_ID, TRANSACTION_DESCRIPTION, TRANSACTION_AMOUNT, false);
    }

    @Test
    public void addTransactionShouldSaveTransaction() {
        when(repository.save(any())).thenAnswer(args -> {
            TransactionEntity entity = args.getArgument(0);
            entity.setTransactionId(TRANSACTION_ID);
            return entity;
        });

        TransactionDto saved = service.addTransaction(createTransactionDto(
                null,
                TRANSACTION_TYPE,
                TRANSACTION_DATE,
                CATEGORY_ID,
                TRANSACTION_DESCRIPTION,
                TRANSACTION_AMOUNT,
                false));

        verify(repository).save(any());

        assertTransaction(saved, TRANSACTION_ID, TRANSACTION_TYPE, TRANSACTION_DATE, CATEGORY_ID, TRANSACTION_DESCRIPTION, TRANSACTION_AMOUNT, false);
    }

    @Test(expected = IllegalStateException.class)
    public void addTransactionWithExistingTransactionIdShouldThrowException() {
        service.addTransaction(createTransactionDto(
                TRANSACTION_ID,
                TRANSACTION_TYPE,
                TRANSACTION_DATE,
                CATEGORY_ID,
                TRANSACTION_DESCRIPTION,
                TRANSACTION_AMOUNT,
                false));
    }

    @Test
    public void updateTransactionShouldSaveTransaction() {
        TransactionEntity entity = TRANSACTION_ENTITY_SUPPLIER.get();
        when(repository.findById(TRANSACTION_ID)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        TransactionDto saved = service.updateTransaction(createTransactionDto(
                TRANSACTION_ID,
                TRANSACTION_TYPE,
                TRANSACTION_DATE,
                CATEGORY_ID,
                TRANSACTION_DESCRIPTION,
                TRANSACTION_AMOUNT,
                false));

        verify(repository).save(entity);

        assertTransaction(saved, TRANSACTION_ID, TRANSACTION_TYPE, TRANSACTION_DATE, CATEGORY_ID, TRANSACTION_DESCRIPTION, TRANSACTION_AMOUNT, false);
    }

    @Test
    public void deleteTransactionShouldSetTransactionAsDeleted() {
        when(repository.findById(TRANSACTION_ID)).thenReturn(Optional.of(TRANSACTION_ENTITY_SUPPLIER.get()));

        service.deleteTransaction(TRANSACTION_ID);

        ArgumentCaptor<TransactionEntity> captor = ArgumentCaptor.forClass(TransactionEntity.class);
        verify(repository).save(captor.capture());

        TransactionEntity saved = captor.getValue();
        assertTransaction(saved, TRANSACTION_ID, TRANSACTION_TYPE, TRANSACTION_DATE, CATEGORY_ID, TRANSACTION_DESCRIPTION, TRANSACTION_AMOUNT, true);
    }

    @Test
    public void recoverTransactionShouldSetTransactionAsNotDeleted() {
        when(repository.findById(TRANSACTION_ID)).thenReturn(Optional.of(TRANSACTION_ENTITY_SUPPLIER.get()));

        service.recoverTransaction(TRANSACTION_ID);

        ArgumentCaptor<TransactionEntity> captor = ArgumentCaptor.forClass(TransactionEntity.class);
        verify(repository).save(captor.capture());

        TransactionEntity saved = captor.getValue();
        assertTransaction(saved, TRANSACTION_ID, TRANSACTION_TYPE, TRANSACTION_DATE, CATEGORY_ID, TRANSACTION_DESCRIPTION, TRANSACTION_AMOUNT, false);
    }

}
