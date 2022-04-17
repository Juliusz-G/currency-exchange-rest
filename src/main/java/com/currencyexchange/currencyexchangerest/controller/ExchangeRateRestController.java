package com.currencyexchange.currencyexchangerest.controller;

import com.currencyexchange.currencyexchangerest.model.ExchangeRateDto;
import com.currencyexchange.currencyexchangerest.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
public class ExchangeRateRestController {

    public static final String DATE_FORMAT = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
    private final ExchangeRateService service;

    @Autowired
    public ExchangeRateRestController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping("/current/{base}/{target}")
    public ResponseEntity<ExchangeRateDto> getCurrentExchangeRate(
            @PathVariable String base,
            @PathVariable String target) {
        return ResponseEntity.ok()
                .body(service.getExchangeRate(base, target, String.valueOf(LocalDate.now())));

    }

    @GetMapping("/historical/{base}/{target}/{date}")
    public ResponseEntity<ExchangeRateDto> getHistoricalExchangeRate(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable @Pattern(regexp = DATE_FORMAT) String date) {
        return ResponseEntity.ok()
                .body(service.getExchangeRate(base, target, date));

    }

    @GetMapping("/historical-interval/{base}/{target}/{from}/{to}")
    public ResponseEntity<List<ExchangeRateDto>> getHistoricalIntervalExchangeRate(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable @Pattern(regexp = DATE_FORMAT) String from,
            @PathVariable @Pattern(regexp = DATE_FORMAT) String to) {
        return ResponseEntity.ok()
                .body(service.getHistoricalIntervalExchangeRates(base, target, from, to));
    }

    @DeleteMapping("/delete/{base}/{target}/{date}")
    public ResponseEntity<ExchangeRateDto> deleteExchangeRate(
            @PathVariable String base,
            @PathVariable String target,
            @PathVariable @Pattern(regexp = DATE_FORMAT) String date) {
        return ResponseEntity.ok()
                .body(service.deleteExchangeRate(base, target, date));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAmountOfRecords() {
        return ResponseEntity.ok()
                .body(service.getAmountOfRecords());
    }

}
