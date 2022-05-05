package com.nttdata.bankaccount.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * This class defines the request of bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
public class CardRequest {

    private String cardNumber;

    private Long securityCode;

    private Date expirationDate;

    private String cci;

    private Float balance;

    private String bankName;

}
