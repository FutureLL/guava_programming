package com.lilei.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/13 9:05
 * @version: 1.0
 * @modified By:
 */
public class CloserTest {

    @Test
    public void testCloser() throws IOException {
        ByteSource byteSource = Files.asByteSource(new File("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\files.PNG"));
        // Closer: A Closeable that collects Closeable resources and closes them all when it is closed.
        // create(): Creates a new Closer.
        Closer closer = Closer.create();
        try {
            // openStream(): Opens a new InputStream for reading from this source.
            // register(): Registers the given closeable to be closed when this Closer is closed.
            InputStream register = closer.register(byteSource.openStream());
        } catch (Throwable e) {
            // rethrow(): Stores the given throwable and rethrows it.
            throw closer.rethrow(e);
        } finally {
            // close(): Closes all Closeable instances that have been added to this Closer.
            closer.close();
        }
    }

    // 演示try的执行顺序
    @Test(expected = RuntimeException.class)
    public void testTryCatchFinally() {
        try {
            System.out.println("work area.");
            // 这里需要抛出一个异常,之后才会执行进入到catch中
            throw new IllegalArgumentException();
        } catch (Exception exception) {
            System.out.println("exception area.");
            // 运行结束的时候抛出一个异常,这里为了不让他抛出,那么会在@Test中加入一个属性expected
            throw new RuntimeException();
        } finally {
            System.out.println("finally area.");
        }
    }

    @Test
    public void testTryCatch() {
        /**
         * 这里哟孤儿问题,就是RuntimeException("1")异常会被RuntimeException("2")异常覆盖,就不会看到RuntimeException("1")这个异常
         *
         * 解决: 把异常进行保存,放到最后的这个异常,这样所有的异常就都被打印出来了,不会出现被覆盖的情况
         */
        Throwable throwable = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception exception) {
            throwable = exception;
            throw exception;
        } finally {
            RuntimeException runtimeException = new RuntimeException("2");
            // addSuppressed(Throwable exception): Appends the specified exception to the exceptions that were suppressed in order to deliver this exception.
            runtimeException.addSuppressed(throwable);
            throw runtimeException;
        }
    }

    // 上边的改进,这样所有的异常都会被跑出去,方便代码的查看bug
    @Test
    public void testTryCatchImprove() {
        Throwable throwable = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception exception) {
            throwable = exception;
            throw exception;
        } finally {
            try {
                // close
                throw new RuntimeException("2");
            } catch (Exception e) {
                throwable.addSuppressed(e);
            }
        }
    }
}
