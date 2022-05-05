package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.dto.mapper.CardMapper;
import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.dto.response.CardResponse;
import com.nttdata.bankaccount.service.ICardService;
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
public class CardRestController {

    private final ICardService cardService;

    private final CardMapper cardMapper;

    /**
     * @return list of bank account charges
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CardResponse> getAll() {
        return cardMapper.toFluxResponse(cardService.findAll());
    }

    /**
     * @return bank account charge
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardResponse> getById(@PathVariable(name = "id") String id) {
        return cardMapper.toMonoResponse(cardService.findById(id));
    }

    /**
     * @param request request to create bank account charge
     * @return bank account charge created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardResponse> create(@RequestBody CardRequest request) {
        return cardMapper.toMonoResponse(cardService.create(request));
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
    public Mono<CardResponse> update(@PathVariable(name = "id") String id,
                                     @RequestBody CardRequest request) {
        return cardMapper.toMonoResponse(cardService.update(id, request));
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return cardService.deleteById(id);
    }

}
