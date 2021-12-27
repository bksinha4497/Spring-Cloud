package com.bk.cloud.kafka.model;

import lombok.Data;

@Data
public class KafkaDetails {

	private String topicName;
	private String parition;
	private String group;
}
