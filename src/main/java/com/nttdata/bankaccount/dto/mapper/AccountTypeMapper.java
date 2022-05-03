package com.nttdata.bankaccount.dto.mapper;

import com.nttdata.bankaccount.dto.request.AccountTypeRequest;
import com.nttdata.bankaccount.dto.response.AccountTypeResponse;
import com.nttdata.bankaccount.model.AccountType;
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
public class AccountTypeMapper {

    /**
     * This method convert request to model
     *
     * @param request request of bankAccountCharge
     * @return bankAccountCharge model
     */
    public Mono<AccountType> toPostModel(AccountTypeRequest request) {
        return Mono.just(
                new AccountType(
                        request.getName(),
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
     * @param accountType entity
     * @param request           accountType request
     * @return accountType model
     */
    public Mono<AccountType> toPutModel(AccountType accountType, AccountTypeRequest request) {
        accountType.setCommission(request.getCommission());
        accountType.setMovementsQuantity(request.getMovementsQuantity());
        accountType.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(accountType);
    }

    /**
     * This method convert bankAccountCharge to response
     *
     * @param bankAccountCharge entity
     * @return converted response
     */
    public Mono<AccountTypeResponse> toMonoResponse(Mono<AccountType> bankAccountCharge) {
        return bankAccountCharge.flatMap(bac -> Mono.just(
                new AccountTypeResponse(
                        bac.getId(),
                        bac.getName(),
                        bac.getCommission(),
                        bac.getMovementsQuantity(),
                        bac.getFrequency(),
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
    public Flux<AccountTypeResponse> toFluxResponse(Flux<AccountType> bankAccountCharges) {
        return bankAccountCharges.flatMap(bac -> toMonoResponse(Mono.just(bac)));
    }

}
