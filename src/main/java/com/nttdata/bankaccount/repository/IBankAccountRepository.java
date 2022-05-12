package com.nttdata.bankaccount.repository;

import com.nttdata.bankaccount.model.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the repository for bank accounts
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface IBankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {

    Flux<BankAccount> findByClientId(String clientId);


}
