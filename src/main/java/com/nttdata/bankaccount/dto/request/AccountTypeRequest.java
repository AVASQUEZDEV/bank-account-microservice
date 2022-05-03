package com.nttdata.bankaccount.dto.request;

import lombok.Data;

/**
 * This class defines the request of bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
public class AccountTypeRequest {

    private String name;

    private Float commission;

    private String movementsQuantity;

}
