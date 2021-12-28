package com.bk.spring.cloud.zookeeper.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.bk.spring.cloud.zookeeper.model.Node;

@Service
public class HealthService {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private Node node;

	@Value("${spring.application.name}")
	private String applicationName;

	public List<Map<String, String>> getInstances() {
		List<Map<String, String>> instances = new ArrayList<>();
		List<ServiceInstance> list = discoveryClient.getInstances(applicationName);
		if (list != null && list.size() > 0 ) {
			for(ServiceInstance node : list) {
				Map<String, String> metadata = new HashMap<>(node.getMetadata());
				metadata.put("instance_id", node.getInstanceId());
				metadata.put("hot", node.getHost());
				metadata.put("port", String.valueOf(node.getPort()));
				instances.add(metadata);
			}
		}

		return instances;
	}

	public String getRole() {
		if (node.isLeader()) {
			return "LEADER";
		}
		return "NOT A LEADER";
	}

}
