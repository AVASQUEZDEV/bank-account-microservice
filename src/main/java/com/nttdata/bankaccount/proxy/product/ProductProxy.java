package com.nttdata.bankaccount.proxy.product;

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
public class ProductProxy {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientProxy.class);

    @Value("${api-gateway.routes.ms-product.product}")
    private String productURL;

    //private WebClient.Builder webClient = WebClient.builder();

    private final WebClient webClient;

    public Mono<ProductResponse> getProductById(String id) {
        LOGGER.info("[REQUEST][URL][getProductById]:" + productURL + "/" + id);
        return webClient.get()
                .uri(productURL + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][getProductById]" + e);
                    return Mono.error(CustomException.badRequest("The request to proxy of product is invalid"));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Product not found")));
    }

}
