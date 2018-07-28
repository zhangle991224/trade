package com.dm.trade.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Roy.Lust@gmail.com
 * 9/17/16
 * 9:22 PM
 */
public class RequestHelper {
    private static final Logger logger = LoggerFactory.getLogger(RequestHelper.class);

    /**
     * 获得sessionID
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String sessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    /**
     * 获取请求的全部路径，包含参数
     *
     * @param request HttpServletRequest
     * @return 请求全路径，包含参数
     */
    public static String fullURI(HttpServletRequest request) {
        String uri = request.getServletPath();
        String queryString = request.getQueryString();

        /**
         * 处理没有请求参数的情况
         */
        if (queryString == null) {
            queryString = "";
        } else {
            queryString = "?" + queryString;
        }
        return uri + queryString;
    }

    /**
     * 获取 域名+端口+contextPath
     * 省略 80 443
     *
     * @param request HttpServletRequest
     * @return domain + contextPath
     */
    public static String fullServletPath(HttpServletRequest request) {
        String schema = request.getScheme();
        int port = request.getServerPort();
        String domain = request.getServerName();
        String contextPath = request.getContextPath();
        String fullPath = "";
        if ("http".equalsIgnoreCase(schema) && (port == 80)) {

            fullPath = schema + "://" + domain + contextPath;
            logger.debug("fullServletPath 普通返回 {}", fullPath);
            return fullPath;
        } else if ("https".equalsIgnoreCase(schema) && (port == 443)) {
            fullPath = schema + "://" + domain + contextPath;
            logger.debug("fullServletPath 普通返回 {}", fullPath);
            return fullPath;
        } else {
            fullPath = schema + "://" + domain + ":" + port + contextPath;
            logger.info("fullServletPath 返回有端口地址 {}", fullPath);
            return fullPath;
        }
    }

    /**
     * 重定向到错误页面
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void redirectError(HttpServletRequest request, HttpServletResponse response, int status, String uri) {
        logger.info("[{}]重定向错误页面 [{}] [{}]", sessionId(request), status, uri);
        response.setStatus(status);
        if (uri != null) {
            try {
                response.sendRedirect(uri);
            } catch (IOException e) {
                logger.error("重定向错误页面异常", e.getMessage());
            }
        }
    }
}
