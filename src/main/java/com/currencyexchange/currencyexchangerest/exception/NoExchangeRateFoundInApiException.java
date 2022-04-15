package com.currencyexchange.currencyexchangerest.exception;

public class NoExchangeRateFoundInApiException extends RuntimeException {
    public NoExchangeRateFoundInApiException() {
        super("Exchange rate not found!");
    }
}
