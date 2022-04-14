package com.currencyexchange.currencyexchangerest.controller;

import com.currencyexchange.currencyexchangerest.model.ExchangeRateDto;
import com.currencyexchange.currencyexchangerest.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeRateRestController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateRestController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/current/{base}/{target}")
    public ResponseEntity<ExchangeRateDto> getCurrentExchangeRate(
            @PathVariable String base,
            @PathVariable String target) {
        return ResponseEntity.ok()
                .body(exchangeRateService.getExchangeRate(base, target, String.valueOf(LocalDate.now())));
    }

    @GetMapping("/historical/{base}/{target}/{date}")
    public ResponseEntity<ExchangeRateDto> getHistoricalExchangeRate(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {
        return ResponseEntity.ok()
                .body(exchangeRateService.getExchangeRate(base, target, date));
    }

    @GetMapping("/statistical/{base}/{target}/{from}/{to}")
    public ResponseEntity<List<ExchangeRateDto>> getStatisticalExchangeRate(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String from,
            @PathVariable String to) {
        return ResponseEntity.ok()
                .body(exchangeRateService.getHistoricalIntervalExchangeRates(base, target, from, to));
    }

    @DeleteMapping("/delete/{base}/{target}/{date}")
    public ResponseEntity<ExchangeRateDto> deleteCurrency(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable String date) {
        return ResponseEntity.ok()
                .body(exchangeRateService.deleteExchangeRate(base, target, date));
    }

}
