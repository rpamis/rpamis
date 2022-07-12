package com.benym.rpas.common.core.trace;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @date 2022/7/12 7:28 下午
 */
public class TraceResponseWrapper extends HttpServletResponseWrapper {

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public TraceResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
