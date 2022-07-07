package com.benym.rpas.common.dto.exception;

/**
 * @Time : 2022/7/7 22:20
 */
public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errCode;

    public AbstractException(String errMessage) {
        super(errMessage);
    }

    public AbstractException(int errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public AbstractException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    public AbstractException(int errCode, String errMessage,Throwable e){
        super(errMessage,e);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
