package com.currencyexchange.currencyexchangerest.exception;

import org.springframework.web.client.RestClientException;

public class NoExchangeRateFoundInApiException extends RestClientException {
    public NoExchangeRateFoundInApiException() {
        super("Exchange rate not found!");
    }
}
