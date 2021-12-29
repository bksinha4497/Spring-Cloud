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


	private static String leaderElectionRootNode;
	private static String processNodePrefix;

	private String processNodePath;

	private String watchedNodePath;

	@Autowired
	private Node node;

	@Autowired
	ZooKeeperService zooKeeperService;

	@Value("${spring.cloud.zookeeper.discovery.instance-id}")
	private int nodeId;

	private void attemptForLeaderPosition() {

		final List<String> childNodePaths = zooKeeperService.getChildren(leaderElectionRootNode, false);
		Collections.sort(childNodePaths);
		int index = childNodePaths.indexOf(processNodePath.substring(processNodePath.lastIndexOf('/') + 1));
		if(index == 0) {
			log.info("[Process: " + nodeId + "] I am the new leader!");
			node.setLeader(true);
		} else {
			final String watchedNodeShortPath = childNodePaths.get(index - 1);
			node.setLeader(false);
			watchedNodePath = leaderElectionRootNode + "/" + watchedNodeShortPath;
			log.info("[Process: " + nodeId + "] - I am Follower - setting watch on node with path: "
					+ watchedNodePath);
			zooKeeperService.watchNode(watchedNodePath, true);
		}
	}

	@Override
	public void run() {
		log.info("Process with id: " + nodeId + " has started!");
		final String rootNodePath = zooKeeperService.createNode(leaderElectionRootNode, false, false);
		if(rootNodePath == null) {
			throw new IllegalStateException(
					"Unable to create/access leader election root node with path: " + leaderElectionRootNode);
		}
		processNodePath = zooKeeperService.createNode(rootNodePath + processNodePrefix, false, true);
		if(processNodePath == null) {
			throw new IllegalStateException(
					"Unable to create/access process node with path: " + leaderElectionRootNode);
		}
		log.debug("[Process: " + nodeId + "] Process node created with path: " + processNodePath);
		attemptForLeaderPosition();
	}

	@Value("${spring.cloud.zookeeper.discovery.node.root}")
	public void setLeaderElectionRootNode(String leaderElectionRootNode) {
		this.leaderElectionRootNode = leaderElectionRootNode;
	}

	@Value("${spring.cloud.zookeeper.discovery.node.prefix}")
	public void setProcessNodePrefix(String processNodePrefix) {
		this.processNodePrefix = processNodePrefix;
	}

}
