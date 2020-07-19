package com.wyx.springbootmongodb.entity;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/9
 * @Date : 2020/7/9
 */
public class BatchUpdateOptions {
	private Query query;
	private Update update;
	/**
	 * 如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入
	 */
	private boolean upsert = true;
	/**
	 * mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新
	 */
	private boolean multi = false;


	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public boolean isUpsert() {
		return upsert;
	}

	public void setUpsert(boolean upsert) {
		this.upsert = upsert;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}
}
