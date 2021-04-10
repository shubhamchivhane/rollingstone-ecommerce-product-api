package com.ecom.rollingstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class RollingstoneEcommerceProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RollingstoneEcommerceProductApiApplication.class, args);
	}

}
