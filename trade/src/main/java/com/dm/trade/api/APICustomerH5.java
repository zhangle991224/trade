package com.dm.trade.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dm.trade.api.dto.APIResult;
import com.dm.trade.api.dto.Pager;
import com.dm.trade.api.dto.request.CommonPage;
import com.dm.trade.api.dto.request.goods.GoodsListQueryOption;
import com.dm.trade.api.dto.response.goods.GoodsListResult;
import com.dm.trade.api.dto.response.order.OrderResult;
import com.dm.trade.common.annotation.Log;
import com.dm.trade.common.domain.Tree;
import com.dm.trade.common.utils.HttpContextUtils;
import com.dm.trade.customer.domain.CustomerAddressDO;
import com.dm.trade.customer.domain.CustomerDO;
import com.dm.trade.customer.service.CustomerAddressService;
import com.dm.trade.goods.service.GoodsCategoryService;
import com.dm.trade.goods.service.GoodsService;
import com.dm.trade.order.service.OrderDetailService;
import com.dm.trade.order.service.OrderService;
import com.dm.trade.payment.miniWechatPay.TradeWxPay;
import com.dm.trade.system.domain.MenuDO;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/api/CustnmerH5")
public class APICustomerH5 {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
	private CustomerAddressService customerAddressService;
    
    @Autowired
    private OrderDetailService orderDetailService;

    @Resource
    private TradeWxPay tradeWxPay;
	
	    @Resource
	    private GoodsService goodsService;

	    @Resource
	    private GoodsCategoryService categoryService;
         
	    @Log("请求访问主页")
	    @GetMapping({"Class_h5"})
	    public String Class_h5(Model model) throws ServletException, IOException {
	    	
			return "class";
		}
	    @Log("请求访问主页")
	    @GetMapping({"cart_h5"})//购物车
	    public String cart_h5(Model model) throws ServletException, IOException {
	    	
			return "H5/cart";
		}
	    @Log("请求访问主页")
	    @GetMapping({"home_h5"})//我的主页
	    public String home_h5(Model model) throws ServletException, IOException {
	    	
			return "H5/home";
		}
	    
	    @Log("收货地址主页")
	    @GetMapping({"shouhuodizhi_h5"})//收货地址
	    public String shouhuodizhi_h5(Model model) throws ServletException, IOException {
	    	
			return "H5/shouhuodizhi";
		}
	    
	    @Log("请求订单列表")
	    @ResponseBody
	    @RequestMapping("shouhuodizhi_h5_list")//我的收货列表
	    public APIResult getAddressList(CommonPage page) {
	        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
	        long xx=26;
	       /* return APIResult.isOk(orderService.getOrderList(page, customerInfo.getId()));*/
	        CustomerAddressDO customerAddress = customerAddressService.get(xx);
	       return APIResult.isOk(customerAddress);
	    }
	    @Log("请求访问主页")
	    @GetMapping({"wodedingdan_h5"})//我的订单页面
	    public String wodedingdan_h5(Model model) throws ServletException, IOException {
	    	
			return "H5/wodedingdan";
		}
	    @Log("请求订单列表")
	    @ResponseBody
	    @RequestMapping("wodedingdan_h5_list")
	   // @RequestMapping({"wodedingdan_h5_list"})//我的订单列表
	    public APIResult getOrderList(CommonPage page) {
	        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
	        long xx=26;
	       /* return APIResult.isOk(orderService.getOrderList(page, customerInfo.getId()));*/
	        Pager<OrderResult> aa=orderService.getOrderList(page, xx);
	       
	       return APIResult.isOk(aa);
	    }
	    @Log("请求订单列表")
	    @ResponseBody
	    @RequestMapping("wodedingdan_h5_Detail")//我的订单列表详情
	    public APIResult getOrderDetail(Long orderId) {
	    	
	        return APIResult.isOk(orderDetailService.getOrderDetails(orderId));
	    }
	    
	    @Log("请求访问主页")
	    @GetMapping({"wodezhiliao_h5"})//我的订单
	    public String wodezhiliao_h5(Model model) throws ServletException, IOException {
			return "H5/wodezhiliao";
		}
	    
	    
	    /**
	     * 根据条件获取商品列表
	     *
	     * @param queryOption
	     * @return
	     */
//	    @NeedLogin
	    @RequestMapping("list")
	    public APIResult getGoodsList(GoodsListQueryOption queryOption) {
	        CustomerDO customerInfo = HttpContextUtils.getCustomerInfo();
	        Pager<GoodsListResult> result = goodsService.getAPIGoodsList(queryOption, customerInfo);
	        return APIResult.isOk(result);
	    }

	    /**
	     * 获取所有商品类目
	     *
	     * @return
	     */
	    @RequestMapping("categoryList")
	    public APIResult getCategoryLists(Integer categoryId) {
	        if (categoryId == null) {
	            categoryId = 0;
	        }
	        Map<String, Object> params = Maps.newHashMap();
	        params.put("pid", categoryId);
	        return APIResult.isOk(categoryService.list(params));
	    }

}
