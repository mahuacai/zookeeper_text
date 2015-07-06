package com.test.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class FristTest implements Watcher{
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	public static void main(String[] args) throws IOException, InterruptedException {
		//创建一个 zookeeper 会话
		ZooKeeper zookeeper = new ZooKeeper("192.168.180.129:2181", 5000, new FristTest());
		System.out.println(zookeeper.getState());
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("zookeeper session established");
		Long sessionId = zookeeper.getSessionId();
		byte[] password = zookeeper.getSessionPasswd();
		
		zookeeper = new ZooKeeper("192.168.180.129:2181",5000,new FristTest(),1L,"hello".getBytes());
		
		zookeeper = new ZooKeeper("192.168.180.129:2181",5000,new FristTest(),sessionId,password);
		Thread.sleep(Integer.MAX_VALUE);
	}

	public void process(WatchedEvent event) {
		System.out.println(event);
		if (event.getState() == KeeperState.SyncConnected){
			countDownLatch.countDown();
		}
		
	}
}
