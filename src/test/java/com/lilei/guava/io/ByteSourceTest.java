package com.lilei.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ByteSourceTest {

    private final String path = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\files.PNG";

    @Test
    public void testAsByteSource() throws IOException {
        File filePath = new File(path);
        ByteSource byteSource = Files.asByteSource(filePath);
        //read(): Reads the full contents of this byte source as a byte array.
        byte[] readByte = byteSource.read();
        assertThat(readByte, equalTo(Files.toByteArray(filePath)));
    }

    @Test
    public void testSliceByteSource() throws IOException {
        //wrap(): Returns a view of the given byte array as a ByteSource.
        ByteSource byteSource = ByteSource.wrap(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
        //slice(): Returns a view of a slice of this byte source that is at most length bytes long starting at the given offset.
        ByteSource sliceByteSource = byteSource.slice(5, 5);
        byte[] bytes = sliceByteSource.read();
        for (byte b : bytes){
            System.out.println(b);
        }

        //size(): Returns the size of this source in bytes, even if doing so requires opening and traversing an entire stream.
        System.out.println("sliceByteSource size is: " + sliceByteSource.size());

        //isEmpty(): Returns whether the source has zero bytes.
        System.out.println("sliceByteSource is empty: " + sliceByteSource.isEmpty());
    }
}
