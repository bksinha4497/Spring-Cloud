package com.bk.cloud.discovery.zookeeper.interfaces;

import com.bk.cloud.discovery.zookeeper.ServiceInstanceMapper;

public interface IServiceRegistry extends AutoCloseable {

	ServiceInstanceMapper getInstance(String name);
}
