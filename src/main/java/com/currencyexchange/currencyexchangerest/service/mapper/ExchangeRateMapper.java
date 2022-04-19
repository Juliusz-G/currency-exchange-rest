package com.currencyexchange.currencyexchangerest.service.mapper;

import com.currencyexchange.currencyexchangerest.model.ExchangeRateDto;
import com.currencyexchange.currencyexchangerest.model.ExchangeRateEntity;
import com.currencyexchange.currencyexchangerest.model.api.ExchangeRateApi;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapper {

    public ExchangeRateEntity apiToEntity(ExchangeRateApi api) {
        ExchangeRateEntity entity = new ExchangeRateEntity();
        entity.setBase(api.getQuery().getBase());
        entity.setTarget(api.getQuery().getTarget());
        entity.setDate(api.getDate());
        entity.setResult(api.getResult());
        return entity;
    }

    public ExchangeRateDto entityToDto(ExchangeRateEntity entity) {
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(entity.getBase());
        dto.setTarget(entity.getTarget());
        dto.setDate(entity.getDate());
        dto.setResult(entity.getResult());
        dto.setViews(entity.getViews());
        return dto;
    }
}
