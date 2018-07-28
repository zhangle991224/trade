package com.dm.trade.api;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.Code;
import com.dm.trade.api.dto.request.customer.ModifyPwdForm;
import com.dm.trade.api.dto.request.customer.RegisterForm;
import com.dm.trade.api.dto.request.customer.RestPwdForm;
import com.dm.trade.common.SMSUtils;
import com.dm.trade.common.annotation.NeedLogin;
import com.dm.trade.common.config.Constant;
import com.dm.trade.common.exception.ApiRequestException;
import com.dm.trade.common.http.impl.UserApi;
import com.dm.trade.common.utils.CodeGenner;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.common.utils.JSONUtils;
import com.dm.trade.common.utils.MD5Utils;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.service.CustomerService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhongchao
 * @title APICustomerController.java
 * @Date 2018-03-25
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/customer")
public class APICustomerController {

    private static final Logger logger = LoggerFactory.getLogger(APICustomerController.class);

    @Resource
    private CustomerService customerService;
    @Autowired
    private UserApi userApi;

    /**
     * 微信用户授权，根据openid进行登陆（针对注册或者激活过的用户）
     *
     * @param params
     */
    @RequestMapping("/auth")
    public R auth(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String resp = null;
        Map<String, Object> resultMap = new HashMap<>();
        resp = this.userApi.getAuth2Token(params.get("code").toString());
        logger.info("获取微信用户授权信息=======》" + resp);
        Map<String, Object> map = JSONUtils.jsonToMap(resp);
        String openId = (String) map.get("openid");//获取用户openid
        CustomerDO customer = loginByOpenid(openId, request);
        resultMap.put("sessionId", request.getSession().getId());
        resultMap.put("openId", openId);
        //返回用户状态
        if (customer != null) {
            resultMap.put("customerStatus", customer.getStatus());
            resultMap.put("customer",customer);
        }else {
            //未注册
            resultMap.put("customerStatus", -1);
        }
        return APIResult.isOk(resultMap);
    }

    /**
     * 根据用户名密码进行登陆（针对系统已分配的用户，相当于用户激活，绑定微信openid）
     *
     * @param phone
     * @param password
     * @param openId
     * @param request
     * @return
     */
    @RequestMapping("/loginByUser")
    public R loginByUser(String phone, String password, String openId, HttpServletRequest request) {
        CustomerDO customer = login(phone, password, openId, request);
        return APIResult.isOk(customer);
    }

    /**
     * 用户注册
     *
     * @param openId
     * @return
     */
    @RequestMapping("/register")
    private R register(String openId) {
        try {
            CustomerDO user = new CustomerDO();
            if (openId != null) {
                user.setOpenId(openId);
                user.setCreateTime(new Timestamp(System.currentTimeMillis()));
                customerService.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }


    @RequestMapping("/regcode")
    public R regcode(String phone, HttpServletRequest request) {
        if (phone == null || phone.length() != 11) {
            throw new ApiRequestException("手机号错误", 400);
        }
        Code code = (Code) request.getSession().getAttribute(Constant.V_CODE);
        if (code != null && System.currentTimeMillis() - code.getTime() < 1000 * 60) {
            throw new ApiRequestException("操作太过频繁,请稍候再试");
        }
        phoneValidate(phone);
        String randNum = CodeGenner.getRandNum(6);
        request.getSession().setAttribute(Constant.V_CODE, new Code(randNum, System.currentTimeMillis()));
        logger.info("phone:{},验证码:{}", phone, randNum);
        SMSUtils.sendSms(phone, randNum);
        return R.ok();
    }

//    @PostMapping("/login")
//    public APIResult newLogin(String phone, String password, HttpServletRequest request) {
//        CustomerDO customer = login(phone, password, request);
//        return APIResult.isOk(customer);
//    }

    /**
     * @param form
     * @return
     */
    @PostMapping("/registerNew")
    public R register(@Valid RegisterForm form, HttpServletRequest request) {
        String phone = form.getPhone();
        phoneValidate(phone);
//        Code code = (Code) request.getSession().getAttribute(Constant.V_CODE);
//        if (!form.getCode().equals(code.getCode())) {
//            throw new ApiRequestException("验证码错误", 400);
//        }
        customerService.register(form);
        loginByOpenid(form.getOpenId(), request);
//        request.getSession().removeAttribute(Constant.V_CODE);
        return R.ok();
    }

    @RequestMapping("/info")
    @NeedLogin
    public APIResult getSysstaffInfo() {
        CustomerDO customerDO = HttpContextUtils.getCustomerInfo();
        return APIResult.isOk(customerDO);
    }

    /**
     * @param form
     * @return
     */
    @PostMapping("/rest")
    public R resetPwd(@Valid RestPwdForm form, HttpServletRequest request) {
        String phone = form.getPhone();
        phoneValidate(phone);
        Code code = (Code) request.getSession().getAttribute(Constant.V_CODE);
        if (!form.getCode().equals(code.getCode())) {
            throw new ApiRequestException("验证码错误", 400);
        }
        customerService.sysAPIRestPwd(form);
        request.getSession().removeAttribute(Constant.V_CODE);
        return R.ok();
    }

    /**
     * @param form
     * @return
     */
    @PostMapping("/modifypwd")
    public R modifyPwd(@Valid ModifyPwdForm form, HttpServletRequest request) {
        String phone = form.getPhone();
        phoneValidate(phone);
        customerService.sysAPIModifyPwd(form);
        return R.ok();
    }

    private CustomerDO login(String phone, String password, String openId, HttpServletRequest request) {
        Assert.notNull(phone, "账户不能为空");
        Assert.notNull(password, "密码不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("mobilephone", phone);
        List<CustomerDO> list = customerService.list(map);
        if (CollectionUtils.isNotEmpty(list)) {
            CustomerDO customer = list.get(0);
            password = MD5Utils.encrypt(phone, password);
            if (customer.getPassword().equals(password)) {
                //保存用户openid
                customer.setOpenId(openId);
                customerService.update(customer);
                HttpContextUtils.setCustomerInfo(request, customer);
                return customer;
            }
        }
        throw new ApiRequestException("用户名或密码错误.", 400);
    }

    /**
     * 根据openid登陆获取用户信息
     *
     * @param openid
     * @param request
     * @return
     */
    private CustomerDO loginByOpenid(String openid, HttpServletRequest request) {
        Assert.notNull(openid, "openid不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("openId", openid);
        List<CustomerDO> list = customerService.list(map);
        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            CustomerDO customer = list.get(0);
            HttpContextUtils.setCustomerInfo(request, customer);
            return customer;
        }
        return null;
    }

    private static void phoneValidate(String phone) {
        Pattern pCM = Pattern.compile(Constant.CM_NUM);
        Pattern pCT = Pattern.compile(Constant.CT_NUM);
        Pattern pCU = Pattern.compile(Constant.CU_NUM);
        Pattern common = Pattern.compile(Constant.COMMON);
        Matcher mCM = pCM.matcher(phone);
        Matcher mCT = pCT.matcher(phone);
        Matcher mCU = pCU.matcher(phone);
        Matcher com = common.matcher(phone);
        if (!mCM.matches() && !mCT.matches() && !mCU.matches() && !com.matches()) {
            throw new ApiRequestException("手机号错误", 400);
        }
    }
}

