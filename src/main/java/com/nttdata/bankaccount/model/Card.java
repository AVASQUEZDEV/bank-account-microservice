package com.nttdata.bankaccount.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * This class defines the model of cards
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@Document(collection = "cards")
public class Card {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(name = "created_at")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(name = "updated_at")
    private Date updatedAt;

}
