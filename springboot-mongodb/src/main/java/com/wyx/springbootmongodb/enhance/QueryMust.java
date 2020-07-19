package com.wyx.springbootmongodb.enhance;

import java.lang.annotation.*;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/15
 * @Date : 2020/7/15
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryMust {
	String field() default "";

}
