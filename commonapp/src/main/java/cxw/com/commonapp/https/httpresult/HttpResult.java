package cxw.com.commonapp.https.httpresult;

public interface HttpResult<T> {

    int getStatus();

    String getMessage();

    T getData();

}