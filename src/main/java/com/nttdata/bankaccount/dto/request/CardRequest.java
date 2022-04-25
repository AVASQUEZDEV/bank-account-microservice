package com.nttdata.bankaccount.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This class defines the request of card
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
public class CardRequest {

    @JsonProperty(value = "name")
    private String name;

}
