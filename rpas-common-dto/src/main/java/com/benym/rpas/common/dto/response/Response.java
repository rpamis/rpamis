package com.benym.rpas.common.dto.response;

import com.benym.rpas.common.dto.enums.ResponseCode;
import com.benym.rpas.common.dto.enums.StatusCode;
import java.io.Serializable;

/**
 * @Time : 2022/7/6 21:17
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errCode;

    private String errMessage;

    private String detailMessage;

    private String traceId;

    private T data;

    public Response() {

    }

    public Response(String errCode, String errMessage, String detailMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.detailMessage = detailMessage;
    }

    public Response(String errCode, String errMessage) {
        this(errCode, errMessage, "");
    }

    public Response(T data) {
        errCode = "";
        errMessage = "";
        setData(data);
    }

    public Response(StatusCode statusCode) {
        errCode = statusCode.getCode();
        errMessage = statusCode.getMessage();
    }

    public static <T> Response<T> success() {
        return new Response<>(ResponseCode.SUCCESS);
    }

    public static <T> Response<T> success(T data, String errCode, String errMessage) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> Response<T> success(T data) {
        return success(data, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    public static <T> Response<T> success(T data, String errMessage) {
        return success(data, ResponseCode.SUCCESS.getCode(), errMessage);
    }


    public static <T> Response<T> fail(String errCode, String errMessage) {
        Response<T> response = new Response<>();
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> Response<T> fail() {
        return fail(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage());
    }

    public static <T> Response<T> fail(StatusCode statusCode) {
        return fail(statusCode.getCode(), statusCode.getMessage());
    }

    public static <T> Response<T> fail(StatusCode statusCode, String detailMessage) {
        Response<T> response = new Response<>();
        response.setErrCode(statusCode.getCode());
        response.setErrMessage(statusCode.getMessage());
        response.setDetailMessage(detailMessage);
        return response;
    }

    public static <T> Response<T> fail(T data, String errCode, String errMessage) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "errCode=" + errCode +
                ", errMessage='" + errMessage + '\'' +
                ", detailMessage='" + detailMessage + '\'' +
                ", traceId='" + traceId + '\'' +
                ", data=" + data +
                '}';
    }
}
