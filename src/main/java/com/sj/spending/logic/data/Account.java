package com.sj.spending.logic.data;

public enum Account {

    TD_VISA(Currency.CAD),
    TD_US_VISA(Currency.CAD),
    COSTCO_MASTERCARD(Currency.CAD),
    TANGERINE_MASTERCARD(Currency.CAD),
    SCOTIA_VISA(Currency.CAD),
    AMEX_CANADA(Currency.CAD),
    UNKNOWN(Currency.CAD),

    BOA_VISA(Currency.USD),
    AMEX_USA(Currency.USD);

    private final Currency currency;

    private Account(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}
