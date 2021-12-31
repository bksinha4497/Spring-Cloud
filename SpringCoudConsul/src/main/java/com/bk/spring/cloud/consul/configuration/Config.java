package com.bk.spring.cloud.consul.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "cloud")
@Data
public class Config {
	private int timeout;
	private boolean success;
}
