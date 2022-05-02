package com.nttdata.bankaccount.dto.mapper;

import com.nttdata.bankaccount.dto.request.CardTypeRequest;
import com.nttdata.bankaccount.dto.response.CardTypeResponse;
import com.nttdata.bankaccount.model.CardType;
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
public class CardTypeMapper {

    /**
     * This method convert request to model
     *
     * @param request request of card
     * @return card model
     */
    public Mono<CardType> toPostModel(CardTypeRequest request) {
        return Mono.just(
                new CardType(request.getName(), AppUtil.dateFormat(new Date()), AppUtil.dateFormat(new Date()))
        );
    }

    /**
     * This method convert request to model
     *
     * @param cardType    entity
     * @param request card request
     * @return card model
     */
    public Mono<CardType> toPutModel(CardType cardType, CardTypeRequest request) {
        cardType.setName(request.getName());
        cardType.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(cardType);
    }

    /**
     * This method convert card to response
     *
     * @param card entity
     * @return converted response
     */
    public Mono<CardTypeResponse> toMonoResponse(Mono<CardType> card) {
        return card.flatMap(c -> Mono.just(
                new CardTypeResponse(c.getId(), c.getName(), c.getCreatedAt(), c.getCreatedAt()))
        );
    }

    /**
     * This method convert a list the cards to response
     *
     * @param cards cards list
     * @return converted response
     */
    public Flux<CardTypeResponse> toFluxResponse(Flux<CardType> cards) {
        return cards.flatMap(c -> toMonoResponse(Mono.just(c)));
    }

}
