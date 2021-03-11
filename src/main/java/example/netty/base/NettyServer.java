package example.netty.base;



import java.io.IOException;
import java.net.InetSocketAddress;
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
 * @create: 2021--26 15:11
 **/
public class NettyServer {
    private static int PORT = 8899;
    private static ByteBuffer echoBuffer = ByteBuffer.allocate(128);

    private static ByteBuffer sendBuffer = ByteBuffer.allocate(256);

    private static int msg = 0;


    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("localhost", PORT);
        ssc.bind(address);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("size = " + selectionKeys.size());
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                System.out.println("key " + key);
                String msg = new String();
                if (key.isAcceptable()) {
                    ServerSocketChannel sscNew = (ServerSocketChannel) key.channel();
                    SocketChannel sc = sscNew.accept();
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    int code = 0;
                    while ((code = sc.read(echoBuffer)) > 0) {
                        byte[] b = new byte[echoBuffer.position()];
                        echoBuffer.flip();
                        echoBuffer.get(b);
                        msg += new String(b,"UTF-8");
                    }
                    if (msg.contains("bye")) {
                        sc.close();
                    } else {
                        echoBuffer.clear();
                    }
                } else if (key.isWritable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    String sendTxt = "Message from Server";
                    sendBuffer.put(sendTxt.getBytes());
                    sendBuffer.flip();
                    int code = 0;
                    while ((code = sc.write(sendBuffer)) > 0 ) {}
                    sendBuffer.clear();
                    sc.register(selector,SelectionKey.OP_READ);
                }
                it.remove();
            }
        }
    }
}
