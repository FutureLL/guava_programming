package com.lilei.guava.io;

import com.google.common.io.ByteStreams;
import org.junit.Test;

import java.io.*;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/12 19:12
 * @version: 1.0
 * @modified By:
 */
public class ByteStreamsTest {

    final String inputCopy = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\source.txt";
    final String outputCopy = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\outputCopy.txt";

    @Test
    public void testByteStreamsCopy() throws IOException {
        FileInputStream in = new FileInputStream(inputCopy);
        FileOutputStream out = new FileOutputStream(outputCopy);
        FileInputStream in_exhaust = new FileInputStream(outputCopy);
        FileInputStream in_limit = new FileInputStream(outputCopy);
        FileInputStream in_read = new FileInputStream(outputCopy);
        FileInputStream in_readFully = new FileInputStream(outputCopy);
        FileInputStream in_skipFully = new FileInputStream(outputCopy);
        FileInputStream in_toByteArray = new FileInputStream(outputCopy);

        //copy(InputStream from, OutputStream to): Copies all bytes from the input stream to the output stream.
        ByteStreams.copy(in, out);

        //exhaust(InputStream in): Reads and discards data from the given InputStream until the end of the stream is reached.
        System.out.println("ByteStreams.exhaust(): " + ByteStreams.exhaust(in_exhaust));

        //limit(InputStream in, long limit): Wraps a InputStream, limiting the number of bytes which can be read.
        InputStream limit = ByteStreams.limit(in_limit, 30);
        byte[] by = new byte[1024];
        int length = 0;
        while ((length = limit.read(by)) != -1) {
            System.out.println(new String(by, 0, length));
        }

        //nullOutputStream(): Returns an OutputStream that simply discards written bytes.
        OutputStream nullOutputStream = ByteStreams.nullOutputStream();
        System.out.println("nullOutputStream(): " + nullOutputStream);

        //read(InputStream in,byte[] b,int off,int len): Reads some bytes from an input stream and stores them into the buffer array b.
        byte[] bytes = new byte[30];
        ByteStreams.read(in_read, bytes, 0, 13);
        System.out.println(new String(bytes));

        //readFully(InputStream in, byte[] b): Attempts to read enough bytes from the stream to fill the given byte array
        byte[] byteFill = new byte[50];
        ByteStreams.readFully(in_readFully, byteFill);
        System.out.println(new String(byteFill));

        //skipFully(InputStream in, long n): Discards n bytes of data from the input stream.
        ByteStreams.skipFully(in_skipFully, 10);
        byte[] byteSkip = new byte[1024];
        int lengthSkip = 0;
        while ((lengthSkip = in_skipFully.read(byteSkip)) != -1) {
            System.out.println(new String(byteSkip, 0, lengthSkip));
        }

        //toByteArray(InputStream in): Reads all bytes from an input stream into a byte array.
        byte[] byteArray = ByteStreams.toByteArray(in_toByteArray);
        System.out.println("ByteStreams.toByteArray(): " + new String(byteArray));
    }
}
