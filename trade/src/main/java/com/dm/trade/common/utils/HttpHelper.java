package com.dm.trade.common.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author: Roy.Lust@gmail.com
 * 9/17/16
 * 11:23 PM
 */
public class HttpHelper {

    private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static void main(String[] args) {
        System.out.println(getTimeout("http://113.204.225.166:8090/DataSnap/Rest/TserverMethods1/queryplate/ABP162", 10));
    }

    /**
     * HttpClient get方法封装
     *
     * @param url URL
     * @return String
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", "application/x-www-form-urlencoded; encoding=UTF-8");
        get.addHeader("accept", "text/html,application/json,application/xhtml+xml,application/xml");

        HttpClient client = HttpClients.createDefault();

        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * 自定义超时请求
     *
     * @param url 请求地址
     * @return 返回内容
     */
    public static String getTimeout(final String url, final int timeout) {
        final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

        final HttpGet get = new HttpGet(url);
        HttpClient client = HttpClients.createDefault();
        get.addHeader("Content-Type", "application/x-www-form-urlencoded; encoding=UTF-8");
        get.addHeader("accept", "text/html,application/json,application/xhtml+xml,application/xml");

        try {
            /**
             * 开始计时
             */
            final Runnable delayedTask = new Runnable() {
                @Override
                public void run() {
                    logger.debug("关闭HTTP超时线程 {} {}", timeout, url);
                    get.abort();
                }
            };
            // 老子先跑起来，看你怎么超时
            SCHEDULED_EXECUTOR.schedule(delayedTask, timeout, TimeUnit.SECONDS);

            HttpResponse response = client.execute(get);
            // 如果不关闭要内存泄漏
            SCHEDULED_EXECUTOR.shutdown();
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            logger.info("{} getTimeout 异常了 {}", url, e.getMessage());
            // 如果不关闭要内存泄漏
            SCHEDULED_EXECUTOR.shutdown();
            return "";
        }
    }

    /**
     * HttpClient post方法封装
     *
     * @param url    URL
     * @param params 参数 可为null
     * @return String
     */
    public static String post(String url, List<NameValuePair> params) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Charset", "UTF-8");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; encoding=UTF-8");
        httpPost.addHeader("Accept", "text/html,application/json,application/xhtml+xml,application/xml");

        String body = "";
        // 设置参数
        if (params != null && params.size() > 0) {
            // 兼容无参数POST
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        }
        // 发送请求
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        body = EntityUtils.toString(entity);
        // 关闭请求
        response.close();
        return body;
    }

    /**
     * POST BODY
     *
     * @param url       地址
     * @param paramBody 参数
     * @return String
     */
    public static String postJson(String url, String paramBody) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Charset", "UTF-8");
        httpPost.addHeader("Content-Type", "application/json; encoding=UTF-8");
        httpPost.addHeader("Accept", "text/html,application/json,application/xhtml+xml,application/xml");
        httpPost.addHeader("User-Agent", "cqkqinfo.com/0.0.10");

        HttpEntity bodyEntity = new ByteArrayEntity(paramBody.getBytes("UTF-8"));
        httpPost.setEntity(bodyEntity);

        // 发送请求
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        // 关闭请求
        response.close();
        return body;
    }


    /**
     * HttpClient post方法封装
     *
     * @param url     URL
     * @param params  参数 可为null
     * @param timeout 超时设定
     * @return String
     */
    public static String postTimeout(String url, List<NameValuePair> params, int timeout) throws Exception {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Charset", "UTF-8");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; encoding=UTF-8");
        httpPost.addHeader("Accept", "text/html,application/json,application/xhtml+xml,application/xml");
        httpPost.addHeader("User-Agent", "cqkqinfo.com/0.0.6");

        String body = "";
        // 设置参数
        if (params != null && params.size() > 0) {
            // 兼容无参数POST
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        }
        // 发送请求
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        body = EntityUtils.toString(entity);
        // 关闭请求
        response.close();
        return body;
    }


}
