package com.bk.spring.cloud.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCoudConsulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCoudConsulApplication.class, args);
	}


}
