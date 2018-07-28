package com.dm.trade.payment.miniWechatPay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 接口调用封装类
 * @author TL
 *
 */
public class BaseApi {

    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     *
     * @Title: sendPostHttps
     * @Description: 证书请求
     * @param @param url
     * @param @param postDataXML
     * @param @return
     * @param @throws NoSuchAlgorithmException
     * @param @throws CertificateException
     * @param @throws IOException
     * @param @throws KeyManagementException
     * @param @throws UnrecoverableKeyException
     * @param @throws KeyStoreException    设定文件
     * @return String    返回类型
     * @throws
     */
    protected String sendPostHttps(String url, String postDataXML) throws Exception {
        this.logger.info("sendPostHttps====》"+postDataXML);
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(Constant.CERTLOCALPATH));
        try {
            keyStore.load(instream, Constant.CERTPASSWORD.toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Constant.CERTPASSWORD.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        //返回结果
        StringBuffer result = new StringBuffer();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(postDataXML, ContentType.create("text/xml", "UTF-8")));

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        result.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result.toString();
    }

    /**
     * 无证书请求
     * @Title: sendPostHttp
     * @Description: TODO
     * @param @param url
     * @param @param params
     * @param @return
     * @param @throws Exception    设定文件
     * @return String    返回类型
     * @throws
     */
    protected String sendPostHttp(String url, String params) throws Exception {
        this.logger.info("sendPostHttp====》"+params);
        StringBuffer result = new StringBuffer();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(params, ContentType.create("text/xml", "UTF-8")));

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        result.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result.toString();
    }

}
