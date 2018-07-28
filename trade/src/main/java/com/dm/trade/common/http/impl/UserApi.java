package com.dm.trade.common.http.impl;

import com.dm.trade.common.http.ApiCall;
import com.dm.trade.common.http.HttpHelper;
import com.dm.trade.common.wx.ConfigUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

/**
 * description:
 * Created by zhangle on 2016/12/7.
 * qq:475166369
 */
@Component("userApi")
public class UserApi extends ApiCall {
    /**
     * 获取微信授权信息
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     * @throws RuntimeException
     */
    public String getAuth2Token(String code) throws RuntimeException {
        String url = ConfigUtil.codeurl+
                "?appid="+ ConfigUtil.appid+
                "&secret="+ConfigUtil.secret+
                "&js_code="+code+
                "&grant_type="+ConfigUtil.grant_type;
        String result = "";
        try {
            result = HttpHelper.get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
