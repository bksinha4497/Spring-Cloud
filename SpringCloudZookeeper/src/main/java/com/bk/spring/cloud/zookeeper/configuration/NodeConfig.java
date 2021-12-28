package com.bk.spring.cloud.zookeeper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bk.spring.cloud.zookeeper.model.Node;

@Configuration
public class NodeConfig {

	@Bean
	public Node node() {
		return new Node();
	}
}
