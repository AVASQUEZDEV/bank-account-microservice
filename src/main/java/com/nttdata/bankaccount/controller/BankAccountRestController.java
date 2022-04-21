package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.model.BankAccount;
import com.nttdata.bankaccount.service.IBankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * This controller class defines the endpoints to bank accounts
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountRestController {

    private final IBankAccountService bankAccountService;

    /**
     * @return list of bank accounts
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BankAccount> getAll() {
        return bankAccountService.findAll();
    }

    /**
     * @param bankAccountRequest request to create bank account
     * @return bank account created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccount> create(@RequestBody BankAccount bankAccountRequest) {
        return bankAccountService.create(bankAccountRequest);
    }

    /**
     * @param id                 bank account id to update
     * @param bankAccountRequest request for update bank account
     * @return bank account updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccount> update(
            @PathVariable(name = "id") String id,
            @RequestBody BankAccount bankAccountRequest) {
        return bankAccountService.update(id, bankAccountRequest);
    }

    /**
     * @param id bank account id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") String id) {
        return bankAccountService.delete(id);
    }

}
