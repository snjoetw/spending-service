package com.sj.spending.logic.data;

public enum Currency {

    USD(2),
    CAD(2),
    TWD(0);

    private final int decimalPlaces;

    Currency(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

}
