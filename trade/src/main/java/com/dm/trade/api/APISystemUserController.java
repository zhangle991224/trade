package com.dm.trade.api;

import com.dm.trade.common.utils.HttpHelper;
import com.dm.trade.common.utils.JacksonHelper;
import com.dm.trade.common.utils.RequestHelper;
import com.dm.trade.common.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author zhangle
 * @title APiSystemUserController.java
 * @Date 2018-06-29
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/sysuser")
public class APISystemUserController {
    private static final Logger logger = LoggerFactory.getLogger(APISystemUserController.class);

    private static final String APPID = "";
    private static final String APP_SECRET = "";

    /**
     * 首次授权获取code，然后跳转获取openid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login1", method = {RequestMethod.GET},
            produces = "text/html;charset=UTF-8",
            consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public String setup1(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 组装跳转微信地址
         */
        String wxURL = "";
        try {
            wxURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect"
                    .replace("APPID", APPID)
                    .replace("REDIRECT_URI", URLEncoder.encode((RequestHelper.fullServletPath(request) + "/api/sysuser/login2.cgi"), "UTF-8"))
                    .replace("SCOPE", "snsapi_base")
                    .replace("STATE", RequestHelper.sessionId(request).substring(0, 15));
        } catch (Exception e) {
            logger.error("组装wxURL1异常", e);
        }
        /**
         * 然后跳转呗
         */
        try {
            logger.debug("[{}] 跳转微信授权页面 [{}]", RequestHelper.sessionId(request), wxURL);
            response.sendRedirect(wxURL);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("跳转失败2", e);
            response.setStatus(500);
            return null;
        }
    }

    /**
     * 获取用户openid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login2", method = {RequestMethod.GET},
            produces = "text/html;charset=UTF-8",
            consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public ModelAndView setup2(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 再判断是否合法的微信返回
         */
        String code = request.getParameter("code");
        if (!StringHelper.checkString(code)) {
            return new ModelAndView("redirect:" + "/oauth2/login1.cgi");
        }

        /**
         * 最后和微信换openid
         */
        String wxURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
                .replace("APPID", APPID)
                .replace("SECRET", APP_SECRET)
                .replace("CODE", code);
        try {
            String json = HttpHelper.get(wxURL);
            logger.info("交换Code返回 [{}] ~> {}", RequestHelper.sessionId(request), json);
            // 解析openid
            String openid = JacksonHelper.getMapper().readTree(json).get("openid").asText();

            return null;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                logger.error(RequestHelper.sessionId(request) + " [堆栈日志DEBUG]code~>" + code + " 交换openid NPE异常了");
                logger.debug(RequestHelper.sessionId(request) + " code~>" + code + " 交换openid异常了", e);
            } else {
                logger.error(RequestHelper.sessionId(request) + " code " + code + " 交换openid异常了", e);
            }
            response.setStatus(500);
            return new ModelAndView("redirect:/");
        }
    }
}
