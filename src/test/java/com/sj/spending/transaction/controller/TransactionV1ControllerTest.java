package com.sj.spending.transaction.controller;

import com.sj.spending.logic.data.Currency;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;
import com.sj.spending.transaction.service.TransactionService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

import static com.sj.spending.transaction.CommonTestHelper.createTransactionDto;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionV1Controller.class)
public class TransactionV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldReturnTransactionsWhenFindingTransactionsWithYearMonthAndNoLastModified() throws Exception {
        when(transactionService.findTransactions(LocalDate.parse("2018-06-01"), LocalDate.parse("2018-06-30"), null))
                .thenReturn(Lists.newArrayList(
                        createTransactionDto(1L, TransactionType.DEBIT, Date.from(Instant.now()), 1, "BBT", new Money(Currency.CAD, 5), false),
                        createTransactionDto(2L, TransactionType.CREDIT, Date.from(Instant.now()), 11, "Income", new Money(Currency.CAD, 1000), false)
                ));

        this.mockMvc.perform(get("/api/v1/transactions?ym=2018-06"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId", is(1)))
                .andExpect(jsonPath("$[0].transactionType", is("DEBIT")))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[0].description", is("BBT")))
                .andExpect(jsonPath("$[0].amount.currency", is("CAD")))
                .andExpect(jsonPath("$[0].amount.amount", is(5)))
                .andExpect(jsonPath("$[0].notes", is(nullValue())))
                .andExpect(jsonPath("$[0].deleted", is(false)))
                .andExpect(jsonPath("$[1].transactionId", is(2)))
                .andExpect(jsonPath("$[1].transactionType", is("CREDIT")))
                .andExpect(jsonPath("$[1].categoryId", is(11)))
                .andExpect(jsonPath("$[1].description", is("Income")))
                .andExpect(jsonPath("$[1].amount.currency", is("CAD")))
                .andExpect(jsonPath("$[1].amount.amount", is(1000)))
                .andExpect(jsonPath("$[1].notes", is(nullValue())))
                .andExpect(jsonPath("$[1].deleted", is(false)));
    }

    @Test
    public void shouldReturnTransactionsWhenFindingTransactionsWithYearMonthAndLastModified() throws Exception {
        when(transactionService.findTransactions(LocalDate.parse("2018-06-01"), LocalDate.parse("2018-06-30"), 1000L))
                .thenReturn(Lists.newArrayList(
                        createTransactionDto(1L, TransactionType.DEBIT, Date.from(Instant.now()), 1, "BBT", new Money(Currency.CAD, 5), false),
                        createTransactionDto(2L, TransactionType.CREDIT, Date.from(Instant.now()), 11, "Income", new Money(Currency.CAD, 1000), false)
                ));

        this.mockMvc.perform(get("/api/v1/transactions?ym=2018-06&l=1000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId", is(1)))
                .andExpect(jsonPath("$[1].transactionId", is(2)));
    }

}
