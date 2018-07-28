package com.dm.trade.common.exception;

import com.alibaba.fastjson.JSON;
import com.dm.trade.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 */
@ControllerAdvice
public class ComExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(ApiRequestException.class)
    public R handleApiException(ApiRequestException e, HttpServletRequest req) {
        logger.warn("异常请求request url:{}", req.getRequestURI(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    /**
     * 自定义异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public R handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest req) {
        logger.warn("异常请求request url:{}", req.getRequestURI(), e);
        R r = new R();
        r.put("code", 400);
        r.put("msg", e.getMessage());
        return r;
    }

    /**
     * 自定义异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(ApiRequestUnknownException.class)
    public R handlerApiRequestUnknownException(ApiRequestUnknownException e, HttpServletRequest req) {
        logger.warn("异常请求request url:{}", req.getRequestURI(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }


    /**
     * 自定义异常
     */
    @ExceptionHandler(BDException.class)
    public R handleBDException(BDException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public R noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        return R.error("没找找到页面");
    }

    @ExceptionHandler(AuthorizationException.class)
    public String handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return "error/403";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.warn("异常请求request url:{}", request.getRequestURI(), e);
        if (request.getRequestURI().startsWith("/api")) {
            try {
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().print(JSON.toJSONString(R.error(500, "服务异常,请稍候再试...")));
                response.getWriter().close();
                return null;
            } catch (Exception e2) {
                logger.error("异常处理返回 json 出现错误->{}" + e2.getMessage());
            }
        }
        return new ModelAndView("/error/500");

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public R resolveBindException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.warn("异常请求request url:{}", request.getRequestURI(), e);
        R r = new R();
        r.put("code", 400);
        r.put("msg", ((BindException) e).getAllErrors().get(0).getDefaultMessage());
        return r;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R resolveHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.warn("异常请求request url:{}", request.getRequestURI(), e);
        R r = new R();
        r.put("code", 400);
        r.put("msg", "错误的请求");
        return r;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    @ExceptionHandler(AuthException.class)
    public R resolveAuthException(HttpServletRequest request, HttpServletResponse response, AuthException e) {
        logger.warn("异常请求request url:{}", request.getRequestURI(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }
}
