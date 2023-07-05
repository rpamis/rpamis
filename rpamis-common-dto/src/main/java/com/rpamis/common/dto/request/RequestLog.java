package com.rpamis.common.dto.request;

/**
 * 请求日志实体
 *
 * @author benym
 * @date 2022/7/12 6:49 下午
 */
public class RequestLog {

  /**
   * 请求Url
   */
  private String requestUrl;

  /**
   * 调用者地址
   */
  private String remoteAddr;

  /**
   * 请求头
   */
  private Object requestHeaders;

  /**
   * 被调用方法
   */
  private String method;

  /**
   * 请求参数
   */
  private Object requestParams;

  /**
   * 响应状态
   */
  private Integer status;

  /**
   * 响应头
   */
  private Object responseHeaders;

  /**
   * 响应数据
   */
  private Object response;

  /**
   * 接口耗时
   */
  private Long totalTime;

  public RequestLog() {
    // 空实例化
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  public String getRemoteAddr() {
    return remoteAddr;
  }

  public void setRemoteAddr(String remoteAddr) {
    this.remoteAddr = remoteAddr;
  }

  public Object getRequestHeaders() {
    return requestHeaders;
  }

  public void setRequestHeaders(Object requestHeaders) {
    this.requestHeaders = requestHeaders;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Object getRequestParams() {
    return requestParams;
  }

  public void setRequestParams(Object requestParams) {
    this.requestParams = requestParams;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Object getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(Object responseHeaders) {
    this.responseHeaders = responseHeaders;
  }

  public Object getResponse() {
    return response;
  }

  public void setResponse(Object response) {
    this.response = response;
  }

  public Long getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(Long totalTime) {
    this.totalTime = totalTime;
  }

  @Override
  public String toString() {
    return "RequestLog{" +
        "requestUrl='" + requestUrl + '\'' +
        ", remoteAddr='" + remoteAddr + '\'' +
        ", requestHeaders=" + requestHeaders +
        ", method='" + method + '\'' +
        ", requestParams=" + requestParams +
        ", status=" + status +
        ", responseHeaders=" + responseHeaders +
        ", response=" + response +
        ", totalTime=" + totalTime +
        '}';
  }
}
