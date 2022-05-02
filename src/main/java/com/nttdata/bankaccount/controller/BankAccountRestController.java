package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.dto.mapper.BankAccountMapper;
import com.nttdata.bankaccount.dto.request.BankAccountRequest;
import com.nttdata.bankaccount.dto.response.BankAccountResponse;
import com.nttdata.bankaccount.enums.TransactionType;
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

    private final BankAccountMapper bankAccountMapper;

    /**
     * @return list of bank accounts
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BankAccountResponse> getAll() {
        return bankAccountMapper.toFluxResponse(bankAccountService.findAll());
    }

    /**
     * @return a bank account
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountResponse> getById(@PathVariable(name = "id") String id) {
        return bankAccountMapper.toMonoResponse(bankAccountService.findById(id));
    }

    /***
     * @param id request
     * @return bank account
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/cci/{cci}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountResponse> getByCCI(@PathVariable(name = "cci") String cci) {
        return bankAccountMapper.toMonoResponse(bankAccountService.findByCCI(cci));
    }

    /**
     * @param request request to create bank account
     * @return bank account created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountResponse> create(@RequestBody BankAccountRequest request) {
        return bankAccountMapper.toMonoResponse(bankAccountService.create(request));
    }

    /**
     * @param id      bank account id to update
     * @param request request for update bank account
     * @return bank account updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountResponse> update(
            @PathVariable(name = "id") String id,
            @RequestBody BankAccountRequest request,
            @RequestParam(name = "transactionType")TransactionType transactionType) {
        return bankAccountMapper.toMonoResponse(bankAccountService.update(id, request, transactionType));
    }

    /**
     * @param id bank account id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return bankAccountService.deleteById(id);
    }

}
