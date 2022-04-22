package com.nttdata.bankaccount.dto.convertion;

import com.nttdata.bankaccount.dto.request.CardRequest;
import com.nttdata.bankaccount.dto.response.ApiResponse;
import com.nttdata.bankaccount.dto.response.CardResponse;
import com.nttdata.bankaccount.model.Card;
import com.nttdata.bankaccount.util.AppUtil;
import com.nttdata.bankaccount.enums.RestMethod;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author Alcibar Vasquez
 * @version 1.0
 */
public class CardConvertion {

    /**
     * This method convert request to model
     *
     * @param cardRequest request of card
     * @param method      type method
     * @return card model
     */
    public static Card toModel(CardRequest cardRequest, RestMethod method) {
        if (method.equals(RestMethod.POST)) {
            return new Card(
                    cardRequest.getName(),
                    AppUtil.dateFormat(new Date()),
                    AppUtil.dateFormat(new Date())
            );
        } else {
            return new Card(
                    cardRequest.getName(),
                    AppUtil.dateFormat(new Date())
            );
        }
    }

    public static Mono<ApiResponse<CardResponse>> toResponse(Mono<Card> card) {
        return card.flatMap(c -> Mono.just(
                new ApiResponse<>(
                        "",
                        "",
                        new CardResponse(
                                c.getId(),
                                c.getName(),
                                c.getCreatedAt(),
                                c.getCreatedAt()
                        )
                ))
        );
    }

}
