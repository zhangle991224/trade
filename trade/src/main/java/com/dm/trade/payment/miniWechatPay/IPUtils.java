package com.dm.trade.payment.miniWechatPay;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {
	/***
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
	 *
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {

		String ip = request.getHeader("X-Real-IP");
		String fromSource = "X-Real-IP";
		if (!isIP(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			fromSource = "X-Forwarded-For";
		}
		if (!isIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			fromSource = "Proxy-Client-IP";
		}
		if (!isIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			fromSource = "WL-Proxy-Client-IP";
		}
		if (!isIP(ip)) {
			ip = request.getRemoteAddr();
			fromSource = "request.getRemoteAddr";
		}
		return ip;
	}

	/**
	 * 判断是否是有效IP地址
	 * @param addr
	 * @return
	 */
	public static boolean isIP(String addr) {
		if (addr == null || "".equals(addr)) {
			return false;
		}
		if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(addr);

		boolean ipAddress = mat.find();

		return ipAddress;
	}
}
