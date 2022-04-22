package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.model.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of cards
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface ICardService {

    Flux<Card> findAll();

    Mono<Card> create(CardRequest cardRequest);

    Mono<Card> update(String id, Card cardRequest);

    Mono<Void> delete(String id);

}
