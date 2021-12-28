package com.bk.spring.cloud.zookeeper.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.spring.cloud.zookeeper.services.HealthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {

	@Autowired
	private HealthService healthService;

	@GetMapping(value = "/instances")
	public List<Map<String, String>> get(){
		return healthService.getInstances();
	}

	@GetMapping(value = "/role")
	public String getRole(){
		return healthService.getRole();
	}

}
