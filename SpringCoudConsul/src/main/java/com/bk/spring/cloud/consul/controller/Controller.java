package com.bk.spring.cloud.consul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.spring.cloud.consul.configuration.Config;

@RestController
@EnableConfigurationProperties(Config.class)
public class Controller {

	@Autowired
	private Config config;

	@GetMapping(value = "/getConfig")
	public Config getConfig() {
		return config;
	}
}
