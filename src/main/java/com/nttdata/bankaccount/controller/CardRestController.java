package com.nttdata.bankaccount.controller;

import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1")
public class CardRestController {

    private final ICardService cardService;

    /**
     * @return list of cards
     */
    @GetMapping(value = "/cards")
    public Mono<ResponseEntity<Flux<Card>>> findAll() {
        return Mono.just(
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(cardService.findAll()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param card request to create card
     * @return card created
     */
    @PostMapping(value = "/card", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Card>> create(@RequestBody Card card) {
        return cardService.create(card)
                .map(bac -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bac));
    }

    /**
     * @param id          card id to update
     * @param cardRequest request for update card
     * @return card updated
     */
    @PutMapping(value = "/card/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Card>> update(@PathVariable(name = "id") String id,
                                             @RequestBody Card cardRequest) {
        return cardService.update(id, cardRequest)
                .map(bac -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bac))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param id card id to delete
     * @return void
     */
    @DeleteMapping("/card/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "id") String id) {
        return cardService.delete(id)
                .then(Mono.just(
                        new ResponseEntity<>(HttpStatus.NO_CONTENT)
                ));
    }

}
