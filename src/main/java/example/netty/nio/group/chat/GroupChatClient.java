package example.netty.nio.group.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @description: Nio群聊天的客户端
 * @author: weiliuyi
 * @create: 2021--17 15:57
 **/
public class GroupChatClient {

    private String SERVER_HOST = "127.0.0.1";

    private int PORT = 6667;

    private SocketChannel socketChannel;

    private Selector selector;

    public GroupChatClient() throws IOException {
        //启动一个注册器
        selector = Selector.open();
        //开启一个通道
        socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_HOST, PORT));
        //设置成非阻塞
        socketChannel.configureBlocking(false);
        //注册到选择器
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println(" local " + socketChannel.getLocalAddress().toString().substring(1));
    }

    /**
     *  接受消息
     */
     void receiveMsg () throws IOException {
        selector.select();
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            if (selectionKey.isReadable()) {
                ByteBuffer msgBuffer = ByteBuffer.allocate(1024);
                SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                socketChannel.read(msgBuffer);
                System.out.println("msg = " + new String(msgBuffer.array()));
            }
            iterator.remove();
        }
    }

    /**
     * 发送消息
     */
      void sendMessage (String msg) throws IOException {
        ByteBuffer sendMsg = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(sendMsg);
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient client = new GroupChatClient();



        new Thread(() -> {
            while(true) {
                try {
                    client.receiveMsg();
                } catch (IOException ignored) {

                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            client.sendMessage(msg);
        }


    }

}
