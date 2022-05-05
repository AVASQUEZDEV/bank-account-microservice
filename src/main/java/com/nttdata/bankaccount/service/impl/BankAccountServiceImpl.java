package com.nttdata.bankaccount.service.impl;

import com.nttdata.bankaccount.dto.mapper.BankAccountMapper;
import com.nttdata.bankaccount.dto.request.BankAccountRequest;
import com.nttdata.bankaccount.dto.response.proxy.ClientResponse;
import com.nttdata.bankaccount.dto.response.proxy.CreditResponse;
import com.nttdata.bankaccount.dto.response.proxy.ProductResponse;
import com.nttdata.bankaccount.enums.PlanType;
import com.nttdata.bankaccount.enums.TransactionType;
import com.nttdata.bankaccount.exceptions.CustomException;
import com.nttdata.bankaccount.model.BankAccount;
import com.nttdata.bankaccount.proxy.client.ClientProxy;
import com.nttdata.bankaccount.proxy.product.ProductProxy;
import com.nttdata.bankaccount.proxy.transaction.CreditProxy;
import com.nttdata.bankaccount.repository.IBankAccountRepository;
import com.nttdata.bankaccount.service.IBankAccountService;
import com.nttdata.bankaccount.util.Constant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of bank accounts
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class BankAccountServiceImpl implements IBankAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final IBankAccountRepository bankAccountRepository;

    private final BankAccountMapper bankAccountMapper;

    private final ProductProxy productProxy;

    private final ClientProxy clientProxy;

    private final CreditProxy creditProxy;


    /**
     * This method returns a list of bank accounts
     *
     * @return bank account list
     */
    @Override
    public Flux<BankAccount> findAll() {
        return bankAccountRepository.findAll()
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
                    return Mono.error(CustomException.internalServerError("Internal Server Error:" + e));
                });
    }

    /**
     * This method return one bank account
     *
     * @param id request
     * @return bank account
     */
    @Override
    public Mono<BankAccount> findById(String id) {
        return bankAccountRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account not found")));
    }

    /**
     * @param cci request
     * @return bank account
     */
    @Override
    public Mono<BankAccount> findByCCI(String cci) {
        return bankAccountRepository.findByCci(cci)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account not found")));
    }

    /**
     * This method creates a bank account
     *
     * @param request request to create new bank account
     * @return bank account created
     */
    @Override
    public Mono<BankAccount> create(BankAccountRequest request) {
        LOGGER.info("[BankAccountServiceImpl][create][request]" + request.toString());
        Mono<ClientResponse> client = clientProxy.getClientById(request.getClientId());
        Mono<ProductResponse> product = productProxy.getProductById(request.getProductId());
        return checkIfExistDebts(request.getClientId())
                .doOnNext(debts -> LOGGER.info("[BankAccountServiceImpl][Proxy][getCreditByClientId]" + debts))
                .flatMap(debts -> Mono.zip(client, product)
                        .doOnNext(res -> LOGGER.info("[BankAccountServiceImpl][Proxy]" + res.toString()))
                        .flatMap(res -> bankAccountRepository.findByClientId(res.getT1().getId())
                                //.filter(cli -> cli.getProductId().equals(res.getT2().getId()))
                                //.singleOrEmpty()
                                .doOnNext(ba -> LOGGER.info("[BankAccountServiceImpl][bankAccountRepository]" + ba.toString()))
                                .flatMap(ba -> {
                                    LOGGER.info("[BankAccountServiceImpl][create][findByCCi]" + res);
                                    if(debts) {
                                        return Mono.error(CustomException
                                                .badRequest("You have pending payments for credit products"));
                                    }
                                    String planName = res.getT1().getPlan().getName();
                                    String productName = res.getT2().getName();
                                    // validate plan type if is personal or empresarial
                                    if (planName.equals(PlanType.Personal.toString())) {
                                        LOGGER.info("[BankAccountServiceImpl][create]" + planName);
                                        if (ba.getProductId().equals(res.getT2().getId())) {
                                            return Mono.error(CustomException
                                                    .badRequest("The personal plan already has a account: " + productName));
                                        } else {
                                            return bankAccountMapper.toPostModel(request)
                                                    .flatMap(bankAccountRepository::save);
                                        }
                                    } else {
                                        LOGGER.info("[BankAccountServiceImpl][create]" + planName);
                                        if (productName.equals(Constant.CURRENT_ACCOUNT) ||
                                                productName.equals(Constant.CREDIT_ACCOUNT)) {
                                            return bankAccountMapper.toPostModel(request)
                                                    .flatMap(bankAccountRepository::save);
                                        } else {
                                            return Mono.error(CustomException
                                                    .badRequest("The business plan can only have current accounts"));
                                        }
                                    }
                                }).singleOrEmpty()
                                .switchIfEmpty(bankAccountMapper.toPostModel(request)
                                        .flatMap(bankAccountRepository::save))
                        ).onErrorResume(e -> {
                            LOGGER.error("[" + getClass().getName() + "][create]" + e);
                            return Mono.error(CustomException.badRequest("The request is invalid:" + e.getMessage()));
                        }).switchIfEmpty(Mono.error(CustomException.notFound("Client or Product not found"))));
    }

    /**
     * This method updates a bank account
     *
     * @param id      bank account id to update
     * @param request request to update bank account
     * @return bank account updated
     */
    @Override
    public Mono<BankAccount> update(String id, BankAccountRequest request, TransactionType transactionType) {
        return findById(id)
                .flatMap(ba -> {
                    // Validate transaction type to update balance
                    Float currentBalance = ba.getBalance();
                    Float newBalance = request.getBalance();
                    if (transactionType.equals(TransactionType.DEPOSIT)) {
                        request.setBalance(currentBalance + newBalance);
                    } else if (transactionType.equals(TransactionType.WITHDRAWAL)) {
                        request.setBalance(currentBalance - newBalance);
                    }
                    return bankAccountMapper.toPutModel(ba, request)
                            .flatMap(bankAccountRepository::save);
                }).onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account not found")));
    }

    /**
     * This method delete a bank account
     *
     * @param id bank account id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return bankAccountRepository.deleteById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][deleteById]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid" + e));
                });
    }

    /**
     * This method check if exist debs of credit card
     *
     * @param clientId request
     * @return boolean or empty
     */
    private Mono<Boolean> checkIfExistDebts(String clientId) {
        return creditProxy.getCreditByClientId(clientId)
                .filter(c -> c.getCreditStatus().equals(Constant.CREDIT_STATUS_EXPIRED))
                .singleOrEmpty()
                .flatMap(s -> Mono.just(true))
                .switchIfEmpty(Mono.just(false));
    }
}
