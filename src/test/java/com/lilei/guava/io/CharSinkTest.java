package com.lilei.guava.io;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.util.Collections.singletonList;

/**
 * CharSource ---> Reader
 * CharSink -----> Writer
 */
public class CharSinkTest {

    @Test
    public void testCharSink() throws IOException {
        //定义文件路径
        File charSinkFile = new File("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\CharSink.txt");
        File sourceFile = new File("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\source.txt");
        //charSinkFile.deleteOnExit();
        /**
         * CharSink(): A destination to which characters can be written,
         *             such as a text file. Unlike a Writer, a CharSink is not an open,
         *             stateful stream that can be written to and closed. Instead,
         *             it is an immutable supplier of Writer instances.
         *
         * asCharSink(): Returns a new CharSink for writing character data to the given file using the given character set.
         */
        CharSink charsink = Files.asCharSink(charSinkFile, Charsets.UTF_8, FileWriteMode.APPEND);
        //write(): Writes the given character sequence to this sink.
        charsink.write("CharSink");
        //writeFrom(): Writes all the text from the given Readable (such as a Reader) to this sink.
        charsink.writeFrom(new FileReader(sourceFile));
        //writeLine(): Writes the given lines of text to this sink with each line (including the last) terminated with the operating system's default line separator.Iterable<File> fileIterable = Files.fileTraverser().depthFirstPreOrder(new File("F:\\IDEA\\guava_programming\\src\\main"));
        ImmutableList<String> charSource = Files.asCharSource(sourceFile, Charsets.UTF_8).readLines();
        charsink.writeLines(charSource);
        charsink.writeLines(singletonList("Append text using Google Guava."));
    }
}
