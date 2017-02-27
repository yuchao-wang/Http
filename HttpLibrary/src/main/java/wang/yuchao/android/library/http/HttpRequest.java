package wang.yuchao.android.library.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class HttpRequest {
    /**
     * 默认超时时间：20秒
     */
    private static final int DEFAULT_TIME_OUT = 20 * 1000;
    /**
     * 格式处理
     */
    private static final String UTF_8 = "UTF-8";

    private static final String TAG = "HttpLibrary";

    private static String getWholeRequest(String strUrl, HashMap<String, String> allParams) {
        String wholeUrl = "";
        try {
            if (allParams != null && (!allParams.isEmpty())) {
                wholeUrl = strUrl + "?";
                for (Map.Entry<String, String> param : allParams.entrySet()) {
                    wholeUrl = wholeUrl + param.getKey() + "=" + URLEncoder.encode(param.getValue(), UTF_8) + "&";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wholeUrl;
    }

    /**
     * 得到服务器的返回的数据
     *
     * @param connection 连接
     * @return 服务器返回的数据
     */
    private static String getResponse(URLConnection connection) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String response = null;
        try {
            StringBuffer result = new StringBuffer();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                result.append(readLine);
            }
            response = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    /**
     * get请求：提交参数集合
     *
     * @param strUrl    字符串url
     * @param allParams 所有的字符串参数集合
     * @return 服务器相应结果
     */
    public static HttpResponse get(String strUrl, HashMap<String, String> allParams) {
        Log.i(TAG, "get request >>> " + getWholeRequest(strUrl, allParams));

        HttpResponse httpResponse = new HttpResponse();
        HttpURLConnection connection = null;
        try {
            if (allParams != null && (!allParams.isEmpty())) {
                strUrl = strUrl + "?";
                for (Map.Entry<String, String> param : allParams.entrySet()) {
                    strUrl = strUrl + param.getKey() + "="
                            + URLEncoder.encode(param.getValue(), UTF_8) + "&";
                }
            }
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();

            if (connection instanceof HttpsURLConnection) { // 是Https请求
                SSLContext sslContext = SSLContextUtil.getSSLContext();
                if (sslContext != null) {
                    SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
                    ((HttpsURLConnection) connection).setHostnameVerifier(SSLContextUtil.hostnameVerifier);
                }
            }

            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setDoInput(true);    //允许getInputStream
            connection.setDoOutput(false);  //不允许getOutputStream,如果设置为true就是post请求了
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(DEFAULT_TIME_OUT);
            connection.setReadTimeout(DEFAULT_TIME_OUT);
            connection.setUseCaches(false);
            connection.connect();

            httpResponse.setResponseCode(connection.getResponseCode());
            if (httpResponse.isSuccess()) {
                httpResponse.setResponse(getResponse(connection));
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.setResponseCode(0);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        Log.i(TAG, "get response >>> " + httpResponse.toString());
        return httpResponse;
    }

    /**
     * post请求：提交参数集合
     *
     * @param strUrl    字符串url
     * @param allParams 所有字符串参数集合
     * @return 服务器相应结果
     */
    public static HttpResponse post(String strUrl, HashMap<String, String> allParams) {
        Log.i(TAG, "post request >>> " + getWholeRequest(strUrl, allParams));

        HttpResponse httpResponse = new HttpResponse();
        DataOutputStream outputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();

            if (connection instanceof HttpsURLConnection) { // 是Https请求
                SSLContext sslContext = SSLContextUtil.getSSLContext();
                if (sslContext != null) {
                    SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
                    ((HttpsURLConnection) connection).setHostnameVerifier(SSLContextUtil.hostnameVerifier);
                }
            }

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(DEFAULT_TIME_OUT);
            connection.connect();
            if (allParams != null) {
                outputStream = new DataOutputStream(connection.getOutputStream());
                for (Map.Entry<String, String> param : allParams.entrySet()) {
                    outputStream.writeBytes(param.getKey() + "="
                            + URLEncoder.encode(param.getValue(), UTF_8) + "&");
                }
                outputStream.flush();
            }
            httpResponse.setResponseCode(connection.getResponseCode());
            if (httpResponse.isSuccess()) {
                httpResponse.setResponse(getResponse(connection));
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.setResponseCode(0);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        Log.i(TAG, "post response >>> " + httpResponse.toString());
        return httpResponse;
    }
}