package com.x3platform.datasource;

import java.lang.reflect.Method;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.x3platform.data.DataSourceName;
import com.x3platform.data.DynamicDataSourceContextHolder;

/**
 * 数据源注入，新加入服务包时需要修改此类。
 *
 * @author dev
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSourceName ds) throws Throwable {
        String key = ds.value();
        if (!DynamicDataSourceContextHolder.containsDataSourceKey(key)) {
            logger.error("数据源[{}]不存在，使用默认数据源 -> {}", key, point.getSignature());
        } else {
            logger.debug("使用数据源 : {} -> {}", ds.value(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceKey(key);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSourceName ds) {
        logger.debug("还原数据源 : {} -> {}", ds.value(), point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }
}

