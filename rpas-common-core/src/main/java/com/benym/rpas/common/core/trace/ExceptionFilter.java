package com.benym.rpas.common.core.trace;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * filter异常处理器，转发filter内的异常到ExceptionController
 * filter在controller之前处理，全局异常需要转发filter异常到controller，再由controller抛出
 *
 * @author benym
 * @date 2022/8/23 14:00
 */
@Order(Integer.MIN_VALUE)
@WebFilter(filterName = "exceptionFilter", urlPatterns = {"/*"})
@Component
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute("filter error", e);
            request.getRequestDispatcher("/filterError").forward(request, response);
        }
    }
}
