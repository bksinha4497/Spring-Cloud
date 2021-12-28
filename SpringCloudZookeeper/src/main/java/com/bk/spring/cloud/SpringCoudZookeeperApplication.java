package com.bk.spring.cloud;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.bk.spring.cloud.zookeeper.processor.ProcessNode;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class SpringCoudZookeeperApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringCoudZookeeperApplication.class, args);
	}

	@Autowired
	private ProcessNode processNode;

	@Override
	public void run(String... args) throws Exception {

		final ExecutorService service = Executors.newSingleThreadExecutor();

		final Future<?> status = service.submit(processNode);

		try {
			status.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage(), e);
			service.shutdown();
		}
	}

}
