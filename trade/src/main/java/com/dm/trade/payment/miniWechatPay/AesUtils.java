package com.dm.trade.payment.miniWechatPay;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtils {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
        	throw new RuntimeException("秘钥异常");
        }
        // 判断Key是否为16位
        if (sKey.length()%16 != 0) {
        	throw new RuntimeException("秘钥异常");
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
//    public static String Decrypt(String sSrc, String sKey) throws Exception {
//        try {
//            // 判断Key是否正确
//            if (sKey == null) {
//            	throw new RuntimeException("秘钥异常");
//            }
//            // 判断Key是否为16位
//            if (sKey.length()%16 != 0) {
//            	throw new RuntimeException("秘钥异常");
//            }
////            byte[] raw = sKey.getBytes("UTF-8");
////            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//
//            SecretKeySpec skeySpec =  initKeyForAES(sKey);
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
//            byte[] original = cipher.doFinal(encrypted1);
//            String originalString = new String(original,"utf-8");
//
////            KeyGenerator kgen = KeyGenerator.getInstance("AES");
////            kgen.init(128, new SecureRandom(sKey.getBytes()));
////            SecretKey secretKey = kgen.generateKey();
////            byte[] enCodeFormat = secretKey.getEncoded();
////            SecretKeySpec key = initKeyForAES(sKey);
////            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
////            cipher.init(Cipher.DECRYPT_MODE, key);// 初始
////            byte[] result = cipher.doFinal(parseHexStr2Byte(sSrc));
//
//            return originalString;
//        } catch (Exception e) {
//        	throw e;
//        }
//    }

    private static SecretKeySpec initKeyForAES(String key) throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;

    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length()%16 != 0) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708"
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 =new Base64().decode(sSrc);//先用bAES64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String generateSecretKey() throws Exception{
    	String[] keyBase = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","e","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","E","S","T","U","V","W","X","Y","Z"};
    	StringBuffer key = new StringBuffer();
    	for (int i = 0; i < 32; i++) {
			int index = (int) (Math.random()*keyBase.length%keyBase.length);
			key.append(keyBase[index]);
		}
    	return key.toString();
    }

    public static void main(String[] args) throws Exception {

        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
//    	String cKey = Constant.SECRETKEY;
//    	System.out.println("秘钥是："+cKey);
//        // 需要加密的字串
//        String cSrc = "www.gowhere.so";
//        System.out.println(cSrc);
//        // 加密
//        String enString = AesUtils.Encrypt(cSrc, cKey);
//        System.out.println("加密后的字串是：" + enString);
//
//        // 解密
//        String DeString = AesUtils.Decrypt(enString, cKey);
//        System.out.println("解密后的字串是：" + DeString);
    }

}
