package com.currencyexchange.currencyexchangerest.exception;

import java.util.NoSuchElementException;

public class NoExchangeRateFoundInDatabaseException extends NoSuchElementException {
    public NoExchangeRateFoundInDatabaseException() {
        super("Exchange rate not found!");
    }
}
