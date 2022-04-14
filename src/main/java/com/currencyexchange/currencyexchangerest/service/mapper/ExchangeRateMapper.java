package com.currencyexchange.currencyexchangerest.service.mapper;

import com.currencyexchange.currencyexchangerest.model.ExchangeRateDto;
import com.currencyexchange.currencyexchangerest.model.ExchangeRateEntity;
import com.currencyexchange.currencyexchangerest.model.api.ExchangeRateApi;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapper {

    public ExchangeRateEntity toEntity(ExchangeRateApi api) {
        ExchangeRateEntity entity = new ExchangeRateEntity();
        entity.setBase(api.getQuery().getBase());
        entity.setTarget(api.getQuery().getTarget());
        entity.setDate(api.getDate());
        entity.setResult(api.getResult());
        return entity;
    }

    public ExchangeRateDto apiToDto(ExchangeRateApi api) {
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(api.getQuery().getBase());
        dto.setTarget(api.getQuery().getTarget());
        dto.setDate(api.getDate());
        dto.setResult(api.getResult());
        return dto;
    }

    public ExchangeRateDto entityToDto(ExchangeRateEntity entity) {
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setBase(entity.getBase());
        dto.setTarget(entity.getTarget());
        dto.setDate(entity.getDate());
        dto.setResult(entity.getResult());
        return dto;
    }
}
