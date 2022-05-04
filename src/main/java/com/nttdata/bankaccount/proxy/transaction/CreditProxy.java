package com.nttdata.bankaccount.proxy.transaction;

import com.nttdata.bankaccount.dto.response.proxy.CreditResponse;
import com.nttdata.bankaccount.dto.response.proxy.ProductResponse;
import com.nttdata.bankaccount.exceptions.CustomException;
import com.nttdata.bankaccount.proxy.client.ClientProxy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * This class get queries external
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class CreditProxy {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientProxy.class);

    @Value("${microservices.transaction.base-url}")
    private String baseUrl;

    @Value("${microservices.transaction.end-point.credit}")
    private String path;

    public String getCompleteURL() {
        return baseUrl + path;
    }
    //private WebClient.Builder webClient = WebClient.builder();

    private final WebClient webClient;

    public Mono<CreditResponse> getCreditByClientId(String id) {
        LOGGER.info("[REQUEST][URL][getCreditByClientId]:" + getCompleteURL() + "/" + id);
        return webClient.get()
                .uri(getCompleteURL() + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CreditResponse.class)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][getCreditByClientId]" + e);
                    return Mono.error(CustomException.badRequest("The request to proxy is invalid"));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Credit not found")));
    }

}
