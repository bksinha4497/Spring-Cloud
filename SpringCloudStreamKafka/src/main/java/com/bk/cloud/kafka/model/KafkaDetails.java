package com.bk.cloud.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaDetails {

	private String topicName;
	private String parition;
	private String group;
}
