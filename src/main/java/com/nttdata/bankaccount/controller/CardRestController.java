package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.service.ICardService;
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
public class CardRestController {

    private final ICardService cardService;

    /**
     * @return list of cards
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Card> getAll() {
        return cardService.findAll();
    }

    /**
     * @param card request to create card
     * @return card created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Card> create(@RequestBody Card card) {
        return cardService.create(card);
    }

    /**
     * @param id          card id to update
     * @param cardRequest request for update card
     * @return card updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Card> update(@PathVariable(name = "id") String id,
                             @RequestBody Card cardRequest) {
        return cardService.update(id, cardRequest);
    }

    /**
     * @param id card id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") String id) {
        return cardService.delete(id);
    }

}
