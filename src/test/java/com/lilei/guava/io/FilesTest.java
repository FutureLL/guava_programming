package com.lilei.guava.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/10 20:17
 * @version: 1.0
 * @modified By:
 */
public class FilesTest {

    private final String SOURCE_FILE = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\source.txt";
    private final String TARGET_FILE = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\target.txt";

    @Test
    public void testCopyFileWithGuava() throws IOException {
        File targetFile = new File(TARGET_FILE);
        File sourceFile = new File(SOURCE_FILE);
        //copy(): Copies all the bytes from one file to another.
        Files.copy(sourceFile, targetFile);
        assertThat(targetFile.exists(),equalTo(true));

        HashCode hashSource = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode hashTarget = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(hashSource.toString(), equalTo(hashTarget.toString()));

    }

    //Java的nio提供的Copy
    @Test()
    public void testCopyFileWithJDKNio2() throws IOException {
        File targetFile = new File(TARGET_FILE);
        java.nio.file.Files.copy(
                Paths.get(SOURCE_FILE),
                Paths.get(TARGET_FILE),
                StandardCopyOption.REPLACE_EXISTING
        );
        assertThat(targetFile.exists(),equalTo(true));
    }

    //文件的移动
    @Test
    public void testMoveFile() throws IOException {
        try {
            //move(): 在一个文件夹中相当于重命名,不同文件夹才相当于移动
            Files.move(new File(SOURCE_FILE), new File(TARGET_FILE));
            assertThat(new File(TARGET_FILE).exists(),equalTo(true));
            assertThat(new File(SOURCE_FILE).exists(),equalTo(false));
        } finally {
            Files.move(new File(TARGET_FILE), new File(SOURCE_FILE));
        }

    }

    //读取File中的字符串
    @Test
    public void testToString() throws IOException {
        final String expectedString = "today we will share the Google Guava io knowledge.\n" +
                "but only for the basic usage.if you wanted to get the more details information please read the Guava document or source code.\n" +
                "\n" +
                "the Guava source code is very cleanly and nice.";
        //每次读取一行,其中不包括 "\n" 换行,所以使用Joiner的时候需要用on("\n")连接
        List<String> strings = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String result = Joiner.on("\n").join(strings);
        assertThat(result,equalTo(expectedString));
    }

