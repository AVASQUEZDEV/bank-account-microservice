package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.model.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of bank accounts
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface IBankAccountService {

    Flux<BankAccount> findAll();

    Mono<BankAccount> create(BankAccount bankAccountRequest);

    Mono<BankAccount> update(String id, BankAccount bankAccountRequest);

    Mono<Void> delete(String id);

}
