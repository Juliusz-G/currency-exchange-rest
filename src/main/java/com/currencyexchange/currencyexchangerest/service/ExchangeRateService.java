package com.currencyexchange.currencyexchangerest.service;

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

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateApiIntegrationService exchangeRateApiIntegrationService;
    private final ExchangeRateMapper exchangeRateMapper;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository,
                               ExchangeRateApiIntegrationService exchangeRateApiIntegrationService,
                               ExchangeRateMapper exchangeRateMapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateApiIntegrationService = exchangeRateApiIntegrationService;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    public void create(String base, String target, String date) {
        exchangeRateRepository.save(
                exchangeRateMapper.toEntity(
                        exchangeRateApiIntegrationService.getExchangeRate(base, target, date)
                )
        );
    }

    //Current Data or Historical Data
    public ExchangeRateDto getExchangeRate(String base, String target, String date) {
        Optional<ExchangeRateEntity> entity = exchangeRateRepository.findByBaseAndTargetAndDate(base, target,
                stringToLocalDate(date));

        if (entity.isPresent()) {
            return exchangeRateMapper.entityToDto(entity.get());
        }
        create(base, target, date);

        return exchangeRateMapper.apiToDto(
                exchangeRateApiIntegrationService.getExchangeRate
                        (base, target, date)
        );
    }

    //Historical interval Data
    public List<ExchangeRateDto> getHistoricalIntervalExchangeRates(String base, String target, String from, String to) {
        List<LocalDate> localDateList = getDatesInterval(LocalDate.parse(from), LocalDate.parse(to));
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();

        for (int i = 0; i < localDateList.size() - 1; i++) {
            ExchangeRateDto exchangeRateDto = getExchangeRate(base, target, localDateList.get(i).toString());
            exchangeRateDtoList.add(exchangeRateDto);
        }

        return exchangeRateDtoList;
    }

    public List<LocalDate> getDatesInterval(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    public LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date);
    }
}
