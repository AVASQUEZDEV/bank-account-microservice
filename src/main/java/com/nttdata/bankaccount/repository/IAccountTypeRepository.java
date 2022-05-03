package com.nttdata.bankaccount.repository;

import com.nttdata.bankaccount.model.AccountType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * This interface defines the repository for bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface IAccountTypeRepository extends ReactiveMongoRepository<AccountType, String> {

    Mono<AccountType> findByName(String name);

}
