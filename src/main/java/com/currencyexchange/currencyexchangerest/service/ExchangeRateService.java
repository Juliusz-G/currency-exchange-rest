package com.currencyexchange.currencyexchangerest.service;

import com.currencyexchange.currencyexchangerest.exception.IncorrectDateFormatException;
import com.currencyexchange.currencyexchangerest.exception.NoExchangeRateFoundInApiException;
import com.currencyexchange.currencyexchangerest.exception.NoExchangeRateFoundInDatabaseException;
import com.currencyexchange.currencyexchangerest.model.ExchangeRateDto;
import com.currencyexchange.currencyexchangerest.model.ExchangeRateEntity;
import com.currencyexchange.currencyexchangerest.model.api.ExchangeRateApi;
import com.currencyexchange.currencyexchangerest.repository.ExchangeRateRepository;
import com.currencyexchange.currencyexchangerest.service.mapper.ExchangeRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository repository;
    private final ExchangeRateApiIntegrationService integrationService;
    private final ExchangeRateMapper mapper;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository repository,
                               ExchangeRateApiIntegrationService integrationService,
                               ExchangeRateMapper mapper) {
        this.repository = repository;
        this.integrationService = integrationService;
        this.mapper = mapper;
    }

    //Current Data or Historical Data
    public ExchangeRateDto getExchangeRate(String base, String target, String date) {
        Optional<ExchangeRateEntity> entity = repository.findByBaseAndTargetAndDate(base, target, stringToLocalDate(date));

        if (entity.isPresent()) {
            return mapper.entityToDto(saveAndUpdateViews(entity.get()));
        }

        try {
            ExchangeRateApi exchangeRateApi = integrationService.getExchangeRate(base, target, date);
            if (exchangeRateApi.getResult() == 0) {
                throw new NoExchangeRateFoundInApiException();
            }
            return mapper.entityToDto(saveAndUpdateViews(mapper.apiToEntity(exchangeRateApi)));
        } catch (RestClientException e) {
            throw new NoExchangeRateFoundInApiException();
        }
    }

    //Historical interval Data
    public List<ExchangeRateDto> getHistoricalIntervalExchangeRates(String base, String target, String from, String to) {
        List<LocalDate> dates = getDatesInterval(LocalDate.parse(from), LocalDate.parse(to));
        return dates.stream()
                .map(localDate -> getExchangeRate(base, target, localDate.toString()))
                .collect(Collectors.toList());
    }

    public List<LocalDate> getDatesInterval(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
    }

    public LocalDate stringToLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeException e) {
            throw new IncorrectDateFormatException();
        }
    }

    public ExchangeRateDto deleteExchangeRate(String base, String target, String date) {
        Optional<ExchangeRateEntity> entity = repository.findByBaseAndTargetAndDate(base, target, stringToLocalDate(date));
        entity.ifPresent(repository::delete);
        return mapper.entityToDto(entity.orElseThrow(() -> {
            throw new NoExchangeRateFoundInDatabaseException();
        }));
    }

    public long getAmountOfRecords() {
        return repository.count();
    }

    public List<ExchangeRateDto> getAllRecords() {
        return repository.findAll()
                .stream()
                .map(entity -> mapper.entityToDto(saveAndUpdateViews(entity)))
                .collect(Collectors.toList());
    }

    public ExchangeRateEntity saveAndUpdateViews(ExchangeRateEntity entity) {
        entity.setViews(entity.getViews() + 1);
        repository.save(entity);
        return entity;
    }
}
