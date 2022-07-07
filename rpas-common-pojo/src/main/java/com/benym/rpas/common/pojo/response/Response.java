package com.benym.rpas.common.pojo.response;

import com.benym.rpas.common.pojo.enums.ResponseCode;
import com.benym.rpas.common.pojo.enums.StatusCode;
import java.io.Serializable;

/**
 * @Time : 2022/7/6 21:17
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int errCode;

    private String errMessage;

    private String detailMessage;

    private T data;

    public Response() {

    }

    public Response(int errCode, String errMessage, String detailMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.detailMessage = detailMessage;
    }

    public Response(int errCode, String errMessage) {
        this(errCode, errMessage, "");
    }

    public Response(T data) {
        errCode = 0;
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

    public static <T> Response<T> success(T data, int errCode, String errMessage) {
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


    public static <T> Response<T> fail(int errCode, String errMessage) {
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

    public static <T> Response<T> fail(T data, int errCode, String errMessage) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
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

    @Override
    public String toString() {
        return "Response{" +
                "errCode=" + errCode +
                ", errMessage='" + errMessage + '\'' +
                ", detailMessage='" + detailMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
