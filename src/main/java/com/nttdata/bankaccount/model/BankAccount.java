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

    @Field(name = "client_id", write = Field.Write.NON_NULL)
    private String clientId;

    @Field(name = "product_id", write = Field.Write.NON_NULL)
    private String productId;

    @Field(name = "card_id", write = Field.Write.NON_NULL)
    private String cardId;

    @Field(name = "balance", write = Field.Write.NON_NULL)
    private Float balance;

    @Field(name = "created_at")
    private Date createdAt;

    @Field(name = "updated_at")
    private Date updatedAt;

    public BankAccount(String clientId, String productId, Float balance, Date createdAt, Date updatedAt) {
        this.clientId = clientId;
        this.productId = productId;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
