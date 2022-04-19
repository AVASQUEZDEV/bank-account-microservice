package com.nttdata.bankaccount;

import com.nttdata.bankaccount.model.BankAccount;
import com.nttdata.bankaccount.repository.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class BankaccountApplication {

	@Autowired
	private IBankAccountRepository bankAccountRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankaccountApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setCardNumber("12345678");;
		bankAccount.setSecurityCode(123);
		Flux.just(bankAccount)
				.flatMap(p -> bankAccountRepository.save(p))
				.subscribe(System.out::println);
	}*/
}
