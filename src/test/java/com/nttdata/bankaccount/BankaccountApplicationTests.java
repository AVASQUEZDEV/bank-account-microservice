package com.nttdata.bankaccount;

import com.nttdata.bankaccount.controller.BankAccountRestController;
import com.nttdata.bankaccount.controller.CardTypeRestController;
import com.nttdata.bankaccount.dto.response.BankAccountResponse;
import com.nttdata.bankaccount.dto.response.CardTypeResponse;
import com.nttdata.bankaccount.service.IBankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

class BankaccountApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	//@MockBean
	//private IBankAccountService bankAccountService;

	@Test
	void createBankAccount() {
		//Mockito.when(bankAccountService.findAll()).thenReturn(Flux.just(request));
		webTestClient.get()
				.uri("/api/v1/bank-accounts")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(BankAccountResponse.class);
	}



}
