package com.bk.cloud.discovery.zookeeper.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bk.cloud.discovery.zookeeper.ServiceRegistry;

@Configuration
public class ZookeeperAutoConfiguration {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ServiceRegistry serviceRegistry(CuratorFramework curatorFramework,
			ZookeeperDiscoveryProperties zookeeperDiscoveryProperties) {
		return new com.bk.cloud.discovery.zookeeper.ServiceRegistry(curatorFramework, zookeeperDiscoveryProperties);
	}
}
