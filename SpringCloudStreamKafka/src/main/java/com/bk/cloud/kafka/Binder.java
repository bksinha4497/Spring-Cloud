package com.bk.cloud.kafka;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Binder {
	
	@Bean
	public Supplier<String> producer(){
		return ()-> "hello world";
	}

	@Bean
	public Consumer<String> consumer() {  
	    return message -> System.out.println("received " + message);  
	}
}