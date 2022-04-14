package com.currencyexchange.currencyexchangerest.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Query {

    @JsonProperty("from")
    private String base;
    @JsonProperty("to")
    private String target;
}