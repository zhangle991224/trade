package com.dm.trade.common.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 接口调用封装类
 * @author TL
 *
 */
public class ApiCall {
	private Logger logger = Logger.getLogger(ApiCall.class);
	/**
	 * 参数实例
	 */
	protected Map<String , String> params = null;

	/**
	 * POST方式接口访问
	 * @param urlPath
	 * @param params
	 * @return
	 */
	public String postCallApi(String urlPath, Map<String, String> params) throws RuntimeException{
//		this.logger.info("urlPath========>"+urlPath+"    params=====>"+ JsonHandler.MapToJson(params));
		final HttpClient httpClient = new HttpClient();
        final PostMethod method = new PostMethod(urlPath);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        method.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        if(params != null){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                method.setParameter(entry.getKey(), entry.getValue());
            }
        }
        String response = "";
        try{
            int status = httpClient.executeMethod(method);
            if(status >= 300 || status < 200){
                throw new RuntimeException("invoke api failed, urlPath:" + urlPath
                        + " status:" + status + " response:" + method.getResponseBodyAsString());
            }
            response = this.parserResponse(method);
        } catch (HttpException e) {
        	logger.error(e.getMessage());
        } catch (IOException e) {
        	logger.error(e.getMessage());
        }finally{
            method.releaseConnection();
        }
        return response;
	}

	 /**
     * 解析http请求的response
     * @param method
     * @return 请求结果
     * @throws IOException
     */
    public String parserResponse(PostMethod method) throws IOException{
        StringBuffer contentBuffer = new StringBuffer();
        InputStream in = method.getResponseBodyAsStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, method.getResponseCharSet()));
        String inputLine = null;
        while((inputLine = reader.readLine()) != null)
        {
            contentBuffer.append(inputLine);
            contentBuffer.append("/n");
        }
        //去掉结尾的换行符
        contentBuffer.delete(contentBuffer.length() - 2, contentBuffer.length());
        in.close();
        return contentBuffer.toString();
    }

}
