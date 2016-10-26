package com.example.oschina_client.api;

import java.util.Locale;

import org.apache.http.client.params.ClientPNames;

import android.content.Context;
import android.util.Log;


import com.example.oschina_client.AppContext;
import com.example.oschina_client.utils.TLog;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 网络请求信息类 ，http请求层封装
 * @author yuxuehai
 *
 */
public class ApiHttpClient {

    public final static String HOST = "www.oschina.net";
    private static String API_URL = "http://www.oschina.net/%s";
    
    public final static String HOST_LOCAL = "113.250.153.35";
    private static String API_URL_LOCAL = "http://113.250.153.35:8080/%s";
    
    // public final static String HOST = "192.168.1.46";
    // private static String API_URL = "http://192.168.1.46/%s";
    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static AsyncHttpClient client;

    public ApiHttpClient() {}

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static void clearUserCookies(Context context) {
        // (new HttpClientCookieStore(context)).a();
    }

    public static void delete(String partUrl, AsyncHttpResponseHandler handler) {
        client.delete(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("DELETE ").append(partUrl).toString());
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("GET ").append(partUrl).toString());
    }

    public static void get(String partUrl, RequestParams params,
            AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("GET ").append(partUrl).append("&")
                .append(params).toString());
    }

    /**
     * 鑾峰彇灞�鍩熺綉鏈嶅姟鍣ㄦ暟鎹�
     * @param partUrl
     * @param params
     * @param handler
     */
    public static void getLocal(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getLocalAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("GET ").append(partUrl).toString());
    }

    /**
     * 鑾峰彇灞�鍩熺綉鏈嶅姟鍣ㄦ暟鎹�
     * @param partUrl
     * @param params
     * @param handler
     */
    public static void getLocal(String partUrl, RequestParams params,
    		AsyncHttpResponseHandler handler) {
    	client.get(getLocalAbsoluteApiUrl(partUrl), params, handler);
    	log(new StringBuilder("GET ").append(partUrl).append("&")
    			.append(params).toString());
    }
    
    public static String getLocalAbsoluteApiUrl(String partUrl) {
        String url = String.format(API_URL_LOCAL, partUrl);
        Log.d("BASE_CLIENT", "request:" + url);
        return url;
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = String.format(API_URL, partUrl);
        Log.d("BASE_CLIENT", "request:" + url);
        return url;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void getDirect(String url, AsyncHttpResponseHandler handler) {
        client.get(url, handler);
        log(new StringBuilder("GET ").append(url).toString());
    }

    public static void log(String log) {
        Log.d("BaseApi", log);
        TLog.log("Test", log);
    }

    public static void post(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("POST ").append(partUrl).toString());
    }

    public static void post(String partUrl, RequestParams params,
            AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("POST ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void postDirect(String url, RequestParams params,
            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
        log(new StringBuilder("POST ").append(url).append("&").append(params)
                .toString());
    }

    public static void put(String partUrl, AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("PUT ").append(partUrl).toString());
    }

    public static void put(String partUrl, RequestParams params,
            AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("PUT ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public static void setHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
        client.addHeader("Host", HOST);
        client.addHeader("Connection", "Keep-Alive");
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        setUserAgent(ApiClientHelper.getUserAgent(AppContext.getInstance()));
    }

    public static void setUserAgent(String userAgent) {
        client.setUserAgent(userAgent);
    }

    public static void setCookie(String cookie) {
        client.addHeader("Cookie", cookie);
    }

    private static String appCookie;

    public static void cleanCookie() {
        appCookie = "";
    }

    public static String getCookie(AppContext appContext) {
        if (appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }
}
