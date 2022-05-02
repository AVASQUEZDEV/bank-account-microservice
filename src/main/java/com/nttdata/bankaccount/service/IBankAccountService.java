package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.dto.request.BankAccountRequest;
import com.nttdata.bankaccount.enums.TransactionType;
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

    Mono<BankAccount> findById(String id);

    Mono<BankAccount> findByCCI(String cci);

    Mono<BankAccount> create(BankAccountRequest request);

    Mono<BankAccount> update(String id, BankAccountRequest request, TransactionType transactionType);

    Mono<Void> deleteById(String id);

}
