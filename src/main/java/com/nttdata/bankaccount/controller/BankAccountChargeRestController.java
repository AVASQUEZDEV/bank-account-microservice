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
    @GetMapping
    public Mono<ResponseEntity<Flux<BankAccountCharge>>> getAll() {
        return Mono.just(
                        ResponseEntity
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(bankAccountChargeService.findAll()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param bankAccChargeRequest request to create bank account charge
     * @return bank account charge created
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BankAccountCharge>> create(@RequestBody BankAccountCharge bankAccChargeRequest) {
        return bankAccountChargeService.create(bankAccChargeRequest)
                .map(bac -> ResponseEntity
                        .created(URI.create(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bac));
    }

    /**
     * @param id                   bank account charge id to update
     * @param bankAccChargeRequest request for update bank account charge
     * @return bank account charge updated
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BankAccountCharge>> update(@PathVariable(name = "id") String id,
                                                          @RequestBody BankAccountCharge bankAccChargeRequest) {
        return bankAccountChargeService.update(id, bankAccChargeRequest)
                .map(bac -> ResponseEntity
                        .created(URI.create(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bac))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "id") String id) {
        return bankAccountChargeService.delete(id)
                .then(Mono.just(
                        new ResponseEntity<>(HttpStatus.NO_CONTENT)
                ));
    }

}
