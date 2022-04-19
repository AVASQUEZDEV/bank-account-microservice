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

/**
 * This controller class defines the endpoints to bank accounts
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BankAccountRestController {

    private final IBankAccountService bankAccountService;

    /**
     * @return list of bank accounts
     */
    @GetMapping("/bank-accounts")
    public Mono<ResponseEntity<Flux<BankAccount>>> getAll() {
        return Mono.just(
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(bankAccountService.findAll()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param bankAccountRequest request to create bank account
     * @return bank account created
     */
    @PostMapping("/bank-account")
    public Mono<ResponseEntity<BankAccount>> create(@RequestBody BankAccount bankAccountRequest) {
        return bankAccountService.create(bankAccountRequest)
                .map(bac -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bac));
    }

    /**
     * @param id                 bank account id to update
     * @param bankAccountRequest request for update bank account
     * @return bank account updated
     */
    @PutMapping("/bank-account/{id}")
    public Mono<ResponseEntity<BankAccount>> update(@PathVariable(name = "id") String id,
                                                    @RequestBody BankAccount bankAccountRequest) {
        return bankAccountService.update(id, bankAccountRequest)
                .map(bac -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bac))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param id bank account id to delete
     * @return void
     */
    @DeleteMapping("/bank-account/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "id") String id) {
        return bankAccountService.delete(id)
                .then(Mono.just(
                        new ResponseEntity<>(HttpStatus.OK)
                ));
    }

}
