package com.benym.rpamis.common.trace.core;

import com.benym.rpamis.common.dto.exception.ExceptionFactory;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.springframework.core.task.TaskDecorator;

/**
 * Skywalking传递traceid包装器
 *
 * @author benym
 * @date 2023/3/21 14:09
 */
public class SkywalkingTraceDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        try {
            return RunnableWrapper.of(runnable);
        } catch (Exception e) {
            throw ExceptionFactory.sysException("skywalking decorator exception", e);
        }
    }
}
