package com.bk.cloud.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bk.cloud.kafka.model.KafkaDetails;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class KafkaController {

	@Autowired
	private StreamBridge publisher;

	@PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getData(@RequestBody KafkaDetails object) {
		log.info("Recieved from controller - {} ", object);
		publisher.send("producer-out-0", object);
		return "published";
	}


}
