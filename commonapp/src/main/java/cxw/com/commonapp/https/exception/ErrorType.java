package cxw.com.commonapp.https.exception;

/**
 * 约定异常
 */

public interface ErrorType {

    //---------- ExceptionEngine使用 通过ExceptionEngin指定的 -------

    /**
     * 解析错误 JsonParseException JSONException ParseException
     */
    int PARSE_ERROR = 1001;
    /**
     * 网络超时和链接异常 ConnectException SocketTimeoutException ConnectTimeoutException UnknownHostException
     */
    int NETWORD_ERROR = 1002;
    /**
     * 协议出错  HttpException
     */
    int HTTP_ERROR = 1003;
    /**
     * 未知错误
     */
    int UNKNOWN_ERROR = 1000;

    //----------服务器返回的 异常状态,服务器不同值不同，通过HttpResult的实现类HttpResultImpl可以转换 -------
    /**
     * 服务器返回de非SUCCESS的错误
     */
    /**
     *
     */
    int ERROR = -1;
    /**
     * 未登录
     */
    int NOT_LOGIN = -1001;
    /**
     * 正常
     */
    int SUCCESS = 0;


}
