package com.wyx.springbootkafka.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/25
 * @Date : 2020/5/25
 */
@Data
public class TopicMessageParam implements Serializable {
	private static final long serialVersionUID = -3411299171039945763L;

	private String message;
}
