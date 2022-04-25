package com.nttdata.bankaccount.dto.mapper;

import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.dto.response.ApiResponse;
import com.nttdata.bankaccount.dto.response.CardResponse;
import com.nttdata.bankaccount.enums.RestMethod;
import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.util.AppUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

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
                new Card(request.getName(), AppUtil.dateFormat(new Date()), AppUtil.dateFormat(new Date()))
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
        card.setName(request.getName());
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
        return card.flatMap(c -> Mono.just(
                new CardResponse(c.getId(), c.getName(), c.getCreatedAt(), c.getCreatedAt()))
        );
    }

    /**
     * This method convert a list the cards to response
     *
     * @param cards cards list
     * @return converted response
     */
    public Flux<CardResponse> toFluxResponse(Flux<Card> cards) {
        return cards.flatMap(c -> toMonoResponse(Mono.just(c)));
    }

}
