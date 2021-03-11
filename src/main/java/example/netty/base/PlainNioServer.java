package example.netty.base;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--26 16:07
 **/
public class PlainNioServer {


    public static void main(String[] args) throws IOException {
        server(8899);
    }

    private static void server (int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ssocket = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress("10.252.218.112",port);
        ssocket.bind(address);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> readyKeys =selector.selectedKeys();
            Iterator<SelectionKey> iterator =readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                System.out.println("selection key =" + JSON.toJSONString(key));
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector,SelectionKey.OP_READ);
                    System.out.println("accepted connection from " + client);
                }
                if (key.isReadable()) {
                    System.out.println("read event ............");
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    client.read(buffer);
                    byte [] dst = new byte[buffer.position()];
                    buffer.flip();
                    buffer.get(dst);
                    System.out.println(String.valueOf(dst));
                     client.register(selector,SelectionKey.OP_WRITE);
                    System.out.println("accepted connection from " + client);
                }
                if(key.isWritable()) {
                    System.out.println("write event ............");
                    SocketChannel client = (SocketChannel) key.channel();
                    String content = String.valueOf("hello " + JSON.toJSONString(client));
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    buff.put(content.getBytes());
                    buff.flip();
                    client.write(buff);
                    client.close();
                    System.out.println("accepted connection from " + client);
                }
            }
            System.out.println("********************************");
        }


    }
}
