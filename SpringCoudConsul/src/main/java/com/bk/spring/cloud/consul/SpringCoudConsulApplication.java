package com.bk.spring.cloud.consul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class SpringCoudConsulApplication implements Runnable {

	public static void main(String[] args) {
		SpringApplication.run(SpringCoudConsulApplication.class, args);
	}

	@Value("${test.msg}")
	String value;

	@Override
	public void run() {
		log.info(" Value - {}", value);

	}

}
