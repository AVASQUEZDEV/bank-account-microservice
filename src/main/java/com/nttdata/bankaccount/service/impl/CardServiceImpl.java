package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.dto.mapper.CardMapper;
import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.exceptions.CustomException;
import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.repository.ICardRepository;
import com.nttdata.bankaccount.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class CardServiceImpl implements ICardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    private final ICardRepository ICardRepository;

    private final CardMapper cardMapper;

    /**
     * This method returns a list of bank accounts charges
     *
     * @return ank account charges list
     */
    @Override
    public Flux<Card> findAll() {
        return ICardRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
            return Mono.error(CustomException.internalServerError("Internal Server Error:" + e));
        });
    }

    /**
     * This method return one bank account charge
     *
     * @param id request
     * @return bank account charge
     */
    @Override
    public Mono<Card> findById(String id) {
        return ICardRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * This method creates a bank account charges
     *
     * @param request request to create new bank account charges
     * @return bank account charges created
     */
    @Override
    public Mono<Card> create(CardRequest request) {
        return cardMapper.toPostModel(request)
                .flatMap(ICardRepository::save)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not created")));
    }

    /**
     * This method updates a bank account charges
     *
     * @param id      bank account charge id to update
     * @param request request to update bank account charge
     * @return bank account charge updated
     */
    @Override
    public Mono<Card> update(String id, CardRequest request) {
        return findById(id)
                .flatMap(bac -> cardMapper.toPutModel(bac, request)
                        .flatMap(ICardRepository::save))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * This method delete a bank account charge
     *
     * @param id bank account charge id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return ICardRepository.deleteById(id).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][delete]" + e);
            return Mono.error(CustomException.badRequest("The request is invalid:" + e));
        });
    }

}
