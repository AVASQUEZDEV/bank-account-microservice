package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.model.Card;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface ICardService {

    Flux<Card> findAll();

    Mono<Card> findById(String id);

    Mono<Card> create(CardRequest request);

    Mono<Card> update(String id, CardRequest request);

    Mono<Void> deleteById(String id);

}
