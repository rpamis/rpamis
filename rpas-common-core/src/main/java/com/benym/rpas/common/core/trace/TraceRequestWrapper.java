package com.benym.rpas.common.core.trace;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 继承HttpServletRequestWrapper，提供修改Header的方法，加入traceId在请求头
 *
 * @date 2022/7/8 4:52 下午
 */
public class TraceRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> headers;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public TraceWrapper(HttpServletRequest request) {
        super(request);
        this.headers = new HashMap<>();
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(headers.keySet());
        return Collections.enumeration(names);
    }

    public Map<String, String> getHeaders() {
        Enumeration<String> headerNames = super.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, super.getHeader(headerName));
        }
        return headers;
    }

    @Override
    public Enumeration<String> getHeaders(String key) {
        List<String> values = Collections.list(super.getHeaders(key));
        if (headers.containsKey(key)) {
            values = Collections.singletonList(headers.get(key));
        }
        return Collections.enumeration(values);
    }

    @Override
    public String getHeader(String key) {
        String value = super.getHeader(key);
        if (headers.containsKey(key)) {
            value = headers.get(key);
        }
        return value;
    }
}
