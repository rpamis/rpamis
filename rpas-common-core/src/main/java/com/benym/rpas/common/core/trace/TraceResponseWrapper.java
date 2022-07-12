package com.benym.rpas.common.core.trace;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 继承ContentCachingResponseWrapper,方便获取Response
 *
 * @date 2022/7/12 7:28 下午
 */
public class TraceResponseWrapper extends ContentCachingResponseWrapper {

    private final Map<String,String> headers;

    /**
     * Create a new ContentCachingResponseWrapper for the given servlet response.
     *
     * @param response the original servlet response
     */
    public TraceResponseWrapper(HttpServletResponse response) {
        super(response);
        this.headers = new HashMap<>();
    }

    public Map<String, String> getHeaders() {
        Collection<String> headerNames = super.getHeaderNames();
        for(String headerName : headerNames) {
            headers.put(headerName, super.getHeader(headerName));
        }
        return headers;
    }
}
