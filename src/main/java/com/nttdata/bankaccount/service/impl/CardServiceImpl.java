package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.dto.mapper.CardMapper;
import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.repository.ICardRepository;
import com.nttdata.bankaccount.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    private final ICardRepository cardRepository;

    private final CardMapper cardMapper;

    /**
     * This method returns a list of cards
     *
     * @return cards list
     */
    @Override
    public Flux<Card> findAll() {
        return cardRepository.findAll()
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findAll]" + e.getMessage());
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "" + e));
                });
    }

    /**
     * This method return one card
     *
     * @param id request param
     * @return card
     */
    @Override
    public Mono<Card> findById(String id) {
        return cardRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * This method creates a card
     *
     * @param request request to create new card
     * @return card created
     */
    @Override
    public Mono<Card> create(CardRequest request) {
        return cardMapper.toPostModel(request)
                .flatMap(cardRepository::save)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * This method updates a card
     *
     * @param id      card id to update
     * @param request request to update card
     * @return card updated
     */
    @Override
    public Mono<Card> update(String id, CardRequest request) {
        return findById(id)
                .flatMap(c -> cardMapper.toPutModel(c, request)
                        .flatMap(cardRepository::save))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e.getMessage());
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * This method delete an card
     *
     * @param id card id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return cardRepository.deleteById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][delete]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
                });
    }
}
