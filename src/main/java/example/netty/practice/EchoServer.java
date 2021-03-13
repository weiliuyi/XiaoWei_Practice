package example.netty.practice;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;


/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--11 17:20
 **/
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start () throws InterruptedException {
        EchoServerHandler handler = new EchoServerHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group).
                    //指定使用的NIO传输Channel
                    channel(NioServerSocketChannel.class).
                    //使用指定端口，设置套接字
                    localAddress(new InetSocketAddress(port)).
                    //添加一个EchoServerHandler到子hannel的ChannelPipeline
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(handler);//@Shareable所以总是可以使用同样的实例
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();//异步的绑定服务器，调用sync() 阻塞等待，直到绑定完成
            future.channel().closeFuture().sync();//获取channel的CloseFuture,并且阻塞当前线程，直到完成
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8899).start();
    }
}
