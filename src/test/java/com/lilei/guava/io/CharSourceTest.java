package com.lilei.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CharSourceTest {

    @Test
    public void testCharSourcewrap() throws IOException {
        /**
         * CharSource: A readable source of characters, such as a text file. Unlike a Reader,
         *             a CharSource is not an open, stateful stream of characters that can be read and closed.
         *             Instead, it is an immutable supplier of Reader instances.
         *
         * wrap(): Returns a view of the given character sequence as a CharSource.
         */
        CharSource charSource = CharSource.wrap("I am CharSource");
        //read(): Reads the contents of this source as a string.
        String resultAsRead = charSource.read();
        assertThat(resultAsRead, equalTo("I am CharSource"));

        //readLines(): Reads all the lines of this source as a list of strings.
        ImmutableList<String> list = charSource.readLines();
        assertThat(1,equalTo(list.size()));

        //length(): Returns the length of this source in chars, even if doing so requires opening and traversing an entire stream.
        assertThat((int) charSource.length(), equalTo(15L));

        //isEmpty(): Returns whether the source has zero chars.
        assertThat(charSource.isEmpty(), equalTo(false));

        //lengthIfKnown(): Returns the size of this source in chars, if the size can be easily determined without actually opening the data stream.
        assertThat(charSource.lengthIfKnown().get(), equalTo(15L));
    }

    @Test
    public void testConcat() throws IOException {
        //concat(): Concatenates multiple ByteSource instances into a single source.
        CharSource sourceConcat = CharSource.concat(
                CharSource.wrap("I am CharSource1\n"),
                CharSource.wrap("I am CharSource2\n"),
                CharSource.wrap("I am CharSource3\n"),
                CharSource.wrap("I am CharSource4\n"),
                CharSource.wrap("I am CharSource5")
        );

        System.out.println(sourceConcat.readLines().size());
        sourceConcat.lines().forEach(System.out :: println);
    }
}
