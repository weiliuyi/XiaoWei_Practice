package example.netty.base;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--23 15:52
 **/
public class Channel {

    @Test
    public void readBuffer () throws Exception {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\weiliuyi\\Desktop\\t_agent_scope.sql", "rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        int cnt = fileChannel.read(buffer);
        while (cnt != -1) {
            System.out.println("cnt = " + cnt);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            cnt = fileChannel.read(buffer);
        }
        fileChannel.close();
    }
}
