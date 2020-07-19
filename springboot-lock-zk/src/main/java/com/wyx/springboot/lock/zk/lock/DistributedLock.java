package com.wyx.springboot.lock.zk.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
@Service
public class DistributedLock {


//	@Qualifier("zkLockClient")
//	private CuratorFramework zkLockClient;
//	/**
//	 * 获取分布式锁
//	 */
//	public void getLock() {
//		while (true) {
//			try {
//				zkLockClient.create()
//						.creatingParentsIfNeeded()
//						.withMode(CreateMode.EPHEMERAL).forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTE_LOCK_NAME);
//				System.out.println("获取分布式锁成功...");
//				return;
//			} catch (Exception e) {
//				try {
//					//如果没有获取到锁,需要重新设置同步资源值
//					if (DISTRIBUTE_LOCK.getCount() <= 0) {
//						DISTRIBUTE_LOCK = new CountDownLatch(1);
//					}
//					System.out.println("获取分布式锁失败,等待他人释放锁中...");
//					DISTRIBUTE_LOCK.await();
//				} catch (InterruptedException ie) {
//					ie.printStackTrace();
//				}
//			}
//		}
//	}

}
