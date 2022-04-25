package com.nttdata.bankaccount.dto.request;

import lombok.Data;

import java.util.Date;

/**
 * This class defines the request of bank account
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
public class BankAccountRequest {

    private String cardNumber;

    private Long securityCode;

    private Date expirationDate;

    private Float balance;

}
