package com.currencyexchange.currencyexchangerest.repository;

import com.currencyexchange.currencyexchangerest.model.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Integer> {
    Optional<ExchangeRateEntity> findByBaseAndTargetAndDate(String base, String target, LocalDate date);
}
