package com.nttdata.bankaccount.service;

import com.nttdata.bankaccount.dto.request.BankAccountChargeRequest;
import com.nttdata.bankaccount.model.BankAccountCharge;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface IBankAccountChargeService {

    Flux<BankAccountCharge> findAll();

    Mono<BankAccountCharge> create(BankAccountChargeRequest request);

    Mono<BankAccountCharge> update(String id, BankAccountChargeRequest request);

    Mono<Void> deleteById(String id);

}
