package com.lilei.guava.io;

import com.google.common.io.BaseEncoding;
import org.junit.Test;

public class BaseEncodingTest {

    // 编码
    @Test
    public void testBase64Encoding() {
        // BaseEncoding: A binary encoding scheme for reversibly translating between byte sequences and printable ASCII strings.
        // base64(): The "base64" base encoding specified by RFC 4648 section 4, Base 64 Encoding.
        BaseEncoding baseEncoding = BaseEncoding.base64();
        // encode(byte[] bytes): Encodes the specified byte array, and returns the encoded String.
        System.out.println(baseEncoding.encode("hello".getBytes()));    // print: aGVsbG8=
        /**
         * 分析:'a'是一个字符,占用1byte,8bit
         *      二进制: 01100001
         *      因为是base64,所以他是按照六位进制进行划分,结尾少了的需要补4个0
         *      得到:                  011000  010000 （后边的四个0是补的）
         *      得到相应的十进制:         24      16
         *      根据base64字符表:         Y       Q
         *      因为我们补了4个0,两个0表示一个=,那么需要补上两个=
         *      得到的结果是YQ==,也就是使用base64编码得到的结果
         */
        System.out.println(baseEncoding.encode("a".getBytes()));        // YQ==
    }

    // 解码
    @Test
    public void testBase64Decode(){
        BaseEncoding baseEncoding = BaseEncoding.base64();
        // decode(CharSequence chars): Decodes the specified character sequence, and returns the resulting byte[].
        System.out.println(new String(baseEncoding.decode("aGVsbG8=")));
    }

    // 我们自己写的encode()方法
    @Test
    public void testMyBase64Encode(){
        System.out.println(Base64.encode("a"));           // YQ==
    }

    // 我们自己写的decode()方法
    @Test
    public void testMyBase64Decode(){
        System.out.println(Base64.decode("YQ=="));      // a
    }
}
