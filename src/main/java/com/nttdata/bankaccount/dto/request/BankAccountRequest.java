package com.nttdata.bankaccount.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class defines the request of bank account
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountRequest {

    private String clientId;

    private String productId;

    private String cardId;

    private Float balance;

}
