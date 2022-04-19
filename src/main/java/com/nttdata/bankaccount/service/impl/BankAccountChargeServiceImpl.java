package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.model.BankAccountCharge;
import com.nttdata.bankaccount.repository.IBankAccountChargeRepository;
import com.nttdata.bankaccount.service.IBankAccountChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class BankAccountChargeServiceImpl implements IBankAccountChargeService {

    private final IBankAccountChargeRepository bankAccountChargeRepository;

    /**
     * This method returns a list of bank accounts charges
     *
     * @return ank account charges list
     */
    @Override
    public Flux<BankAccountCharge> findAll() {
        return bankAccountChargeRepository.findAll();
    }

    /**
     * This method creates an bank account charges
     *
     * @param bankAccChargeRequest request for create new bank account charges
     * @return bank account charges created
     */
    @Override
    public Mono<BankAccountCharge> create(BankAccountCharge bankAccChargeRequest) {
        return bankAccountChargeRepository.save(bankAccChargeRequest);
    }

    /**
     * This method updates an bank account charges
     *
     * @param id                   bank account charge id to update
     * @param bankAccChargeRequest request for update bank account charge
     * @return bank account charge updated
     */
    @Override
    public Mono<BankAccountCharge> update(String id, BankAccountCharge bankAccChargeRequest) {
        return findAll().filter(bac -> bac.getId().equals(id))
                .single()
                .flatMap(bac -> {
                    bac.setCommission(bankAccChargeRequest.getCommission());
                    bac.setMovementsQuantity(bankAccChargeRequest.getMovementsQuantity());
                    bac.setUpdatedAt(bankAccChargeRequest.getUpdatedAt());
                    return bankAccountChargeRepository.save(bac);
                });
    }

    /**
     * This method delete an bank account charge
     *
     * @param id bank account charge id to delete
     * @return a boolean value
     */
    @Override
    public Mono<Void> delete(String id) {
        return bankAccountChargeRepository.deleteById(id);
    }

}
