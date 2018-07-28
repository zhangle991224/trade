package com.dm.trade.customer.service.impl;

import com.dm.trade.api.dto.request.customer.ModifyPwdForm;
import com.dm.trade.api.dto.request.customer.RegisterForm;
import com.dm.trade.api.dto.request.customer.RestPwdForm;
import com.dm.trade.common.exception.ApiRequestException;
import com.dm.trade.common.utils.BeanUtils;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.common.utils.MD5Utils;
import com.dm.trade.common.utils.StringUtils;
import com.dm.trade.customer.dao.CustomerAddressDao;
import com.dm.trade.customer.dao.CustomerDao;
import com.dm.trade.customer.dao.CustomerDetailDao;
import com.dm.trade.customer.domain.CustomerAddressDO;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.domain.CustomerDetailDO;
import com.dm.trade.customer.service.CustomerService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Resource
    private CustomerDetailDao customerDetailDao;

    @Resource
    private CustomerAddressDao customerAddressDao;

    @Override
    public CustomerDO get(Integer id) {
        return customerDao.get(id);
    }

    @Override
    public List<CustomerDO> list(Map<String, Object> map) {
        return customerDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return customerDao.count(map);
    }

    @Override
    public Long save(CustomerDO customer) {
        return customerDao.save(customer);
    }

    @Override
    public int update(CustomerDO customer) {
        return customerDao.update(customer);
    }

    @Override
    public int remove(Integer id) {
        return customerDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return customerDao.batchRemove(ids);
    }

    @Transactional
    @Override
    public void register(RegisterForm registerForm) {
        CustomerDO customerDO = BeanUtils.copyProperties(registerForm, CustomerDO.class);
        if(StringUtils.isNotEmpty(registerForm.getPassword())){
            customerDO.setPassword(MD5Utils.encrypt(registerForm.getPassword()));
        }
        customerDO.setRealName(registerForm.getLinkName());
        customerDO.setOpenId(registerForm.getOpenId());
        customerDao.save(customerDO);
        CustomerDetailDO detailDO = BeanUtils.copyProperties(registerForm, CustomerDetailDO.class);
        detailDO.setCustomerId(customerDO.getId());
        detailDO.setLinkPhone(registerForm.getPhone());
        detailDO.setName(registerForm.getComName());
        detailDO.setFromCustomer(registerForm.getFromCustomer());
        customerDetailDao.save(detailDO);
        CustomerAddressDO addressDO = BeanUtils.copyProperties(registerForm, CustomerAddressDO.class);
        addressDO.setCustomerId(customerDO.getId());
        addressDO.setAddress(registerForm.getApplyAddress());
        addressDO.setLinkMan(registerForm.getLinkName());
        addressDO.setLinkPhone(registerForm.getPhone());
        customerAddressDao.save(addressDO);
    }

    @Override
    public void sysAPIRestPwd(RestPwdForm form) {
        List<CustomerDO> list = getSysstaffDOByPhone(form.getPhone());
        if (CollectionUtils.isEmpty(list)) {
            throw new ApiRequestException("该手机号尚未注册!", 400);
        }
        CustomerDO sysstaffDO = list.get(0);
        sysstaffDO.setPassword(MD5Utils.encrypt(form.getPhone(), form.getNewPwd()));
        customerDao.update(sysstaffDO);
    }

    @Override
    public void sysAPIModifyPwd(ModifyPwdForm form) {
        List<CustomerDO> list = getSysstaffDOByPhone(form.getPhone());
        if (CollectionUtils.isEmpty(list)) {
            throw new ApiRequestException("该手机号尚未注册!", 400);
        }
        CustomerDO customer = list.get(0);
        String oldPwdEncrypt = MD5Utils.encrypt(form.getPhone(), form.getOldPwd());
        if (!oldPwdEncrypt.equals(form.getOldPwd())) {
            throw new ApiRequestException("原密码不匹配!", 400);
        }
        String newPwdEncrypt = MD5Utils.encrypt(form.getPhone(), form.getNewPwd());
        if (newPwdEncrypt.equals(oldPwdEncrypt)) {
            throw new ApiRequestException("新密码不能和老密码相同!", 400);
        }
        customer.setPassword(newPwdEncrypt);
        customerDao.update(customer);
    }


    private List<CustomerDO> getSysstaffDOByPhone(String phone) {
        // 根据手机号获取信息
        Map<String, Object> map = Maps.newHashMap();
        map.put("phone", phone);
        return customerDao.list(map);
    }

}
