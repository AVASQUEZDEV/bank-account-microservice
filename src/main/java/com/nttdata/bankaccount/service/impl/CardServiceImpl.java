package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.repository.ICardRepository;
import com.nttdata.bankaccount.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of cards
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class CardServiceImpl implements ICardService {

    private final ICardRepository cardRepository;

    /**
     * This method returns a list of cards
     *
     * @return cards list
     */
    @Override
    public Flux<Card> findAll() {
        return cardRepository.findAll();
    }

    /**
     * This method creates an card
     *
     * @param cardRequest request for create new card
     * @return card created
     */
    @Override
    public Mono<Card> create(Card cardRequest) {
        return cardRepository.save(cardRequest);
    }

    /**
     * This method updates an card
     *
     * @param id          card id to update
     * @param cardRequest request for update card
     * @return card updated
     */
    @Override
    public Mono<Card> update(String id, Card cardRequest) {
        return findAll().filter(c -> c.getId().equals(id))
                .single()
                .flatMap(c -> {
                    c.setName(cardRequest.getName());
                    c.setUpdatedAt(cardRequest.getUpdatedAt());
                    return cardRepository.save(c);
                });
    }

    /**
     * This method delete an card
     *
     * @param id card id to delete
     * @return a boolean value
     */
    @Override
    public Mono<Void> delete(String id) {
        return cardRepository.deleteById(id);
    }
}
