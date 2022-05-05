package com.nttdata.bankaccount.dto.mapper;

import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.dto.response.CardResponse;
import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.util.AppUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * This class convert request and response
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Service
public class CardMapper {

    /**
     * This method convert request to model
     *
     * @param request request of card
     * @return card model
     */
    public Mono<Card> toPostModel(CardRequest request) {
        return Mono.just(
                new Card(
                        request.getCardNumber(),
                        request.getSecurityCode(),
                        request.getExpirationDate(),
                        request.getCci(),
                        request.getBalance(),
                        request.getBankName(),
                        AppUtil.dateFormat(new Date()),
                        AppUtil.dateFormat(new Date())
                )
        );
    }

    /**
     * This method convert request to model
     *
     * @param card    entity
     * @param request card request
     * @return card model
     */
    public Mono<Card> toPutModel(Card card, CardRequest request) {
        card.setBalance(request.getBalance());
        card.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(card);
    }

    /**
     * This method convert card to response
     *
     * @param card entity
     * @return converted response
     */
    public Mono<CardResponse> toMonoResponse(Mono<Card> card) {
        return card.flatMap(bac -> Mono.just(
                new CardResponse(
                        bac.getId(),
                        bac.getCardNumber(),
                        bac.getSecurityCode(),
                        bac.getExpirationDate(),
                        bac.getCci(),
                        bac.getBalance(),
                        bac.getBankName(),
                        bac.getCreatedAt(),
                        bac.getCreatedAt()))
        );
    }

    /**
     * This method convert a list the cards to response
     *
     * @param cards cards list
     * @return converted response
     */
    public Flux<CardResponse> toFluxResponse(Flux<Card> cards) {
        return cards.flatMap(bac -> toMonoResponse(Mono.just(bac)));
    }

}
