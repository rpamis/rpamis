package com.benym.rpamis.common.trace.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Filter内异常，交给全局异常处理
 *
 * @author benym
 * @date 2022/8/23 12:39
 */
@RestController
public class ExceptionController {
    @RequestMapping("/filterError")
    public void throwFilterError(HttpServletRequest request) throws Exception {
        throw ((Exception) request.getAttribute("filter error"));
    }
}
