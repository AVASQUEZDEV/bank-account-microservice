package com.nttdata.bankaccount.repository;

import com.nttdata.bankaccount.model.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface defines the repository for cards
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface ICardRepository extends ReactiveMongoRepository<Card, String> {
}
