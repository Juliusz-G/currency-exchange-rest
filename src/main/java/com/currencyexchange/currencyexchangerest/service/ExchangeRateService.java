package com.currencyexchange.currencyexchangerest.service;

import com.currencyexchange.currencyexchangerest.exception.NoExchangeRateFoundInApiException;
import com.currencyexchange.currencyexchangerest.exception.NoExchangeRateFoundInDatabaseException;
import com.currencyexchange.currencyexchangerest.model.ExchangeRateDto;
import com.currencyexchange.currencyexchangerest.model.ExchangeRateEntity;
import com.currencyexchange.currencyexchangerest.repository.ExchangeRateRepository;
import com.currencyexchange.currencyexchangerest.service.mapper.ExchangeRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public void create(String base, String target, String date) {
        repository.save(
                mapper.toEntity(
                        integrationService.getExchangeRate(
                                base,
                                target,
                                date
                        )
                )
        );
    }

    //Current Data or Historical Data
    public ExchangeRateDto getExchangeRate(String base, String target, String date) {
        Optional<ExchangeRateEntity> entity = repository.findByBaseAndTargetAndDate(base, target, stringToLocalDate(date));

        if (entity.isPresent()) {
            return mapper.entityToDto(entity.get());
        }

        try {
            create(base, target, date);
            return mapper.apiToDto(integrationService.getExchangeRate(base, target, date));
        } catch (RuntimeException e) {
            throw new NoExchangeRateFoundInApiException();
        }
    }

    //Historical interval Data
    public List<ExchangeRateDto> getHistoricalIntervalExchangeRates(String base, String target, String from, String to) {
        List<LocalDate> dates = getDatesInterval(LocalDate.parse(from), LocalDate.parse(to));
        List<ExchangeRateDto> dtoList = new ArrayList<>();

        for (int i = 0; i < dates.size() - 1; i++) {
            ExchangeRateDto dto = getExchangeRate(base, target, dates.get(i).toString());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<LocalDate> getDatesInterval(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    public LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date);
    }

    public ExchangeRateDto deleteExchangeRate(String base, String target, String date) {
        Optional<ExchangeRateEntity> entity = repository.findByBaseAndTargetAndDate(base, target, stringToLocalDate(date));
        entity.ifPresent(repository::delete);
        return mapper.entityToDto(entity.orElseThrow(() -> {
            throw new NoExchangeRateFoundInDatabaseException();
        }));
    }
}
