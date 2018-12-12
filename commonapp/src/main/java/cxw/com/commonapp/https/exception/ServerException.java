package cxw.com.commonapp.https.exception;

import cxw.com.commonapp.https.httpresult.HttpResult;

public class ServerException extends RuntimeException {
    // 异常处理，为速度，不必要设置getter和setter
    public int code;
    public String message;
    public HttpResult httpResult;

    public ServerException(String message, int code, HttpResult httpResult) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpResult = httpResult;
    }

    public ServerException(Throwable e, String message, int code) {
        super(e);
        this.code = code;
        this.message = message == null ? e.getMessage() : message;
    }
}
