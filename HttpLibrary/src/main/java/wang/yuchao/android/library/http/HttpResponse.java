package wang.yuchao.android.library.http;


/**
 * Created by wangyuchao on 15/11/18.
 */
public final class HttpResponse {

    private static final String REQUEST_SUCCESS = "网络请求成功";
    private static final String REQUEST_FAIL_UN_STABLE = "网络不稳定,请稍后重试";
    private static final String REQUEST_FAIL_SEVER_ERROR = "服务器错误";
    private static final String REQUEST_FAIL_CLIENT_ERROR = "客户端请求错误";
    private static final String REQUEST_FAIL_UN_CONNECT = "网络未连接";
    private static final String REQUEST_FAIL_INTERNET = "网络异常";

    private static final String REQUEST_UNKNOWN = "网络连接超时";

    /**
     * 网络请求错误码
     */
    private int responseCode = 0;

    /**
     * 网络请求返回数据
     */
    private String response = "";

    /**
     * 网络请求提示信息，注意：不要跟服务器请求成功后自定义返回的错误信息混淆
     */
    private String errorMessage = REQUEST_UNKNOWN;

    @Override
    public String toString() {
        return "HttpResponse{" +
                "responseCode=" + responseCode +
                ", response='" + response + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    /**
     * 请求是否成功，以及填充错误信息
     */
    public boolean isSuccess() {
        return responseCode >= 200 && responseCode < 300;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
        //遇到这种情况很少，虽然code拿到了，isSuccess(),但是没有拉取成功。UI：提示：网络不稳定
        if (response == null) {
            errorMessage = REQUEST_FAIL_UN_STABLE;
            responseCode = -1;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        if (responseCode >= 200 && responseCode < 300) {
            //UI：请求数据成功,然后再分为两个业务类：success(),fail()
            errorMessage = REQUEST_SUCCESS;
        } else if (responseCode == 600) {
            //源站没有返回响应头部，只返回实体内容
            errorMessage = REQUEST_FAIL_SEVER_ERROR;
        } else if (responseCode >= 500 && responseCode < 600) {
            // UI：提示：服务器错误 5XX
            errorMessage = REQUEST_FAIL_SEVER_ERROR;
        } else if (responseCode >= 400 && responseCode < 500) {
            // UI：提示：客户端请求错误4XX（基本上不会出现，除非客户端写错地址或者服务器更改地址了）
            errorMessage = REQUEST_FAIL_CLIENT_ERROR;
        } else if (responseCode >= 300 && responseCode < 400) {
            // UI：提示：网络(重定向之类的)。responseCode为0或者3XX
            errorMessage = REQUEST_FAIL_INTERNET;
        } else {
            if (!NetworkUtil.isNetworkConnected()) {
                // UI：提示：网络未连接
                errorMessage = REQUEST_FAIL_UN_CONNECT;
            } else {
                //未知错误
                errorMessage = REQUEST_UNKNOWN;
            }
        }
    }
}
