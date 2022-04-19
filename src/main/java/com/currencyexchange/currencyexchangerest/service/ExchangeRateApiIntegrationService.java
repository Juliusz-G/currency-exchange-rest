package com.currencyexchange.currencyexchangerest.service;

import com.currencyexchange.currencyexchangerest.model.api.ExchangeRateApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateApiIntegrationService {

    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateApiIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRateApi getExchangeRate(String base, String target, String date) throws RestClientException {
        String exchangeRateApiUrl = "https://api.exchangerate.host/convert?from=%s&to=%s&date=%s";
        return restTemplate.getForObject(
                String.format(
                        exchangeRateApiUrl,
                        base,
                        target,
                        date
                ),
                ExchangeRateApi.class
        );
    }
}
