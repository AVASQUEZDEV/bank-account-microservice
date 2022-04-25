package com.nttdata.bankaccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * This class defines the model of bank accounts
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bank_accounts")
public class BankAccount {

    @Id
    private String id;

    @Field(name = "card_number", write = Field.Write.NON_NULL)
    private String cardNumber;

    @Field(name = "security_code", write = Field.Write.NON_NULL)
    private Long securityCode;

    @Field(name = "expiration_date", write = Field.Write.NON_NULL)
    private Date expirationDate;

    @Field(name = "balance", write = Field.Write.NON_NULL)
    private Float balance;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(name = "created_at")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(name = "updated_at")
    private Date updatedAt;

    public BankAccount(String cardNumber, Long securityCode, Date expirationDate, Float balance, Date createdAt, Date updatedAt) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
