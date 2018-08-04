package com.sj.spending.transaction.data.repo;

import com.sj.spending.logic.data.Currency;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;
import com.sj.spending.transaction.data.entity.TransactionEntity;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.sj.spending.transaction.CommonTestHelper.createCategory;
import static com.sj.spending.transaction.CommonTestHelper.createTransactionEntity;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        categoryRepository.save(createCategory("Drinks", null, false));

        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-01-01"), 1L, "Bubble World", new Money(Currency.CAD, BigDecimal.TEN)));
        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-01-31"), 1L, "Starbucks", new Money(Currency.CAD, BigDecimal.ONE)));
    }

    @Test
    @Ignore
    public void shouldReturnAllTransactionsModifiedAfterLastModifiedDate() {
        Date lastModifiedDate = Date.from(Instant.now().minusNanos(1));

        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-01-31"), 1L, "Starbucks", new Money(Currency.CAD, BigDecimal.TEN)));
        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-02-01"), 1L, "BBT", new Money(Currency.CAD, BigDecimal.ONE)));

        List<TransactionEntity> transactions = transactionRepository.findByModifiedAtAfter(lastModifiedDate);

        assertThat(transactions, hasSize(2));
    }

    @Test
    @Ignore
    public void shouldReturnAllTransactionsBetweenSpecifiedDatesAndModifiedAfterLastModifiedDate() {
        Date lastModifiedDate = Date.from(Instant.now().minusMillis(1));

        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-01-31"), 1L, "Starbucks", new Money(Currency.CAD, BigDecimal.TEN)));
        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-02-01"), 1L, "BBT", new Money(Currency.CAD, BigDecimal.ONE)));

        List<TransactionEntity> transactions = transactionRepository.findByTransactionDateBetweenAndModifiedAtAfter(dateOf("2018-01-01"), dateOf("2018-02-01"), lastModifiedDate);

        assertThat(transactions, hasSize(1));
    }

    @Test
    public void shouldReturnAllTransactionsBetweenSpecifiedDates() {
        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-01-31"), 1L, "Starbucks", new Money(Currency.CAD, BigDecimal.TEN)));
        transactionRepository.save(createTransactionEntity(TransactionType.DEBIT, dateOf("2018-02-01"), 1L, "BBT", new Money(Currency.CAD, BigDecimal.ONE)));

        List<TransactionEntity> transactions = transactionRepository.findByTransactionDateBetween(dateOf("2018-01-01"), dateOf("2018-02-01"));

        assertThat(transactions, hasSize(3));
    }

    private Date dateOf(String localDateStr) {
        LocalDate localDate = LocalDate.parse(localDateStr);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
