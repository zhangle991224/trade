package com.dm.trade.common.utils;

import com.dm.trade.common.config.Constant;
import com.dm.trade.customer.domain.CustomerDO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static void setCustomerInfo(HttpServletRequest request, CustomerDO customerDO) {
        request.getSession(true).setAttribute(Constant.USER, customerDO);
    }

    public static CustomerDO getCustomerInfo() {
        return (CustomerDO) getHttpServletRequest().getSession().getAttribute(Constant.USER);
    }
}
