package com.currencyexchange.currencyexchangerest.service;

import com.currencyexchange.currencyexchangerest.model.api.ExchangeRateApi;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateApiIntegrationService {

    private static final String EXCHANGE_RATE_API_URL = "https://api.exchangerate.host/convert?from=%s&to=%s&date=%s";
    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRateApi getExchangeRate(String base, String target, String date) {
        return restTemplate.getForObject(
                String.format(
                        EXCHANGE_RATE_API_URL,
                        base,
                        target,
                        date
                ),
                ExchangeRateApi.class
        );
    }
}
