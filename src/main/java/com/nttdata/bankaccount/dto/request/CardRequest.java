package com.nttdata.bankaccount.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardRequest {

    @JsonProperty(value = "name")
    private String name;

}
