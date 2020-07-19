package com.example.springbootes.service;

import com.example.springbootes.config.ElasticSearchConfig;
import com.wyx.common.model.Result;
import org.apache.lucene.util.fst.Util;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/11
 * @Date : 2020/6/11
 */
@Service
public class IndexService {
	private static final Logger logger = LoggerFactory.getLogger(IndexService.class);


	@Autowired
	@Qualifier(value = "restHighLevelClient")
	private RestHighLevelClient client;

	/**
	 * 创建索引
	 *
	 * @param index
	 * @return
	 */
	public boolean createIndex(String index) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", 1)
				.put("index.number_of_replicas", 1)
		);
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		return createIndexResponse.isAcknowledged();
	}

//	/**
//	 * 判断索引是否存在
//	 *
//	 * @param index
//	 * @return
//	 */
//	public static boolean isIndexExist(String index) {
//		IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
//		if (inExistsResponse.isExists()) {
//			client.info("Index [" + index + "] is exist!");
//		} else {
//			client.info("Index [" + index + "] is not exist!");
//		}
//		return inExistsResponse.isExists();
//	}
//
//
//	/**
//	 * 判断索引是否存在
//	 *
//	 * @param index
//	 * @return
//	 */
//	public boolean isIndexExist(String index) {
//		GetIndexRequest request = new GetIndexRequest();
//		request.indices(index);
//		boolean inExistsResponse = false;
//		try {
//			inExistsResponse = client.indices().exists(request, RequestOptions.DEFAULT);
//		} catch (IOException e) {
//			log.error("isIndexExist ",e);
//		}
//		return inExistsResponse;
//	}

	public Result<Integer> deleteIndex(String index) throws IOException {
		try {
			AcknowledgedResponse response = client.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
			if (response.isAcknowledged()) {
				return Result.success(1);
			}

		} catch (ElasticsearchException exception) {
			if (exception.status() == RestStatus.NOT_FOUND) {
				logger.info("索引不存在");
			}
		}
		return Result.success(0);
	}

//	public Result<Integer> isIndexExists(String index) {
//		GetIndexRequest request = new GetIndexRequest(index);
//		try {
//			boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
//
//		} catch (IOException e) {
//			Result.fail(e.getMessage());
//		}
//	}

	public void updateByQuery() {
//		client.updateByQuery()
	}

}
