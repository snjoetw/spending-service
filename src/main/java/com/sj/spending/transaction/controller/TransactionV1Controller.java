package com.sj.spending.transaction.controller;

import com.sj.spending.transaction.data.dto.TransactionDto;
import com.sj.spending.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionV1Controller {

    private final TransactionService transactionService;

    @Autowired
    public TransactionV1Controller(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.GET)
    public TransactionDto findTransaction(@PathVariable(value = "transactionId") long transactionId) {
        return transactionService.findTransaction(transactionId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TransactionDto> findTransactions(
            @RequestParam("ym") @DateTimeFormat(pattern = "yyyy-M") YearMonth yearMonth,
            @RequestParam(value = "l", required = false) Long lastModified) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return transactionService.findTransactions(startDate, endDate, lastModified);
    }

    @RequestMapping(method = RequestMethod.POST)
    public TransactionDto addTransaction(@RequestBody TransactionDto transaction) {
        return transactionService.addTransaction(transaction);
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.PUT)
    public TransactionDto updateTransaction(@RequestBody TransactionDto transaction) {
        return transactionService.updateTransaction(transaction);
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.DELETE)
    public void deleteTransaction(@PathVariable(value = "transactionId") long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

}
