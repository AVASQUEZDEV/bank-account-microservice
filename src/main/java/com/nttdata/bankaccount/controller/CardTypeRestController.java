package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.dto.mapper.CardTypeMapper;
import com.nttdata.bankaccount.dto.request.CardTypeRequest;
import com.nttdata.bankaccount.dto.response.CardTypeResponse;
import com.nttdata.bankaccount.service.ICardTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This controller class defines the endpoints to cards
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardTypeRestController {

    private final ICardTypeService cardService;

    private final CardTypeMapper cardTypeMapper;

    /**
     * @return list of cards
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CardTypeResponse> getAll() {
        return cardTypeMapper.toFluxResponse(cardService.findAll());
    }

    /**
     * @return card
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardTypeResponse> getById(@PathVariable(name = "id") String id) {
        return cardTypeMapper.toMonoResponse(cardService.findById(id));
    }

    /**
     * @param cardTypeRequest request to create card
     * @return card created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardTypeResponse> create(@RequestBody CardTypeRequest cardTypeRequest) {
        return cardTypeMapper.toMonoResponse(cardService.create(cardTypeRequest));
    }

    /**
     * @param id      card id to update
     * @param request request for update card
     * @return card updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardTypeResponse> update(@PathVariable(name = "id") String id,
                                         @RequestBody CardTypeRequest request) {
        return cardTypeMapper.toMonoResponse(cardService.update(id, request));
    }

    /**
     * @param id card id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return cardService.deleteById(id);
    }

}
