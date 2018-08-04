package com.sj.spending.transaction.data.entity;

import com.sj.spending.logic.data.Currency;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TransactionEntityTest {

    @Test
    public void shouldSetAndGetCorrectFields() {
        TransactionEntity entity = new TransactionEntity();
        entity.setCategoryId(1L);
        entity.setDeleted(true);
        entity.setDescription("Transaction Description");
        entity.setNotes("Transaction Notes");
        entity.setTransactionType(TransactionType.CREDIT);
        entity.setTransactionId(2L);

        Money amount = new Money(Currency.CAD, BigDecimal.TEN);
        entity.setAmount(amount);

        Date transactionDate = Date.from(Instant.now());
        entity.setTransactionDate(transactionDate);

        assertThat(entity.getCategoryId(), is(1L));
        assertThat(entity.isDeleted(), is(true));
        assertThat(entity.getDescription(), is("Transaction Description"));
        assertThat(entity.getNotes(), is("Transaction Notes"));
        assertThat(entity.getAmount(), is(amount));
        assertThat(entity.getTransactionType(), is(TransactionType.CREDIT));
        assertThat(entity.getTransactionId(), is(2L));
        assertThat(entity.getTransactionDate(), is(transactionDate));
    }

}
