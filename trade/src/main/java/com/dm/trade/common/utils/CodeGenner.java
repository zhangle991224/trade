package com.dm.trade.common.utils;

import java.util.Random;

/**
 * @author zhongchao
 * @title CodeGenner.java
 * @Date 2017-11-23
 * @since v1.0.0
 */
public class CodeGenner {
    public static String getRandNum(int charCount) {
        StringBuilder charValue = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue.append(String.valueOf(c));
        }
        return charValue.toString();
    }

    private static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
