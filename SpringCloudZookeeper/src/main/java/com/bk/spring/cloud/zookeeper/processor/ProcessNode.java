package com.bk.spring.cloud.zookeeper.processor;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bk.spring.cloud.zookeeper.model.Node;
import com.bk.spring.cloud.zookeeper.services.ZooKeeperService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessNode implements Runnable{

	@Component
	public class ProcessNodeWatcher implements Watcher{
		@Value("${spring.cloud.zookeeper.discovery.instance-id}")
		private int nodeId;
		@Override
		public void process(WatchedEvent event) {

			log.debug("[Process: " + nodeId + "] Event received: " + event);

			final Event.EventType eventType = event.getType();
			if(Watcher.Event.EventType.NodeDeleted.equals(eventType)) {
				if(event.getPath().equalsIgnoreCase(watchedNodePath)) {
					attemptForLeaderPosition();
				}
			}
		}
	}

	private static String PROCESS_NODE_PREFIX = "/p_";

	private static String LEADER_ELECTION_ROOT_NODE;

	private String processNodePath;

	private String watchedNodePath;

	@Autowired
	private Node node;

	@Autowired
	ZooKeeperService zooKeeperService;

	@Value("${spring.cloud.zookeeper.discovery.instance-id}")
	private int nodeId;

	private void attemptForLeaderPosition() {

		final List<String> childNodePaths = zooKeeperService.getChildren(LEADER_ELECTION_ROOT_NODE, false);
		Collections.sort(childNodePaths);
		int index = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf('/') + 1));
		if(index == 0) {
			log.info("[Process: " + nodeId + "] I am the new leader!");
			node.setLeader(true);
		} else {
			final String watchedNodeShortPath = childNodePaths.get(index - 1);
			node.setLeader(false);
			watchedNodePath = LEADER_ELECTION_ROOT_NODE + "/" + watchedNodeShortPath;
			log.info("[Process: " + nodeId + "] - I am Follower - setting watch on node with path: "
					+ watchedNodePath);
			zooKeeperService.watchNode(watchedNodePath, true);
		}
	}

	@Override
	public void run() {
		log.info("Process with id: " + nodeId + " has started!");
		final String rootNodePath = zooKeeperService.createNode(LEADER_ELECTION_ROOT_NODE, false, false);
		if(rootNodePath == null) {
			throw new IllegalStateException("Unable to create/access leader election root node with path: " + LEADER_ELECTION_ROOT_NODE);
		}
		processNodePath = zooKeeperService.createNode(rootNodePath + PROCESS_NODE_PREFIX, false, true);
		if(processNodePath == null) {
			throw new IllegalStateException("Unable to create/access process node with path: " + LEADER_ELECTION_ROOT_NODE);
		}
		log.debug("[Process: " + nodeId + "] Process node created with path: " + processNodePath);
		attemptForLeaderPosition();
	}

	@Value("${spring.cloud.zookeeper.discovery.node.root}")
	public  void setLEADER_ELECTION_ROOT_NODE(String lEADER_ELECTION_ROOT_NODE) {
		LEADER_ELECTION_ROOT_NODE = lEADER_ELECTION_ROOT_NODE;
	}

	@Value("${spring.cloud.zookeeper.discovery.node.prefix}")
	public void setPROCESS_NODE_PREFIX(String pROCESS_NODE_PREFIX) {
		PROCESS_NODE_PREFIX = pROCESS_NODE_PREFIX;
	}

}
