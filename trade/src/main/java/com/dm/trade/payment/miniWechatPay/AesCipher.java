package com.dm.trade.payment.miniWechatPay;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Roy.Lust@gmail.com
 * 7/11/16
 * 11:04 PM
 */
public class AesCipher {
    /**
     * AES/CBC/128bit 加密算法
     * ECB算法不需要initVector码
     *
     * @param key        密码
     * @param initVector 盐
     * @param value      文本字符
     * @return Base64后的加密字符
     */
    protected static String encrypt(String key, String initVector, String value)
            throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

        byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
        return Base64.encodeBase64URLSafeString(encrypted);
    }

    /**
     * AES/CBC/128bit 解密算法
     *
     * @param key        密码
     * @param initVector 盐
     * @param encrypted  秘文
     * @return 文本
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    protected static String decrypt(String key, String initVector, String encrypted)
            throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(original, "UTF-8");
    }

    /**
     * 生成随机字符串
     * @return
     * @throws Exception
     */
    public static String generateSecretKey() throws Exception{
        String[] keyBase = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","e","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","E","S","T","U","V","W","X","Y","Z"};
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int index = (int) (Math.random()*keyBase.length%keyBase.length);
            key.append(keyBase[index]);
        }
        return key.toString();
    }

    public static void main(String[] args)
            throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        String key = "39f45e1f0501839a"; // 128 bit key
        String initVector = "6b71dacdbd1f7daf"; // 16 bytes IV
        //  String encryptD = encrypt(key, initVector, "你好中国");
        String encryptD = "4yI5toPvTNX4L4AOwBV3sbfIQ2s+HyyOsXK0eAa3mzrNVdIk8YC38fpjOc3VT/d31FI92SXT+IG8jrEnJxRnMux9bDqPk/o+DC8EGbt97z6t73JBgODFaQeFloXbTenju7wBppOoxP41m8yH3I/9I034wESEi3Hs5YsR/4mnK65rRoMJny3AM1TQjlElBmMUYP9TIprGcYFXXLioE/TpeSx4Cv3rZhkFXer25GrIKwJdEzsWtXbmrg0+u2GbYMzWDvfEJlI3dGjKFah1chKD72+9FcsidQwgBkc8yRpFjSGrjjMTYIYhHPTPAwFkqmK9TooeKHfjeiP6aFN8DqPci36mO2EwwNQXjpry0c7HXd5Nzbw09SJN1QM4Wm2NSJjKKPG2lq9zPQ8RD7+e/5NYqSCGrX2tIxl7CgDAdn/JmYqwic6M6PAipLwl/QamMS5eNIAlB8LexghzY4IrnVJ3uW+Ij0V0hfrIoHrQcn79BCSrjygWgNN4WpjD2Yzk9+gr5BGQftZxrfQqwtg2JW7KxtOUn5z38D7R80+FBjtsxHY2LcVGx8/TfDOx/xKExVNsFrDRRjG+BZXv+TGx2MB990sYRhoamJKx8J9D2WbX6/kjB76SK1H7QhELXjIf7cbstHxGsjEjKzucKSxN6Stdk8dOGeNKfs+GaGmWsAkVfGl1u/KrKuMfc0tzODDte/F8pm+ZeGawXZClYX6RvV0eDbhFHQjbaQ0D86hqiN6zc4pcHavX8j44AVqVpOSQd1LBcUm8DbYsqUXxSYZSyxLoQSBJ7G25gx+IuC5L0HFlA0JJFzrCLalLSvcK1NY8cY1C1Sc+cqHdaR7FlbPgwJyq7bvGjqNopsj+hJJ8j3KZFWTkfzMvXoF8IbD0cZD2X1etNYZs7rLot4aYG8r5HN+g2Q/2qr0697YiLhii6/S0M+pLVnxy4KcyxYIGvq54lTchJCWD8NGBMQJKUET/0jAsdA==";
        System.out.println("encryptD ~> " + encryptD);

        String text = decrypt(key, initVector, encryptD);
        System.out.println(text);
    }
}
