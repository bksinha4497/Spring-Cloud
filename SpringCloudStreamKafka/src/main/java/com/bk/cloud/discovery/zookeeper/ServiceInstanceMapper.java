package com.bk.cloud.discovery.zookeeper;

import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;

import lombok.Data;

@Data
public class ServiceInstanceMapper {

	private String serviceInstanceName;
	private String serviceInstanceId;
	private ZookeeperInstance payload;

	public ServiceInstanceMapper(ServiceInstance<ZookeeperInstance> zookeeperInstance) {
		super();
		this.serviceInstanceName = zookeeperInstance.getName();
		this.serviceInstanceId = zookeeperInstance.getId();
		this.payload = zookeeperInstance.getPayload();
	}
}
