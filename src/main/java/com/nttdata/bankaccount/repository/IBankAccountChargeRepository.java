package com.nttdata.bankaccount.repository;

import com.nttdata.bankaccount.model.BankAccountCharge;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface defines the repository for bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface IBankAccountChargeRepository extends ReactiveMongoRepository<BankAccountCharge, String> {
}
