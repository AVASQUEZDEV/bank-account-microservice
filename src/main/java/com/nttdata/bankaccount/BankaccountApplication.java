package com.nttdata.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankaccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankaccountApplication.class, args);
    }

}
