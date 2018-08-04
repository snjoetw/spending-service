package com.sj.spending.transaction.service;

import com.google.common.collect.Maps;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;
import com.sj.spending.transaction.data.dto.CategoryDto;
import com.sj.spending.transaction.data.dto.TransactionDto;
import com.sj.spending.transaction.data.dto.TransactionSummaryDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public TransactionSummaryServiceImpl(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @Override
    public Collection<TransactionSummaryDto> getTransactionSummaries
            (LocalDate start, LocalDate end) {
        List<TransactionDto> transactions = transactionService.findTransactions(start, end);
        Map<TransactionType, TransactionSummaryDto.Builder> summaryByTransactionType = Maps.newHashMap();
        summaryByTransactionType.put(TransactionType.DEBIT, TransactionSummaryDto.newBuilder(TransactionType.DEBIT));
        summaryByTransactionType.put(TransactionType.CREDIT, TransactionSummaryDto.newBuilder(TransactionType.CREDIT));

        for (TransactionDto transaction : transactions) {
            TransactionSummaryDto.Builder summaryDto = summaryByTransactionType.get(transaction.getTransactionType());
            summarize(summaryDto, transaction.getCategoryId(), transaction.getAmount());
        }

        return summaryByTransactionType.values().stream()
                .map(t -> t.build())
                .collect(Collectors.toList());

    }

    private void summarize(TransactionSummaryDto.Builder summaryDto, Long categoryId, Money amount) {
        if (categoryId == null) {
            return;
        }

        summaryDto.add(categoryId, amount);

        CategoryDto category = categoryService.findCategory(categoryId);
        summarize(summaryDto, category.getParentCategoryId(), amount);
    }

}
