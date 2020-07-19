//package com.wyx.springboot.lock.zk.config;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.curator.retry.RetryNTimes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
///**
// * @author : Just wyx
// * @Description : TODO 2020/7/19
// * @Date : 2020/7/19
// */
//@Component
//public class ZkConfig {
//	final Logger log = LoggerFactory.getLogger(this.getClass());
//
//
//	private static final String root = "/lock";
//	public static final  String ZK_SERVER = "127.0.0.1:2181";
//
//
//
//	@Bean("zkLockClient")
//	public CuratorFramework zkLockClient() {
//		if (StringUtils.isBlank(root)) {
//			throw new RuntimeException("zookeeper 'root' can't be null");
//		}
//		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
//
//		return CuratorFrameworkFactory
//				.builder()
//				.connectString(ZK_SERVER)
//				.retryPolicy(retryPolicy)
//				.namespace(root)
//				.build();
//	}
//}
