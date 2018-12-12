package cxw.com.commonapp.https.httpresult;



import com.google.gson.annotations.SerializedName;

import cxw.com.commonapp.https.exception.ErrorType;

/**
 * @description 解析网络请求返回数据解析类
 */
public class HttpResultImpl<T> implements HttpResult<T>  {
    public int errorCode;
    public String errorMsg;
    public T data;


    @Override
    public int getStatus() {
        return errorCode==0?ErrorType.SUCCESS:errorCode;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    @Override
    public T getData() {
        return data;
    }
}
