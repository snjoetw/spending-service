package com.sj.spending.transaction.service;

import com.sj.spending.transaction.data.dto.TransactionSummaryDto;

import java.time.LocalDate;
import java.util.Collection;

public interface TransactionSummaryService {

    Collection<TransactionSummaryDto> getTransactionSummaries(LocalDate start, LocalDate end);

}
