package com.lilei.guava.io;

import com.google.common.base.Preconditions;

/**
 * @description: 完成Base64的两个功能encode(),decode()
 * @author: Alex Wang
 * @date: Created in 2019/12/13 17:12
 * @version: 1.0
 * @modified By: Mr.Li
 */
public final class Base64 {

    private final static String CODE_STRING = "ABCDEFGHIJKLIMOPQRSTUVWXYZabcdefghijklimopqrstuvwxyz0123456789+/";

    private final static char[] CODE_DICT = CODE_STRING.toCharArray();

    private Base64() {

    }

    public static String encode(String plain) {
        // 判断是否为空
        Preconditions.checkNotNull(plain);
        // 创建StringBuilder对象
        StringBuilder result = new StringBuilder();
        // 得到二进制的字符串形式
        String binaryString = toBinary(plain);
        // 因为是base64的方式,所以需要6个字符进行一组
        int delta = 6 - binaryString.length() % 6;
        // 不够的话需要补0
        for (int i = 0; i < delta && delta != 6; i++) {
            binaryString += "0";
        }
        // 每6个分成一组
        for (int index = 0; index < binaryString.length() / 6; index++) {
            int begin = index * 6;
            // 切割
            String encodeString = binaryString.substring(begin, begin + 6);
            // 计算得到每组的十进制,并得到相应的字符
            // valueOf(String s, int radix): Returns an Integer object holding the value extracted from the specified String when parsed with the radix given by the second argument.
            char encodeChar = CODE_DICT[Integer.valueOf(encodeString, 2)];
            // 对最后的结果进行追加
            result.append(encodeChar);
        }

        // 根据补的0换=
        if (delta != 6) {
            for (int i = 0; i < delta / 2; i++) {
                result.append("=");
            }
        }

        return result.toString();
    }

    private static String toBinary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            // toBinaryString(): Returns a string representation of the integer argument as an unsigned integer in base 2.
            String charBin = Integer.toBinaryString(source.charAt(index));
            // 判断是否够八位,不够需要补0。
            // 为什么是8,因为1byte,是8bit
            int delta = 8 - charBin.length();
            // 补0过程
            for (int d = 0; d < delta; d++) {
                charBin = "0" + charBin;
            }
            // 追加的形式存放
            binaryResult.append(charBin);
        }
        // 返回
        return binaryResult.toString();
    }

    public static String decode(final String encrypt) {
        StringBuilder resultBuilder = new StringBuilder();
        String temp = encrypt;
        // 判断该字符第一次出现的位置
        int equalIndex = temp.indexOf("=");
        if (equalIndex > 0) {
            //得到没有去掉等号的字符串
            temp = temp.substring(0, equalIndex);
        }

        // 得到对应字符串的二进制字符串
        String base64Binary = toBase64Binary(temp);

        // 因为一个字符使用1byte表示,而1byte是8bit,所以这里是 / 8
        for (int i = 0; i < base64Binary.length() / 8; i++) {
            int begin = i * 8;
            String str = base64Binary.substring(begin, begin + 8);
            // 这里将一个上边得到的字符串变成一个字符数组,因为这个数组只有一个字符,所以[0]即可
            char chars = Character.toChars(Integer.valueOf(str, 2))[0];
            // 将得到的字符进行拼接得到编码之前的StringBuilder对象
            resultBuilder.append(chars);
        }
        // 返回结果
        return resultBuilder.toString();
    }

    private static String toBase64Binary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            // 判断该字符第一次出现的位置
            int i = CODE_STRING.indexOf(source.charAt(index));
            // 找到对应的字符,变成相应的二进制字符串
            String charBin = Integer.toBinaryString(i);
            // 如果得到的二进制字符串长度小于6那么需要在前边补0
            int delta = 6 - charBin.length();
            // 补0
            for (int d = 0; d < delta; d++) {
                charBin = "0" + charBin;
            }
            // 追加
            binaryResult.append(charBin);
        }
        // 返回
        return binaryResult.toString();
    }
}
