package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.dto.mapper.AccountTypeMapper;
import com.nttdata.bankaccount.dto.request.AccountTypeRequest;
import com.nttdata.bankaccount.dto.response.AccountTypeResponse;
import com.nttdata.bankaccount.service.IAccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This controller class defines the endpoints to bank account charges
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank-accounts/types")
public class AccountTypeRestController {

    private final IAccountTypeService accountTypeService;

    private final AccountTypeMapper accountTypeMapper;

    /**
     * @return list of bank account charges
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AccountTypeResponse> getAll() {
        return accountTypeMapper.toFluxResponse(accountTypeService.findAll());
    }

    /**
     * @return bank account charge
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountTypeResponse> getById(@PathVariable(name = "id") String id) {
        return accountTypeMapper.toMonoResponse(accountTypeService.findById(id));
    }

    /**
     * @param request request to create bank account charge
     * @return bank account charge created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AccountTypeResponse> create(@RequestBody AccountTypeRequest request) {
        return accountTypeMapper.toMonoResponse(accountTypeService.create(request));
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
    public Mono<AccountTypeResponse> update(@PathVariable(name = "id") String id,
                                            @RequestBody AccountTypeRequest request) {
        return accountTypeMapper.toMonoResponse(accountTypeService.update(id, request));
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return accountTypeService.deleteById(id);
    }

}
