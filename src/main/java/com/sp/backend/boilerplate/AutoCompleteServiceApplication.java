package com.sp.backend.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AutoCompleteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoCompleteServiceApplication.class, args);
    }
}
