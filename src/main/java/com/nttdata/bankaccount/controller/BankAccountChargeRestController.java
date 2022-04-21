package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.model.BankAccountCharge;
import com.nttdata.bankaccount.service.IBankAccountChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * This controller class defines the endpoints to bank account charges
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank-accounts/charges")
public class BankAccountChargeRestController {

    private final IBankAccountChargeService bankAccountChargeService;

    /**
     * @return list of bank account charges
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BankAccountCharge> getAll() {
        return bankAccountChargeService.findAll();
    }

    /**
     * @param bankAccChargeRequest request to create bank account charge
     * @return bank account charge created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountCharge> create(@RequestBody BankAccountCharge bankAccChargeRequest) {
        return bankAccountChargeService.create(bankAccChargeRequest);
    }

    /**
     * @param id                   bank account charge id to update
     * @param bankAccChargeRequest request for update bank account charge
     * @return bank account charge updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountCharge> update(@PathVariable(name = "id") String id,
                                          @RequestBody BankAccountCharge bankAccChargeRequest) {
        return bankAccountChargeService.update(id, bankAccChargeRequest);
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") String id) {
        return bankAccountChargeService.delete(id);
    }

}
