package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.dto.request.AccountTypeRequest;
import com.nttdata.bankaccount.model.AccountType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface IAccountTypeService {

    Flux<AccountType> findAll();

    Mono<AccountType> findById(String id);

    Mono<AccountType> create(AccountTypeRequest request);

    Mono<AccountType> update(String id, AccountTypeRequest request);

    Mono<Void> deleteById(String id);

}
