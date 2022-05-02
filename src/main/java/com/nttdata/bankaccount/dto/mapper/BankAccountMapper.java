package com.nttdata.bankaccount.dto.mapper;

import com.nttdata.bankaccount.dto.request.BankAccountRequest;
import com.nttdata.bankaccount.dto.response.BankAccountResponse;
import com.nttdata.bankaccount.model.BankAccount;
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
public class BankAccountMapper {

    /**
     * This method convert request to model
     *
     * @param request request of bankAccount
     * @return bankAccount model
     */
    public Mono<BankAccount> toPostModel(BankAccountRequest request) {
        return Mono.just(
                new BankAccount(
                        request.getCardNumber(),
                        request.getSecurityCode(),
                        request.getExpirationDate(),
                        request.getCci(),
                        request.getBalance(),
                        AppUtil.dateFormat(new Date()),
                        AppUtil.dateFormat(new Date())
                )
        );
    }

    /**
     * This method convert request to model
     *
     * @param bankAccount entity
     * @param request     bankAccount request
     * @return bankAccount model
     */
    public Mono<BankAccount> toPutModel(BankAccount bankAccount, BankAccountRequest request) {
        bankAccount.setBalance(request.getBalance());
        bankAccount.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(bankAccount);
    }

    /**
     * This method convert bankAccount to response
     *
     * @param bankAccount entity
     * @return converted response
     */
    public Mono<BankAccountResponse> toMonoResponse(Mono<BankAccount> bankAccount) {
        return bankAccount.flatMap(ba -> Mono.just(
                new BankAccountResponse(
                        ba.getId(),
                        ba.getCardNumber(),
                        ba.getSecurityCode(),
                        ba.getExpirationDate(),
                        ba.getCci(),
                        ba.getBalance(),
                        ba.getCreatedAt(),
                        ba.getCreatedAt()))
        );
    }

    /**
     * This method convert a list the bankAccounts to response
     *
     * @param bankAccounts bankAccounts list
     * @return converted response
     */
    public Flux<BankAccountResponse> toFluxResponse(Flux<BankAccount> bankAccounts) {
        return bankAccounts.flatMap(ba -> toMonoResponse(Mono.just(ba)));
    }

}
