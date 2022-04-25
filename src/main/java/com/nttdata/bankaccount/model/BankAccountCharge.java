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
 * This class defines the model of bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bank_account_charges")
public class BankAccountCharge {

    @Id
    private String id;

    @Field(name = "commission")
    private Float commission;

    @Field(name = "movements_quantity")
    private Long movementsQuantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(name = "created_at")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(name = "updated_at")
    private Date updatedAt;

    public BankAccountCharge(Float commission, Long movementsQuantity, Date createdAt, Date updatedAt) {
        this.commission = commission;
        this.movementsQuantity = movementsQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
