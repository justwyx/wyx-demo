package com.wyx.springbootkafka.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description : 发送消息
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@Data
public class TopicMessageParam implements Serializable {
	private static final long serialVersionUID = -3411299171039945763L;

	private String topic;

	private String message;

	@Override
	public String toString() {
		return "TopicMessageParam{" +
				"topic='" + topic + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}