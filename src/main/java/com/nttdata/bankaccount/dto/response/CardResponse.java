package com.nttdata.bankaccount.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CardResponse {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "id")
    private String name;

    @JsonProperty(value = "id")
    private Date createdAt;

    @JsonProperty(value = "id")
    private Date updatedAt;

    public CardResponse(String id, String name, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

