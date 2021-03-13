package example.netty.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--13 09:59
 **/
public class NioBase {

    @Test
    public void writeFile () throws Exception {
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\weiliuyi\\Desktop\\test\\test.txt"));
        FileChannel fc = fos.getChannel();
        String str = "channel test !!!!!";
        ByteBuffer bf = ByteBuffer.allocate(1024);
        bf.put(str.getBytes());
        bf.flip();
        fc.write(bf);
        bf.clear();
        fos.close();
        fc.close();
    }

    @Test
    public void readFile () throws Exception {
        FileInputStream fis = new FileInputStream(new File("C:\\Users\\weiliuyi\\Desktop\\test\\test.txt"));
        FileChannel fc = fis.getChannel();
        ByteBuffer bf = ByteBuffer.allocate(1024);
        fc.read(bf);
        bf.flip();
        System.out.println(new String(bf.array()));
        fis.close();
        fc.close();
    }
}
