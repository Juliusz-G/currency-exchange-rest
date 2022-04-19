package com.currencyexchange.currencyexchangerest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExchangeRateExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoExchangeRateFoundInDatabaseException.class)
    public ErrorMessage handleNotFoundInDbException(final NoExchangeRateFoundInDatabaseException exception) {
        log.error("\u001B[31mExchange rate not found in database!\033[0m");
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoExchangeRateFoundInApiException.class)
    public ErrorMessage handleNotFoundInApiException(final NoExchangeRateFoundInApiException exception) {
        log.error("\u001B[31mExchange rate not found in api!\033[0m");
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectDateFormatException.class)
    public ErrorMessage handleIncorrectDateFormatException(final IncorrectDateFormatException exception) {
        log.error("\u001B[31mIncorrect date format!\033[0m");
        return new ErrorMessage(exception.getMessage());
    }

}
