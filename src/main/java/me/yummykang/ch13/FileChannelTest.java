package me.yummykang.ch13;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * write some dec. here.
 * Created by Demon on 2016/11/16 0016.
 */
public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("test.txt", "rw");
        FileChannel fileChannel = accessFile.getChannel();
        String content = "write something into the file";
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put(content.getBytes());
        writeBuffer.flip();
        fileChannel.write(writeBuffer);
    }
}
