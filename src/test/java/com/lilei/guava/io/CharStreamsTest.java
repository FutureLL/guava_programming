package com.lilei.guava.io;

import com.google.common.io.CharStreams;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class CharStreamsTest {

    final String sourceCharStream = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\source.txt";

    @Test
    public void testCharStream() throws IOException {
        File fileCharStream = new File(sourceCharStream);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileCharStream));
        StringBuffer stringBuffer = new StringBuffer();
        //copy(Readable from, Appendable to): Copies all characters between the Readable and Appendable objects.
        CharStreams.copy(bufferedReader, stringBuffer);
        bufferedReader.close();

        //asWrite(Appendable target): Returns a Writer that sends all output to the given Appendable target.
        //该方法是把Appendable对象当做一个Writer对象来操作,所有这个Writer对象操作的都相当于Appendable对象操作的
        StringBuilder sbAsWrite = new StringBuilder();
        Writer writerStringBuffer = CharStreams.asWriter(sbAsWrite);
        writerStringBuffer.append("I used asWrite method.");
        System.out.println(sbAsWrite);

        //exhaust(Readable readable): Reads and discards data from the given Readable until the end of the stream is reached.
        //这个方法我暂时没看懂,他说的丢弃是丢弃文件中的,还是其他的没懂,如果是原文件中的那么为什么数据还在,看源码发现在返回之前就被close了
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\exhaustTest.txt"));
        System.out.println("exhaust: " + CharStreams.exhaust(bufferedReader1));

        //nullWriter(): Returns a Writer that simply discards written chars.
        Writer nullWriter = CharStreams.nullWriter().append("I am nullWriter() method.");
        nullWriter.flush();
        System.out.println(nullWriter);

        //readLines(Readable r): Reads all of the lines from a Readable object.
        FileReader fileReader = new FileReader("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\source.txt");
        List<String> listReadLines = CharStreams.readLines(fileReader);
        Iterator<String> iterator = listReadLines.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        //skipFully(Reader reader, long n): Discards n characters of data from the reader.
        //if n > reader file the number of bytes, then a EOFException will be reported
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\exhaustTest.txt"));
        CharStreams.skipFully(bufferedReader2, 10);
        //toString(Readable r): Reads all characters from a Readable object into a String.
        System.out.println(CharStreams.toString(bufferedReader2));
    }
}
