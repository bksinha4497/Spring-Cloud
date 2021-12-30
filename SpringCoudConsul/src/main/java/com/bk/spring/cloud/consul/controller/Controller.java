package com.bk.spring.cloud.consul.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.spring.cloud.consul.configuration.Config;

import lombok.AllArgsConstructor;

@RestController
@EnableConfigurationProperties(Config.class)
@AllArgsConstructor
public class Controller {

	private final Config config;

	@GetMapping(value = "/getConfig")
	public Config getConfig() {
		return config;
	}
}
