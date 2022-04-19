package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.model.BankAccountCharge;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface IBankAccountChargeService {

    Flux<BankAccountCharge> findAll();

    Mono<BankAccountCharge> create(BankAccountCharge bankAccChargeRequest);

    Mono<BankAccountCharge> update(String id, BankAccountCharge bankAccChargeRequest);

    Mono<Void> delete(String id);

}
