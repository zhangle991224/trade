package com.dm.trade.customer.service;

import com.dm.trade.api.dto.request.customer.ModifyPwdForm;
import com.dm.trade.api.dto.request.customer.RegisterForm;
import com.dm.trade.api.dto.request.customer.RestPwdForm;
import com.dm.trade.customer.domain.CustomerDO;

import java.util.List;
import java.util.Map;

/**
 * 用户信息 主表
 *
 * @author zhongchao
 * @email zhong_ch@foxmail.com
 * @date 2018-03-25 11:29:15
 */
public interface CustomerService {

    CustomerDO get(Integer id);

    List<CustomerDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    Long save(CustomerDO customer);

    int update(CustomerDO customer);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    void register(RegisterForm registerForm);

    /**
     * 重置密码
     *
     * @param form
     */
    void sysAPIRestPwd(RestPwdForm form);

    /**
     * 修改密码
     *
     * @param form
     */
    void sysAPIModifyPwd(ModifyPwdForm form);

//    /**
//     * 根据 微信的code 获取用户信息
//     * <p>1. 获取</p>
//     * @param code
//     */
//    void getCustomerByWxCode(String code);
}
