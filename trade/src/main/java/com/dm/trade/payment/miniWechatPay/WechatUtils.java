package com.dm.trade.payment.miniWechatPay;


import com.dm.trade.common.utils.StringUtils;
import com.dm.trade.common.wx.ConfigUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class WechatUtils {

	/**
	 *
	* @Title: getSign
	* @Description: 生成微信签名
	* @param @param appid
	* @param @param mchId
	* @param @param deviceInfo
	* @param @param body
	* @param @param nonceStr
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String generateSign(Map<String, Object> map) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if(entry.getValue()!=null&& !StringUtils.isEmpty(entry.getValue().toString())){
				list.add(entry.getKey()+"=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + ConfigUtil.MCH_KEY;
		result = MD5.MD5Encode(result).toUpperCase();
		return result;
	}


//	public static boolean checkSign(String appid, String mchId, String deviceInfo, String body, String nonceStr,String sign){
//		String newSign = WechatUtils.generateSign(appid, mchId, deviceInfo, body, nonceStr);
//		return newSign.equals(sign);
//	}

	/**
	 *
	* @Title: generateOrderNo
	* @Description: 生成订单ID
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String generateOrderNo(){
		String random = ""+(int)(Math.random()*100000000);
		while (random.length()<8) {
			random = "0" + random;
		}
		SimpleDateFormat sdFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdFormat.format(new Date())+random;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			String orderNo = generateOrderNo();
			System.out.println(orderNo);
		}
//		System.out.println(orderNo.length());
	}
}
