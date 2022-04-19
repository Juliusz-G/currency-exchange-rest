package com.currencyexchange.currencyexchangerest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExchangeRateDto {

    private String base;
    private String target;
    private LocalDate date;
    private double result;
    private int views;
}
