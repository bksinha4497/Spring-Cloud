package com.bk.cloud.discovery.zookeeper.services;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.stereotype.Service;

import com.bk.cloud.discovery.zookeeper.interfaces.IServiceRegistry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceRegistry implements IServiceRegistry {

	private CuratorFramework curatorFramework;
	private ZookeeperDiscoveryProperties zookeeperDiscoveryProperties;
	private ServiceDiscovery<ZookeeperInstance> serviceDiscovery;
	private String name;

	@Autowired
	public ServiceRegistry(CuratorFramework curatorFramework,
			ZookeeperDiscoveryProperties zookeeperDiscoveryProperties) {
		super();
		this.curatorFramework = curatorFramework;
		this.zookeeperDiscoveryProperties = zookeeperDiscoveryProperties;
		this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ZookeeperInstance.class).client(curatorFramework)
				.basePath(zookeeperDiscoveryProperties.getRoot()).build();
	}

	@Override
	public void close() throws Exception {
		if (curatorFramework != null) {
			curatorFramework.close();
			curatorFramework = null;
		}
		if (serviceDiscovery != null) {
			serviceDiscovery.close();
			serviceDiscovery = null;
		}
	}

	@Override
	public ServiceInstanceMapper getInstance(String name) {
		this.name = name;
		try {
			Collection<ServiceInstance<ZookeeperInstance>> serviceInstances = serviceDiscovery.queryForInstances(name);
			Optional<ServiceInstance<ZookeeperInstance>> first = serviceInstances.stream()
					.filter(instance -> StringUtils.equals(instance.getName(), name)).findFirst();
			if (first.isPresent()) {
				ServiceInstance<ZookeeperInstance> serviceInstance = first.get();
				ServiceInstanceMapper mapper = new ServiceInstanceMapper(serviceInstance);
				return mapper;
			}
		} catch (Exception e) {
			log.error("getInstance Error - ", e);
		}
		return null;
	}


}
