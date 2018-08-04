package com.sj.spending.transaction.data.dto;

import com.google.common.collect.Maps;
import com.sj.spending.logic.data.Currency;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionSummaryDto {

    private final TransactionType transactionType;

    private final List<TransactionSummaryItemDto> summaryItems;

    private final List<Money> amounts;

    private TransactionSummaryDto(Builder builder) {
        this.transactionType = builder.transactionType;
        this.summaryItems = builder.summaryItemByCategoryId.values().stream()
                .map(i -> i.build())
                .collect(Collectors.toList());
        this.amounts = builder.amountByCurrency.values().stream().collect(Collectors.toList());
    }

    public static Builder newBuilder(TransactionType transactionType) {
        return new Builder(transactionType);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public List<TransactionSummaryItemDto> getSummaryItems() {
        return summaryItems;
    }

    public List<Money> getAmounts() {
        return amounts;
    }

    public static class Builder {

        private final TransactionType transactionType;
        private final Map<Long, TransactionSummaryItemDto.Builder> summaryItemByCategoryId;
        private final Map<Currency, Money> amountByCurrency;

        private Builder(TransactionType transactionType) {
            this.transactionType = transactionType;
            this.summaryItemByCategoryId = Maps.newHashMap();
            this.amountByCurrency = Maps.newHashMap();
        }

        public Builder add(Long categoryId, Money amount) {
            TransactionSummaryItemDto.Builder summaryItem = summaryItemByCategoryId.get(categoryId);

            if (summaryItem == null) {
                summaryItem = TransactionSummaryItemDto.newBuilder(categoryId, amount);
                summaryItemByCategoryId.put(categoryId, summaryItem);
            } else {
                summaryItem.add(amount);
            }

            accumulateTotalAmount(amount);

            return this;
        }

        private void accumulateTotalAmount(Money amount) {
            Money total = amountByCurrency.get(amount.getCurrency());

            if (total == null) {
                total = amount;
            } else {
                total = total.plus(amount);
            }

            amountByCurrency.put(amount.getCurrency(), total);
        }

        public TransactionSummaryDto build() {
            return new TransactionSummaryDto(this);
        }

    }

}
