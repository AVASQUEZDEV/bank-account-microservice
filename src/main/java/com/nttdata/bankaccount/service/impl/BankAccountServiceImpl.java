package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.model.BankAccount;
import com.nttdata.bankaccount.repository.IBankAccountRepository;
import com.nttdata.bankaccount.service.IBankAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of bank accounts
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class BankAccountServiceImpl implements IBankAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final IBankAccountRepository bankAccountRepository;

    /**
     * This method returns a list of bank accounts
     *
     * @return bank account list
     */
    @Override
    public Flux<BankAccount> findAll() {
        return bankAccountRepository.findAll()
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "" + e));
                });
    }

    /**
     * This method creates a bank account
     *
     * @param bankAccountRequest request to create new bank account
     * @return bank account created
     */
    @Override
    public Mono<BankAccount> create(BankAccount bankAccountRequest) {
        return bankAccountRepository.save(bankAccountRequest)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + e));
                }).switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
                );
    }

    /**
     * This method updates a bank account
     *
     * @param id                 bank account id to update
     * @param bankAccountRequest request to update bank account
     * @return bank account updated
     */
    @Override
    public Mono<BankAccount> update(String id, BankAccount bankAccountRequest) {
        return findAll()
                .filter(ba -> ba.getId().equals(id))
                .single()
                .flatMap(ba -> {
                    ba.setCardNumber(bankAccountRequest.getCardNumber());
                    ba.setSecurityCode(bankAccountRequest.getSecurityCode());
                    ba.setExpirationDate(bankAccountRequest.getExpirationDate());
                    ba.setUpdatedAt(bankAccountRequest.getUpdatedAt());
                    return bankAccountRepository.save(ba);
                }).onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
                }).switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
                );
    }

    /**
     * This method delete a bank account
     *
     * @param id bank account id to delete
     * @return void
     */
    @Override
    public Mono<Void> delete(String id) {
        return bankAccountRepository.deleteById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][delete]" + e);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
                });
    }
}
