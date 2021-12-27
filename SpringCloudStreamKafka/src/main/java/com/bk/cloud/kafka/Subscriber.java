package com.bk.cloud.kafka;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.bk.cloud.kafka.interfaces.ISubscriber;
import com.bk.cloud.kafka.model.KafkaDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Subscriber implements ISubscriber {

	@Override
	@Bean
	public Consumer<KafkaDetails> consumer() {
		return message -> log.info("Consumed Message -  " + message);
	}
}