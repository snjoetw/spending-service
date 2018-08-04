package com.sj.spending.transaction.data.dto;

import com.google.common.collect.Maps;
import com.sj.spending.logic.data.Currency;
import com.sj.spending.logic.data.Money;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionSummaryItemDto {

    private final Long categoryId;

    private final List<Money> amounts;

    private TransactionSummaryItemDto(Builder builder) {
        this.categoryId = builder.categoryId;
        this.amounts = builder.amountByCurrency.values().stream()
                .collect(Collectors.toList());
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<Money> getAmounts() {
        return amounts;
    }

    public static Builder newBuilder(Long categoryId, Money amount) {
        return new Builder(categoryId, amount);
    }

    public static class Builder {

        private final Long categoryId;
        private final Map<Currency, Money> amountByCurrency;

        private Builder(Long categoryId, Money amount) {
            this.categoryId = categoryId;
            this.amountByCurrency = Maps.newHashMap();

            this.add(amount);
        }

        public Builder add(Money amount) {
            Money total = amountByCurrency.get(amount.getCurrency());

            if (total == null) {
                total = amount;
            } else {
                total = total.plus(amount);
            }

            amountByCurrency.put(amount.getCurrency(), total);
            return this;
        }

        public TransactionSummaryItemDto build() {
            return new TransactionSummaryItemDto(this);
        }

    }

}
