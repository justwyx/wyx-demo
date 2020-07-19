package com.wyx.springbootmongodb.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.wyx.springbootmongodb.dto.QueryDTO;
import com.wyx.springbootmongodb.enhance.QueryMust;
import com.wyx.springbootmongodb.entity.BatchUpdateOptions;
import com.wyx.springbootmongodb.entity.MerchantSku;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/7
 * @Date : 2020/7/7
 */
@Service
public class MongodbService {
	private static final Logger logger = LoggerFactory.getLogger(MongodbService.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<MerchantSku> query(String collectionName, QueryDTO param) {
		Query query = buildQuery(param);

		System.out.println("query:" + query.toString());
		return mongoTemplate.find(query, MerchantSku.class, collectionName);
	}

	private static Query buildQuery(QueryDTO queryParam) {
		Query query = new Query(buildCriteria(new Criteria(), queryParam));
		if (queryParam.getSkip() != null) {
			query.skip(queryParam.getSkip());
		}
		if (queryParam.getLimit() != null) {
			query.limit(queryParam.getLimit());
		}
		return query;
	}



	private static <Q> Criteria buildCriteria(Criteria criteria, Q queryBean) {
		Class queryBeanClass = queryBean.getClass();
		Field[] fields = queryBeanClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(QueryMust.class) == null) {
				continue;
			}
			String queryField = field.getAnnotation(QueryMust.class).field();
			if (StringUtils.isEmpty(queryField)) {
				queryField = field.getName();
			}
			try {
				Object value = field.get(queryBean);
				if (value == null) {
					continue;
				}
				if (value instanceof List) {
					List valueList = (List) value;
					if (valueList.size() > 0) {
						criteria.and(queryField).in(valueList);
					}
				} else {
					criteria.and(queryField).is(value);
				}
			} catch (IllegalAccessException e) {
//				log.error("mustBuild error", e);
			}
		}
		return criteria;
	}



	public MerchantSku findById(String collectionName, Integer id) {
		return mongoTemplate.findById(id, MerchantSku.class, collectionName);
	}

	public List<MerchantSku> findByIdList(String collectionName, List<Integer> idList) {
		Query query = new Query(Criteria.where("_id").in(idList));
		return mongoTemplate.find(query, MerchantSku.class, collectionName);
	}

	public List<MerchantSku> findByIdList1(String collectionName, List<Integer> idList) {
		Query query = new Query(Criteria.where("_id").in(idList).andOperator(Criteria.where("merchantEdit").is(true)));


		return mongoTemplate.find(query, MerchantSku.class, collectionName);
	}

	public int insertAndIdIsNotNull(String collectionName, List<MerchantSku> paramList) {
		// assert collectionName is not null
		// assert paramList is not null and id is not null
		try {
			return mongoTemplate.insert(paramList, collectionName).size();
		} catch (Exception e) {
			return 0;
		}
	}

	public int upsertById(String collectionName, MerchantSku sku) {
		// assert collectionName is not null
		// assert sku is not null
		Query query = new Query(Criteria.where("_id").is(sku.getId()));
		mongoTemplate.upsert(query, sku.convertToUpdate(), collectionName);
		return 1;
	}


	public  int batchUpsertById(String collectionName, List<MerchantSku> skuList) {
		// assert collectionName is not null
		// assert skuList is not null
		List<BatchUpdateOptions> optionsList = new ArrayList<>();
		for (MerchantSku sku : skuList) {
			BatchUpdateOptions options = new BatchUpdateOptions();
			options.setQuery(new Query(Criteria.where("_id").is(sku.getId())));
			options.setUpdate(sku.convertToUpdate());
			optionsList.add(options);
		}

		return doBatchUpdate(mongoTemplate, collectionName, optionsList);
	}



	private static int doBatchUpdate(MongoTemplate mongoTemplate, String collName, List<BatchUpdateOptions> options) {
		return doBatchUpdate(mongoTemplate, collName, options, false);
	}
	/**
	 * 批量更新
	 * @param ordered 如果为true,一条语句更新失败，剩下的语句将不再执。如果为false,一条语句更新失败，剩下的将继续执行。默认为false。
	 * @return
	 */
	private static int doBatchUpdate(MongoTemplate mongoTemplate, String collName, List<BatchUpdateOptions> options, boolean ordered) {
		List<BasicDBObject> updateList = new ArrayList<>();
		for (BatchUpdateOptions option : options) {
			BasicDBObject update = new BasicDBObject();
			update.put("q", option.getQuery().getQueryObject());
			update.put("u", option.getUpdate().getUpdateObject());
			update.put("upsert", option.isUpsert());
			update.put("multi", option.isMulti());
			updateList.add(update);
		}
		DBObject command = new BasicDBObject();
		command.put("update", collName);
		command.put("updates", updateList);
		command.put("ordered", ordered);
		Document document = mongoTemplate.getDb().runCommand((Bson) command);
		System.out.println("doc:"+document);
		System.out.println("doc--n:"+document.get("n"));
		System.out.println("doc--nModified:"+document.get("nModified"));

		// n为符合Query查询的记录总数 因为是根据id进行的查询, 原则上只要符合查询的记录数等于要更新的数量就代表成功
		Object n = document.get("n");
		System.out.println("doc--n--class:"+n.getClass());

		if(n.getClass()==Integer.class){
			return (Integer)n;
		}
		return Integer.parseInt(String.valueOf(n));
	}
}
