package com.sj.spending.transaction.service;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class MintTransactionAdapterTest {

    @Test
    public void shouldReturnNullTransactionDateWhenDateIsNull() {
        MintTransaction mintTransaction = new MintTransaction();
        mintTransaction.setDate(null);

        MintTransactionAdapter adapter = new MintTransactionAdapter(mintTransaction);

        assertThat(adapter.getTransactionDate(), is(nullValue()));
    }

    @Test
    public void shouldReturnNullTransactionDateWhenDateIsPresentAndValid() {
        MintTransaction mintTransaction = new MintTransaction();
        mintTransaction.setDate("Jun 29");

        MintTransactionAdapter adapter = new MintTransactionAdapter(mintTransaction);

        assertThat(adapter.getTransactionDate(), is(Date.from(LocalDate.of(2018, 6, 29).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }


}