package com.currencyexchange.currencyexchangerest.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExchangeRateApi {

    private LocalDate date;
    private Double result;
    private Query query;
}