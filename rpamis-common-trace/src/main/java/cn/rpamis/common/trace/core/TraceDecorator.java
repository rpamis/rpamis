package cn.rpamis.common.trace.core;

import cn.rpamis.common.dto.enums.Trace;
import cn.rpamis.common.utils.SnowflakeUtils;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * @author benym
 * @date 2022/7/10 21:49
 */
public class TraceDecorator implements TaskDecorator {

    /**
     * 线程池绑定主线程traceId，创建线程池时手动指定该装饰器
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(contextMap);
                MDC.put(Trace.SPAN_ID, String.valueOf(SnowflakeUtils.get().next()));
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
