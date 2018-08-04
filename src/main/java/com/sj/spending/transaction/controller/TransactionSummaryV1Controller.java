package com.sj.spending.transaction.controller;

import com.sj.spending.transaction.data.dto.TransactionSummaryDto;
import com.sj.spending.transaction.service.TransactionSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/transactionSummaries")
public class TransactionSummaryV1Controller {

    private final TransactionSummaryService transactionSummaryService;

    @Autowired
    public TransactionSummaryV1Controller(TransactionSummaryService transactionSummaryService) {
        this.transactionSummaryService = transactionSummaryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<TransactionSummaryDto> findTransactionSummaries(
            @RequestParam("ym") @DateTimeFormat(pattern = "yyyy-M") YearMonth yearMonth,
            @RequestParam(value = "l", required = false) Long lastModified) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return transactionSummaryService.getTransactionSummaries(startDate, endDate);
    }

}
