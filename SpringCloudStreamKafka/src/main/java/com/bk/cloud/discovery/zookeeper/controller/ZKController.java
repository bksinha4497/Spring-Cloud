package com.bk.cloud.discovery.zookeeper.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.cloud.discovery.zookeeper.services.ServiceInstanceMapper;
import com.bk.cloud.discovery.zookeeper.services.ServiceRegistry;

@RestController
@RequestMapping("zk")
public class ZKController {

	private ServiceRegistry serviceRegistry;

	public ZKController(ServiceRegistry serviceRegistry) {
		super();
		this.serviceRegistry = serviceRegistry;
	}

	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceInstanceMapper getInstance(@PathVariable String name) {
		return serviceRegistry.getInstance(name);
	}

}
