package com.bk.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;


import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpringCloudStreamKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaApplication.class, args);
	}
	
}
