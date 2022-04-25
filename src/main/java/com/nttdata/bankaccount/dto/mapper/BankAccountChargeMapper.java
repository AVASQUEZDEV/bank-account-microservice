package com.nttdata.bankaccount.dto.mapper;

import com.nttdata.bankaccount.dto.request.BankAccountChargeRequest;
import com.nttdata.bankaccount.dto.response.BankAccountChargeResponse;
import com.nttdata.bankaccount.model.BankAccountCharge;
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
public class BankAccountChargeMapper {

    /**
     * This method convert request to model
     *
     * @param request request of bankAccountCharge
     * @return bankAccountCharge model
     */
    public Mono<BankAccountCharge> toPostModel(BankAccountChargeRequest request) {
        return Mono.just(
                new BankAccountCharge(
                        request.getCommission(),
                        request.getMovementsQuantity(),
                        AppUtil.dateFormat(new Date()),
                        AppUtil.dateFormat(new Date())
                )
        );
    }

    /**
     * This method convert request to model
     *
     * @param bankAccountCharge entity
     * @param request           bankAccountCharge request
     * @return bankAccountCharge model
     */
    public Mono<BankAccountCharge> toPutModel(BankAccountCharge bankAccountCharge, BankAccountChargeRequest request) {
        bankAccountCharge.setCommission(request.getCommission());
        bankAccountCharge.setMovementsQuantity(request.getMovementsQuantity());
        bankAccountCharge.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(bankAccountCharge);
    }

    /**
     * This method convert bankAccountCharge to response
     *
     * @param bankAccountCharge entity
     * @return converted response
     */
    public Mono<BankAccountChargeResponse> toMonoResponse(Mono<BankAccountCharge> bankAccountCharge) {
        return bankAccountCharge.flatMap(bac -> Mono.just(
                new BankAccountChargeResponse(
                        bac.getId(),
                        bac.getCommission(),
                        bac.getMovementsQuantity(),
                        bac.getCreatedAt(),
                        bac.getCreatedAt()))
        );
    }

    /**
     * This method convert a list the bankAccountCharges to response
     *
     * @param bankAccountCharges bankAccountCharges list
     * @return converted response
     */
    public Flux<BankAccountChargeResponse> toFluxResponse(Flux<BankAccountCharge> bankAccountCharges) {
        return bankAccountCharges.flatMap(bac -> toMonoResponse(Mono.just(bac)));
    }

}
