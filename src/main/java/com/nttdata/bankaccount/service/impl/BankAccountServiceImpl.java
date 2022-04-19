package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.model.BankAccount;
import com.nttdata.bankaccount.repository.IBankAccountRepository;
import com.nttdata.bankaccount.service.IBankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final IBankAccountRepository bankAccountRepository;

    /**
     * This method returns a list of bank accounts
     *
     * @return bank account list
     */
    @Override
    public Flux<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    /**
     * This method creates an bank account
     *
     * @param bankAccountRequest request for create new bank account
     * @return bank account created
     */
    @Override
    public Mono<BankAccount> create(BankAccount bankAccountRequest) {
        return bankAccountRepository.save(bankAccountRequest);
    }

    /**
     * This method updates an bank account
     *
     * @param id                 bank account id to update
     * @param bankAccountRequest request for update bank account
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
                });
    }

    /**
     * This method delete an bank account
     *
     * @param id bank account id to delete
     * @return a boolean value
     */
    @Override
    public Mono<Void> delete(String id) {
        return bankAccountRepository.deleteById(id);
    }
}
