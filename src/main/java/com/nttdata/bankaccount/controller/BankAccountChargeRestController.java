package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.dto.mapper.BankAccountChargeMapper;
import com.nttdata.bankaccount.dto.request.BankAccountChargeRequest;
import com.nttdata.bankaccount.dto.response.BankAccountChargeResponse;
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

    private final BankAccountChargeMapper bankAccountChargeMapper;

    /**
     * @return list of bank account charges
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BankAccountChargeResponse> getAll() {
        return bankAccountChargeMapper.toFluxResponse(bankAccountChargeService.findAll());
    }

    /**
     * @param request request to create bank account charge
     * @return bank account charge created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountChargeResponse> create(@RequestBody BankAccountChargeRequest request) {
        return bankAccountChargeMapper.toMonoResponse(bankAccountChargeService.create(request));
    }

    /**
     * @param id      bank account charge id to update
     * @param request request for update bank account charge
     * @return bank account charge updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BankAccountChargeResponse> update(@PathVariable(name = "id") String id,
                                                  @RequestBody BankAccountChargeRequest request) {
        return bankAccountChargeMapper.toMonoResponse(bankAccountChargeService.update(id, request));
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return bankAccountChargeService.deleteById(id);
    }

}
