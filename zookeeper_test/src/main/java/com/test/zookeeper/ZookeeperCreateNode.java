package com.test.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperCreateNode implements Watcher{
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zookeeper = new ZooKeeper("192.168.180.129",
				5000, new ZookeeperCreateNode());
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = new String("/mahua-test-");
		zookeeper.create(path, "hello word!".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,new IStringCallback(),"i am context");
		zookeeper.create(path, "hello word".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL,new IStringCallback(),"i am context");
		Thread.sleep(6000);
	}
	
	public void process(WatchedEvent event) {
		System.out.println( event);
		if (KeeperState.SyncConnected == event.getState()){
			countDownLatch.countDown();
		}
		
	}
}
class IStringCallback implements AsyncCallback.StringCallback{

	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("craete path result ["+rc+","+path+","+ctx+","+name);	
	}
	
}
