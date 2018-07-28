package com.dm.trade.payment.miniWechatPay;

import com.dm.trade.common.utils.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * @author 蔡政滦
 * @version 2015年7月23日
 */
public class XmlUtils {

	/**
	 * 根据Map组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，
	 * 以及包含元素为上述支持数据类型的Map
	 *
	 * @param vo
	 * @param rootElement
	 * @return
	 * @author 蔡政滦 modify by 2015-6-5
	 */
	public static String map2xmlBody(Map<String, Object> vo, String rootElement) {
		Document doc = DocumentHelper.createDocument();
		Element body = DocumentHelper.createElement(rootElement);
		doc.add(body);
		_buildMap2xmlBody(body, vo);
		return doc.asXML();
	}

	private static void _buildMap2xmlBody(Element body, Map<String, Object> vo) {
		if (vo != null) {
			Iterator<String> it = vo.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!StringUtils.isEmpty(key)) {
					Object obj = vo.get(key);
					Element element = DocumentHelper.createElement(key);
					if (obj != null) {
						if (obj instanceof String) {
							element.setText((String) obj);
						} else {
							if (obj instanceof Character || obj instanceof Boolean
									|| obj instanceof Number || obj instanceof java.math.BigInteger
									|| obj instanceof java.math.BigDecimal) {
								Attribute attr = DocumentHelper.createAttribute(element, "type",
										obj.getClass().getCanonicalName());
								element.add(attr);
								element.setText(String.valueOf(obj));
							} else if (obj instanceof Map) {
								Attribute attr = DocumentHelper.createAttribute(element, "type",
										Map.class.getCanonicalName());
								element.add(attr);
								_buildMap2xmlBody(element, (Map<String, Object>) obj);
							} else {
							}
						}
					}
					body.add(element);
				}
			}
		}
	}

	/**
	 * 根据xml消息体转化为Map
	 *
	 * @param xml
	 * @param rootElement
	 * @return
	 * @throws DocumentException
	 * @author 蔡政滦 modify by 2015-6-5
	 */
	public static Map<String, Object> xmlBody2map(String xml, String rootElement) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element body = (Element) doc.selectSingleNode(rootElement);
		Map<String, Object> vo = _buildXmlBody2map(body);
		return vo;
	}

	private static Map<String, Object> _buildXmlBody2map(Element body) {
		Map<String, Object> vo = new HashMap<String, Object>();
		if (body != null) {
			List<Element> elements = body.elements();
			for (Element element : elements) {
				String key = element.getName();
				if (!StringUtils.isEmpty(key)) {
					String type = element.attributeValue("type", "java.lang.String");
					String text = element.getText().trim();
					Object value = null;
					if (String.class.getCanonicalName().equals(type)) {
						value = text;
					} else if (Character.class.getCanonicalName().equals(type)) {
						value = new Character(text.charAt(0));
					} else if (Boolean.class.getCanonicalName().equals(type)) {
						value = new Boolean(text);
					} else if (Short.class.getCanonicalName().equals(type)) {
						value = Short.parseShort(text);
					} else if (Integer.class.getCanonicalName().equals(type)) {
						value = Integer.parseInt(text);
					} else if (Long.class.getCanonicalName().equals(type)) {
						value = Long.parseLong(text);
					} else if (Float.class.getCanonicalName().equals(type)) {
						value = Float.parseFloat(text);
					} else if (Double.class.getCanonicalName().equals(type)) {
						value = Double.parseDouble(text);
					} else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
						value = new java.math.BigInteger(text);
					} else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
						value = new java.math.BigDecimal(text);
					} else if (Map.class.getCanonicalName().equals(type)) {
						value = _buildXmlBody2map(element);
					} else {
					}
					vo.put(key, value);
				}
			}
		}
		return vo;
	}

	public static Map<String, Object> Dom2Map(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Map<String, Object> map = new HashMap<String, Object>();
		if(doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if(list.size() > 0){
				map.put(e.getName(), Dom2Map(e));
			}else
				map.put(e.getName(), e.getText());
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	public static Map Dom2Map(Element e){
		Map map = new HashMap();
		List list = e.elements();
		if(list.size() > 0){
			for (int i = 0;i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if(iter.elements().size() > 0){
					Map m = Dom2Map(iter);
					if(map.get(iter.getName()) != null){
						Object obj = map.get(iter.getName());
						if(!obj.getClass().getName().equals("java.util.ArrayList")){
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if(obj.getClass().getName().equals("java.util.ArrayList")){
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					}else
						map.put(iter.getName(), m);
				}
				else{
					if(map.get(iter.getName()) != null){
						Object obj = map.get(iter.getName());
						if(!obj.getClass().getName().equals("java.util.ArrayList")){
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if(obj.getClass().getName().equals("java.util.ArrayList")){
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					}else
						map.put(iter.getName(), iter.getText());
				}
			}
		}else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static List<Map<String,Object>> yuzhongQueue_Helper(String r403) {
		List<Map<String,Object>> lineUpDetails = new ArrayList<Map<String,Object>>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(r403);

			Element rootEltdoctor = doc.getRootElement(); // 获取根节点
			Iterator<?> iters1 = rootEltdoctor.elementIterator("grid");
			while (iters1.hasNext()) {

				Map<String,Object> lineUpDetail = new HashMap<>();
				Element itemEle = (Element) iters1.next();
				// 拿到body下的子节点DeptList下的字节点DeptName的值
				String patient_id = itemEle.elementTextTrim("patient_id");
				String patient_name = itemEle.elementTextTrim("patient_name");
				String sex = itemEle.elementTextTrim("sex");
				String dept_name = itemEle.elementTextTrim("area_name");
				String group_name = itemEle.elementTextTrim("group_name");
				String reg_datetime = itemEle.elementTextTrim("reg_datetime");
				String queue_no1 = itemEle.elementTextTrim("queue_no");
				Integer queue_no = Integer.valueOf(queue_no1);
				String status = itemEle.elementTextTrim("status");
				String wait_count1 = itemEle.elementTextTrim("wait_count");
				Integer wait_count = Integer.valueOf(wait_count1);
				String call_doctor = itemEle.elementTextTrim("call_doctor");
				String area_name = itemEle.elementTextTrim("area_name");
				String other_info = itemEle.elementTextTrim("other_info");
				lineUpDetail.put("patient_id",patient_id);
				lineUpDetail.put("patient_name",patient_name);
				lineUpDetail.put("sex",sex);
				lineUpDetail.put("dept_name",dept_name);
				lineUpDetail.put("group_name",group_name);
				lineUpDetail.put("reg_datetime",reg_datetime);
				lineUpDetail.put("queue_no",queue_no);
				lineUpDetail.put("status",status);
				lineUpDetail.put("wait_count",wait_count);
				lineUpDetail.put("call_doctor",call_doctor);
				lineUpDetail.put("area_name",area_name);
				lineUpDetail.put("other_info",other_info);
				lineUpDetail.put("type","渝中本部");
				lineUpDetail.put("flag",false);
				lineUpDetail.put("call_doctor",call_doctor);
				lineUpDetails.add(lineUpDetail);
			}
		} catch (Exception e) {

		}
		return lineUpDetails;
	}



	public static void main(String[] args) throws DocumentException {
		String xml = "<xml>"
					+"   <appid>wx2421b1c4370ec43b</appid>"
					+"   <attach>支付测试</attach>"
					+"   <body>JSAPI支付测试</body>"
					+"   <mch_id>10000100</mch_id>"
					+"   <detail><![CDATA[asdfsadfs]]></detail>"
					+"   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>"
					+"   <notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>"
					+"   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>"
					+"   <out_trade_no>1415659990</out_trade_no>"
					+"   <spbill_create_ip>14.23.150.211</spbill_create_ip>"
					+"   <total_fee>1</total_fee>"
					+"   <trade_type>JSAPI</trade_type>"
					+"   <sign>0CB01533B8C1EF103065174F50BCA001</sign>"
					+"   <test><t>0CB01533B8C1EF103065174F50BCA001</t></test>"
					+"</xml>";

		Map<String, Object> xmlMap = XmlUtils.xmlBody2map(xml, "xml/test");
		System.out.println(xmlMap.size());
	}
}