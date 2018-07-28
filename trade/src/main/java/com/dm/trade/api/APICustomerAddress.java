package com.dm.trade.api;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.request.customerAddress.CustomerAddressForm;
import com.dm.trade.common.annotation.NeedLogin;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.common.utils.R;
import com.dm.trade.customer.domain.CustomerAddressDO;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.service.CustomerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangle
 * @title APICustomerAddress.java
 * @Date 2018-05-04
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/customer/address")
public class APICustomerAddress {
    private static final Logger logger = LoggerFactory.getLogger(APICustomerAddress.class);

    @Resource
    private CustomerAddressService customerAddressService;

    /**
     * 新增收货地址
     * @param customerAddressForm
     * @return
     */
    @NeedLogin
    @RequestMapping("add")
    public R addAddress(@Valid CustomerAddressForm customerAddressForm){
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        customerAddressService.addAddress(customerAddressForm,customerInfo.getId());
        return R.ok();
    }

    /**
     * 修改收货地址
     * @param customerAddressForm
     * @return
     */
    @NeedLogin
    @RequestMapping("update")
    public R updateAddress(@Valid CustomerAddressForm customerAddressForm){
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        customerAddressService.updateAddress(customerAddressForm,customerInfo.getId());
        return R.ok();
    }

    /**
     * 获取收货地址列表
     * @return
     */
    @NeedLogin
    @RequestMapping("list")
    public R addressList(){
        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("customerId",customerInfo.getId());
        List<CustomerAddressDO> list = customerAddressService.list(map);
        return APIResult.isOk(list);
    }

    /**
     * 获取收货地址详情
     * @return
     */
    @NeedLogin
    @RequestMapping("detail")
    public R addressDetail(Long id){
        CustomerAddressDO customerAddressDO = customerAddressService.get(id);
        return APIResult.isOk(customerAddressDO);
    }
}
