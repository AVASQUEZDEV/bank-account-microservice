package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.dto.mapper.BankAccountChargeMapper;
import com.nttdata.bankaccount.dto.request.BankAccountChargeRequest;
import com.nttdata.bankaccount.model.BankAccountCharge;
import com.nttdata.bankaccount.repository.IBankAccountChargeRepository;
import com.nttdata.bankaccount.service.IBankAccountChargeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
public class BankAccountChargeServiceImpl implements IBankAccountChargeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountChargeServiceImpl.class);

    private final IBankAccountChargeRepository bankAccountChargeRepository;

    private final BankAccountChargeMapper bankAccountChargeMapper;

    /**
     * This method returns a list of bank accounts charges
     *
     * @return ank account charges list
     */
    @Override
    public Flux<BankAccountCharge> findAll() {
        return bankAccountChargeRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "" + e));
        });
    }

    /**
     * This method return one bank account charge
     *
     * @param id request
     * @return bank account charge
     */
    @Override
    public Mono<BankAccountCharge> findById(String id) {
        return bankAccountChargeRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * This method creates a bank account charges
     *
     * @param request request to create new bank account charges
     * @return bank account charges created
     */
    @Override
    public Mono<BankAccountCharge> create(BankAccountChargeRequest request) {
        return bankAccountChargeMapper.toPostModel(request)
                .flatMap(bankAccountChargeRepository::save)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * This method updates a bank account charges
     *
     * @param id      bank account charge id to update
     * @param request request to update bank account charge
     * @return bank account charge updated
     */
    @Override
    public Mono<BankAccountCharge> update(String id, BankAccountChargeRequest request) {
        return findById(id)
                .flatMap(bac -> bankAccountChargeMapper.toPutModel(bac, request)
                        .flatMap(bankAccountChargeRepository::save))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * This method delete a bank account charge
     *
     * @param id bank account charge id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return bankAccountChargeRepository.deleteById(id).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][delete]" + e);
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
        });
    }

}
