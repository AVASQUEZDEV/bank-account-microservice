package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.dto.mapper.CardTypeMapper;
import com.nttdata.bankaccount.dto.request.CardTypeRequest;
import com.nttdata.bankaccount.exceptions.CustomException;
import com.nttdata.bankaccount.model.CardType;
import com.nttdata.bankaccount.repository.ICardTypeRepository;
import com.nttdata.bankaccount.service.ICardTypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CardTypeServiceImpl implements ICardTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardTypeServiceImpl.class);

    private final ICardTypeRepository cardTypeRepository;

    private final CardTypeMapper cardTypeMapper;

    /**
     * This method returns a list of cards
     *
     * @return cards list
     */
    @Override
    public Flux<CardType> findAll() {
        return cardTypeRepository.findAll()
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findAll]" + e.getMessage());
                    return Mono.error(CustomException.internalServerError("Internal Server Error:" + e));
                });
    }

    /**
     * This method return one card
     *
     * @param id request param
     * @return card
     */
    @Override
    public Mono<CardType> findById(String id) {
        return cardTypeRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request si invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Card type not found")));
    }

    /**
     * This method creates a card
     *
     * @param request request to create new card
     * @return card created
     */
    @Override
    public Mono<CardType> create(CardTypeRequest request) {
        return cardTypeMapper.toPostModel(request)
                .flatMap(cardTypeRepository::save)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Card type not created")));
    }

    /**
     * This method updates a card
     *
     * @param id      card id to update
     * @param request request to update card
     * @return card updated
     */
    @Override
    public Mono<CardType> update(String id, CardTypeRequest request) {
        return findById(id)
                .flatMap(c -> cardTypeMapper.toPutModel(c, request)
                        .flatMap(cardTypeRepository::save))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Card type not found")));
    }

    /**
     * This method delete an card
     *
     * @param id card id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return cardTypeRepository.deleteById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][delete]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid"));
                });
    }
}