    //对每一行进行设置
    @Test
    public void testToProcessString() throws IOException {
        /**
         * Guava的14版本还有这个方法,我用的是28版本这个方法已经被注销了
         * Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8, new LineProcessor<Object>() {
         *     @Override
         *     public boolean processLine(String line) throws IOException {
         *         return false;
         *     }
         *     @Override
         *     public Object getResult() {
         *         return null;
         *     }
         * });
         */

        //一行一行的读取,每读取一行都交给每行的行处理器LineProcessor
        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<>() {

            private final List<Integer> lengthList = new ArrayList<>();

            /**
             * This method will be called once for each line.
             * @param line the line read from the input, without delimiter
             * @return true to continue processing, false to stop
             */
            @Override
            public boolean processLine(String line) throws IOException {
                if (line.length() == 0){
                    return false;
                }
                lengthList.add(line.length());
                //我们这里选择true
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return lengthList;
            }
        };
        List<Integer> result = Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8).readLines(lineProcessor);
        System.out.println(result);
    }

    //文件的hash值
    @Test
    public void testFileSha() throws IOException {
        File file = new File(SOURCE_FILE);
        /**
         * md5(): 方法过期
         * Files.hash(file, Hashing.md5());
         *
         * hash(): 方法过期
         * Files.hash(file, Hashing.goodFastHash(128));
         */
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode.toString());
    }

    //文件写入
    @Test
    public void testFileWrite() throws IOException {
        String testPath = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\testFileWrite.txt";
        File testFile = new File(testPath);
        //删除文件
        testFile.deleteOnExit();
        String content1 = "content 1";
        //给文件写入,采用追加的方式APPEND,目前只有这一种方式
        /**
         * write(): 方法过期
         * Files.write(content1, testFile, Charsets.UTF_8);
         */
        Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND).write(content1);
        //文件读取
        /**
         * toString(): 方法过期
         * Files.toString(testFile,Charsets.UTF_8);
         */
        String actully = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actully,equalTo(content1));

        /**
         * 也可以不使用追加的方式,它会直接把原有的内容删掉
         * 如果使用追加的方式那么直接在 asCharSink() 方法中添加一个参数 FileWriteMode.APPEND 即可
         */
        String content2 = "content 2";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content2);
        actully = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actully,equalTo(content2));
    }

    //文件追加
    @Test
    public void testFileAppend() throws IOException {
        String testPath = "F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\testFileWrite.txt";
        File testFile = new File(testPath);
        //虚拟机终止时删除该路径所表示的文件
        testFile.deleteOnExit();

        String content1 = "content 1";
        //追加FileWriteMode.APPEND
        CharSink charSink = Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write(content1);
        String actually = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(content1));

        charSink.write(content1);
        actually = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actually,equalTo(content1+content1));
    }

    //创建空文件
    @Test
    public void testTouchFile() throws IOException {
        File touchFile = new File("F:\\IDEA\\guava_programming\\src\\test\\resources\\io\\touch.txt");
        //虚拟机终止时删除该路径所表示的文件
        touchFile.deleteOnExit();
        //touch(): Creates an empty file or updates the last updated timestamp on the same as the unix command of the same name.
        Files.touch(touchFile);
        assertThat(touchFile.exists(),equalTo(true));
    }

    //得到文件路径
    @Test
    public void testRecursive(){
        List<File> list = new ArrayList<>();
        //得到文件路径
        recursiveListPath(new File("F:\\IDEA\\guava_programming\\src\\main"), list);
        /**
         * 本质上是一个循环相当于:
         * for(File file : list) {
         *     System.out.println(file);
         * }
         */
        list.forEach(System.out :: println);

        System.out.println("===============================");

        //只得到文件
        List<File> listFile = new ArrayList<>();
        recursiveList(new File("F:\\IDEA\\guava_programming\\src\\main"), listFile);
        listFile.forEach(System.out :: println);
    }

    //只得到文件
    private void recursiveList(File root, List<File> fileList){
        if (root.isHidden()){
            return;
        }
        if (root.isFile()){
            fileList.add(root);
        } else {
            File[] files = root.listFiles();
            for (File f : files){
                recursiveList(f, fileList);
            }
        }
    }

    //得到文件路径
    private void recursiveListPath(File root, List<File> fileList){
        //isHidden(): Tests whether the file named by this abstract pathname is a hidden file.
        if (root.isHidden()){
            return;
        }
        fileList.add(root);
        //isFile(): Tests whether the file denoted by this abstract pathname is a normal file.
        if (!root.isFile()){
            //listFiles(): Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname
            File[] files = root.listFiles();
            for (File f : files){
                recursiveListPath(f, fileList);
            }
        }
    }

    //深度优先遍历
    @Test
    public void testTreeFilesDepthFirstPreOrPostOrder(){
        File root = new File("F:\\IDEA\\guava_programming\\src\\main");
        //简单说是先序遍历
        //fileTraverser(): Returns a Traverser instance for the file and directory tree.
        //depthFirstPreOrder(): Returns an unmodifiable Iterable over the nodes reachable from any of the startNodes, in the order of a depth-first pre-order traversal.
        Files.fileTraverser().depthFirstPreOrder(root).forEach(System.out :: println);

        System.out.println("======================");

        //如果只要文件
        Iterable<File> filePreTxt = Files.fileTraverser().depthFirstPreOrder(root);
        Iterator<File> it = filePreTxt.iterator();
        while (it.hasNext()){
            File fileNext = it.next();
            if (fileNext.isFile()){
                System.out.println(fileNext);
            }
        }

        System.out.println("======================");

        //后序遍历
        //depthFirstPostOrder(): Returns an unmodifiable Iterable over the nodes reachable from startNode, in the order of a depth-first post-order traversal.
        Files.fileTraverser().depthFirstPostOrder(root).forEach(System.out :: println);
    }

    //广度优先遍历
    @Test
    public void testTreeFilesBreadthFirst(){
        File root = new File("F:\\IDEA\\guava_programming\\src\\main");
        //breadthFirst(): Returns an unmodifiable Iterable over the nodes reachable from startNode, in the order of a breadth-first traversal.
        Files.fileTraverser().breadthFirst(root).forEach(System.out :: println);
    }

    //每次执行之前,如果被Copy的文件存在那么就删除
    @After
    public void tearDown(){
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }
}