package com.dm.trade.common.aspect;

import com.alibaba.fastjson.JSON;
import com.dm.trade.common.annotation.NeedLogin;
import com.dm.trade.common.exception.ApiRequestException;
import com.dm.trade.common.exception.ApiRequestUnknownException;
import com.dm.trade.common.exception.AuthException;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.service.CustomerService;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author zhongchao
 * @Date 2017-11-15
 * @since v1.0.0
 */
@Aspect
@Component
public class ApiExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionAspect.class);

    @Pointcut("execution(* com.dm.trade.api..*(..))")

    public void loginExcudeService() {
    }

    @Resource
    private CustomerService customerService;

    @Around("loginExcudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        List<Object> params = Lists.newArrayList();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        try {
            Annotation[] annotations = signature.getMethod().getAnnotations();
            boolean needLogin = false;
            if (annotations != null) {
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().equals(NeedLogin.class)) {
                        needLogin = true;
                    }
                }
            }
            if (needLogin) {
                validateLogin();
            }
            Object[] args = pjp.getArgs();
            for (Object o : args) {
                if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    continue;
                }
                params.add(o);
            }
            Object result = pjp.proceed();
            logger.info("controller:" + pjp.getTarget().getClass().getName() + "." + signature.getMethod().getName() + " params:{},result:{}", JSON.toJSONString(params), JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("controller:" + pjp.getTarget().getClass().getName() + "." + signature.getMethod().getName() + " params:{}", JSON.toJSONString(params));
            // 参数错误或者 权限错误 apiRequestException 直接抛出
            if (e instanceof ApiRequestException || e instanceof IllegalArgumentException || e instanceof AuthException) {
                throw e;
            }
            throw new ApiRequestUnknownException("服务器正忙请稍候再试.", e);
        }

    }

    private void validateLogin() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        CustomerDO userInfo = HttpContextUtils.getCustomerInfo();
        if (userInfo == null) {
            throw new AuthException("请先登录!");
        }
    }

}