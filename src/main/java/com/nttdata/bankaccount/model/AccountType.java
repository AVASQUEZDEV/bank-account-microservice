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
@Document(collection = "account_types")
public class AccountType {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "commission")
    private Float commission;

    @Field(name = "movements_quantity")
    private String movementsQuantity;

    @Field(name = "frequency")
    private String frequency = "Mensual";

    @Field(name = "created_at")
    private Date createdAt;

    @Field(name = "updated_at")
    private Date updatedAt;

    public AccountType(String name, Float commission,
                       String movementsQuantity, Date createdAt,
                       Date updatedAt) {
        this.name = name;
        this.commission = commission;
        this.movementsQuantity = movementsQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
