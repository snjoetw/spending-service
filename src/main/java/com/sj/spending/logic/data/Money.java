package com.sj.spending.logic.data;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

public class Money {
    private Currency currency;
    private BigDecimal amount;

    public Money() {
        this(null, null);
    }

    public Money(Currency currency, int amount) {
        this(currency, new BigDecimal(amount));
    }

    public Money(Currency currency, double amount) {
        this(currency, new BigDecimal(amount));
    }

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Money plus(Money other) {
        if (getCurrency() != other.getCurrency()) {
            throw new IllegalStateException("Cannot do plus operation with different currency, " + getCurrency() + " vs " + other.getCurrency());
        }
        return new Money(getCurrency(), getAmount().add(other.getAmount()));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("currency", getCurrency())
                .add("amount", getAmount())
                .toString();
    }

}
