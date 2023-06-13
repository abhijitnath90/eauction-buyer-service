package com.eauction.eauctionbuyerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EauctionBuyerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EauctionBuyerServiceApplication.class, args);
	}

}
