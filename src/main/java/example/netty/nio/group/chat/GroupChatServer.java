package example.netty.nio.group.chat;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @description: 群聊天Server
 * @author: weiliuyi
 * @create: 2021--17 14:33
 **/
public class GroupChatServer {

    private int PORT =6667;

    private String HOST = "127.0.0.1";

    /**
     * 负责监听客户端的Socket连接
     */
    private ServerSocketChannel listenChannel;

    /**
     * 选择器
     */
    private Selector selector;

    public GroupChatServer () throws IOException {
        //开启一个选择器
        selector = Selector.open();
        //打开一个ServerSocketChannel
        listenChannel = ServerSocketChannel.open().bind(new InetSocketAddress(HOST, PORT));
        //设置成非阻塞
        listenChannel.configureBlocking(false);
        //注册到选择器
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 监听客户端的连接
     */
    private void listen() {
        while (true) {
            try {
                //选择已经就绪的SelectionKey
                int selectCount = selector.select();
                if (selectCount > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectKey = iterator.next();
                        //有接受连接的的事件
                        if (selectKey.isAcceptable()) {
                            //获取SockeChannel
                            SocketChannel socketChannel = listenChannel.accept();
                            //设置非阻塞
                            socketChannel.configureBlocking(false);
                            //注册到选择器
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() +  " online ");
                        }
                        //可读事件
                        if (selectKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectKey.channel();
                            // 读取消息
                            String message = receiveMessage(socketChannel);
                            //发送消息
                            if (StringUtils.isNotBlank(message)) {
                                sendMessage2OtherChannel(message,socketChannel);
                            }
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("没有可用的通道");
                }
            } catch (IOException e) {
                System.out.println("服务器已经下线");
            }
        }
    }


    /**
     *  接受消息
     * @param socketChannel 获取数据的通道
     * @return 消息实体
     */
    private String receiveMessage (SocketChannel socketChannel) {
        //分配缓冲区
        ByteBuffer msg = ByteBuffer.allocate(1024);
        try {
            //将数据从channel读入到缓冲区
            socketChannel.read(msg);
            String msgStr = new String(msg.array());
            System.out.println("Server receive msg = "  + msgStr + " From =" + socketChannel.getRemoteAddress());
            return msgStr;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println(socketChannel.getRemoteAddress() + " offline");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return "";
    }


    /**
     *  向除去selfChannel以外的其他channel发送数据
     *  @param  message 消息的内容
     *  @param  selfChannel 发送消息的channel
     */
    private void sendMessage2OtherChannel (String message,SocketChannel selfChannel) {
        //获取所有的selectKeys
        Iterator<SelectionKey> iterator = selector.keys().iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            SelectableChannel selectable = selectionKey.channel();
            //selectable 是 ServerSocketChannel类型 或者是等于selfChannel
            if (selectable instanceof ServerSocketChannel || selectable == selfChannel) {
                continue;
            }
            SocketChannel socketChannel = (SocketChannel)selectable;
            try {
                socketChannel.write(ByteBuffer.wrap(message.getBytes()));
            } catch (IOException e) {
                try {
                    System.out.println(socketChannel.getRemoteAddress().toString() + "offline");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //创建服务器并且配置参数
        GroupChatServer server = new GroupChatServer();
        //启动监听程序
        server.listen();
    }

}
