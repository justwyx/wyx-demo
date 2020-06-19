package com.example.springbootes.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/11
 * @Date : 2020/6/11
 */
@Configuration
public class ElasticSearchConfig {
	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);

	@Value("${elasticSearch.host}")
	private String host;

	@Value("${elasticSearch.port}")
	private int port;

	@Value("${elasticSearch.client.connectNum}")
	private Integer connectNum;

	@Value("${elasticSearch.client.connectPerRoute}")
	private Integer connectPerRoute;

	@Bean
	public HttpHost httpHost(){
		return new HttpHost(host,port,"http");
	}

	@Bean(initMethod="init",destroyMethod="close")
	public ESClientSpringFactory getFactory(){
		return ESClientSpringFactory.
				build(httpHost(), connectNum, connectPerRoute);
	}

	@Bean
	@Scope("singleton")
	public RestClient getRestClient(){
		return getFactory().getClient();
	}

	@Bean(name = "restHighLevelClient")
	@Scope("singleton")
	public RestHighLevelClient restHighLevelClient(){
		return getFactory().getRhlClient();
	}
}
