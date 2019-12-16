package com.lilei.guava.io;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ByteSinkTest {

    private final String path = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\ByteSinkTest.dat";

    @Test
    public void testByteSink() throws IOException {
        File filePath = new File(path);
        filePath.deleteOnExit();
        ByteSink byteSink = Files.asByteSink(filePath);
        byte[] bytes = {0x01, 0x02};
        //write(): Writes all the given bytes to this sink.
        byteSink.write(bytes);

        byte[] expected = Files.toByteArray(filePath);
        assertThat(expected, equalTo(bytes));
    }

    @Test
    public void testFromSourceToSink() throws IOException {
        String source = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\files.PNG";
        String target = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\files-2.PNG";
        File sourceFile = new File(source);
        File targetFile = new File(target);
        targetFile.deleteOnExit();
        ByteSource byteSource = Files.asByteSource(sourceFile);
        //copyTo(): Copies the contents of this byte source to the given ByteSink.
        byteSource.copyTo(Files.asByteSink(targetFile));

        assertThat(targetFile.exists(), equalTo(true));

        assertThat(
                //hash(): Hashes the contents of this byte source using the given hash function.
                Files.asByteSource(sourceFile).hash(Hashing.sha256()).toString(),
                equalTo(Files.asByteSource(targetFile).hash(Hashing.sha256()).toString())
        );
    }
}
