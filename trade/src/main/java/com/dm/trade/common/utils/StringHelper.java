package com.dm.trade.common.utils;

/**
 * Author: Roy.Lust@gmail.com
 * 8/13/16
 * 12:24 PM
 */
public class StringHelper {

    /**
     * 不换行输出用
     *
     * @param str String
     * @return String
     */
    public static String prettyNoLineWrap(String str) {
        return (str + "").replaceAll("\\r", "").replaceAll("\\n", "");
    }

    public static String prettyNoLineWrapShrinkSpace(String str) {
        return (str + "").replaceAll("\\r", "").replaceAll("\\n", "").replaceAll("\\s{2,}", " ").trim();
    }

    public static String prettyXML(String str) {
        return (str + "").replaceAll("\\r", "").replaceAll("\\n", "").replaceAll("\\s{2,}", " ").trim().replaceAll(">\\s+<", "><");
    }


    /**
     * 判断字符串是否常规合法
     *
     * @param str String
     * @return boolean
     */
    public static boolean checkString(String str) {
        /* 为了方便阅读，没有简化表达式 */
        if (str == null || str.equals("") || str.equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检验医院用户ID合法性
     * 兼容新鉴权流程
     *
     * @param Hospital_userid 医院用户ID
     * @return boolean
     */
    public static boolean validHospitalUserid(String Hospital_userid) {
        if (!checkString(Hospital_userid)) {
            return false;
        } else if (Hospital_userid.contains("\\ ") || Hospital_userid.contains("%")) {
            return false;
        } else if (Hospital_userid.length() > 34) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 组合hospital_userid的方法，预防规则变化
     *
     * @param openid         openid
     * @param patient_cardno 就诊卡
     * @return 医院用户唯一标识
     */
    @Deprecated
    public static String hospital_userid(String openid, String patient_cardno) {
        return openid + "_" + patient_cardno;
    }


    /**
     * 从医院用户ID中获取用户openid
     *
     * @param hospital_userid 医院用户ID
     * @return openid
     */
    @Deprecated
    public static String getOpenidFromHostapital_userid(String hospital_userid) {
        return (hospital_userid + "").substring(0, 28);
    }

    /**
     * 从医院用户ID中获取用户openid
     *
     * @param hospital_userid 医院用户id
     * @return 就诊卡号
     */
    @Deprecated
    public static String getCardNoFromHospital_userid(String hospital_userid) {
        return (hospital_userid + "").substring(29);
    }

    /**
     * @param Outp_unique_id 开单唯一ID
     * @return 开单日期
     */
    public static String getDateFromOutp_unique_id(String Outp_unique_id) {
        return Outp_unique_id.replaceAll("zj", "").replaceAll("_.*$", "");
    }

    /**
     * @param Outp_unique_id 开单唯一ID
     * @return 开单序列号
     */
    public static String getSeqenceIdFromOutp_unique_id(String Outp_unique_id) {
        return Outp_unique_id.replaceAll("^.*_", "");
    }

    /**
     * 判断字符串是否为0
     *
     * @param str 字符串
     * @return true为0
     */
    public static boolean ifZero(String str) {
        str += "";
        return str.equals("0") || str.equals("0.0") || str.equals("0.00");
    }

    /**
     * 正则验证身份证
     *
     * @param str 身份证字符串
     * @return T合法
     */
    @Deprecated
    public static boolean regexCheckIdCard(String str) {
        str += "";
        return str.matches("[1-9][0-9]{16}[0-9Xx]");
    }

    /**
     * 正则验证手机号
     *
     * @param str 手机号
     * @return T为合法
     */
    public static boolean regexCheckPhone(String str) {
        str += "";
        return str.matches("^1[34578][0-9]{9}$");
    }
}
