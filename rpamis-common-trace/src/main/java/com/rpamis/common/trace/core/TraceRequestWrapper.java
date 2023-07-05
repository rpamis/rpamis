package com.rpamis.common.trace.core;

import com.rpamis.common.dto.exception.ExceptionFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 继承HttpServletRequestWrapper，提供修改Header的方法，加入traceId在请求头
 *
 * @author benym
 * @date 2022/7/8 4:52 下午
 */
public class TraceRequestWrapper extends HttpServletRequestWrapper {

  private final Map<String, String> headers;

  /**
   * 用于存储post参数 避免HttpServletRequest的getInputStream()和getReader()因为只能读取一次，造成异常
   */
  private final byte[] postData;

  /**
   * Constructs a request object wrapping the given request.
   *
   * @param request The request to wrap
   * @throws IllegalArgumentException if the request is null
   */
  public TraceRequestWrapper(HttpServletRequest request) {
    super(request);
    this.headers = new HashMap<>();
    StringBuilder data = new StringBuilder();
    String line;
    BufferedReader reader;
    try {
      //
      reader = request.getReader();
      while (null != (line = reader.readLine())) {
        data.append(line);
      }
    } catch (Exception e) {
      throw ExceptionFactory.sysException("Trace Rquest Wrapper error: ", e);
    }
    this.postData = data.toString().getBytes(StandardCharsets.UTF_8);
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

  public String getPostData() {
    return new String(postData, StandardCharsets.UTF_8);
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(postData);
    return new ServletInputStream() {

      @Override
      public int read() throws IOException {
        return byteArrayInputStream.read();
      }

      @Override
      public void setReadListener(ReadListener listener) {
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public boolean isFinished() {
        return false;
      }
    };
  }
}
