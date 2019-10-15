package com.qk365.ocr.util;

public class StringUtil {
    public static String trim(String str, char c) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        int st = 0;
        while ((st < len) && (chars[st] == c)) {
            st++;
        }
        while ((st < len) && (chars[len - 1] == c)) {
            len--;
        }

        return (st > 0) && (len < chars.length) ? str.substring(st, len) : str;
    }
}
