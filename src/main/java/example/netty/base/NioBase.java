package example.netty.base;

/**
 * @description: netty需要的Nio基础知识(https://ifeve.com/overview/)
 * @author: weiliuyi
 * @create: 2021--18 15:52
 **/
public class NioBase {
    /**
     * 美团：
     * https://tech.meituan.com/2016/11/04/nio.html
     *
     * https://www.infoq.cn/article/netty-high-performance
     *
     *
     * 网络通信，多线程编程，序列化和反序列化，异步和同步编程，SSL和TLS安全，内存池，http，MQTT等多种协议栈   TCP/IP协议栈，Socket通信
     * Nio基本思想：
     *
     * 1.缓冲区 Buffer----ByteBuffer
     * 读写 相对于谁而言？ 通道或者缓存区
     * 在Nio中所有的数据都是用缓冲区处理的，在读数据的时候，是将数据从通道写到缓冲区的，
     * 在写数据的时候，是读取缓冲区数据写入到通道中的，任何时候访问Nio中的数据，都是通过缓冲区进行操作的；缓冲区提供了对数据进行结构化访问以及维护读写位置的(limit)信息；
     *  Buffer的实现原理
     *  https://ifeve.com/buffers/
     *
     *  MappedByteBuffer:内存映射文件
     *
     *
     * 2.channel（通道） 是双向的，可以同时进行读写
     * 通过通道读取和写入数据
     * 通道和流的区别？
     * 1.通道是双向的(全双工)，流是单向的
     *
     * FileChannel: 从文件中读取数据,在使用FileChannel之前，必须先打开它。但是，我们无法直接打开一个FileChannel，
     * 需要通过使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例
     * DatagramChannel:能从UDP中读取网络中的数据
     * SocketChannel:通过TCP读取网络中的数据(在SocketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）)
     * ServerSocketChannel:可以监听新进来的TCP链接，像web服务器那样，每新进来的链接都会创建一个SocketChannel
     *
     * 3.多路复用 selector
     * 1.提供选择已经就绪的任务的能力，selector会不断轮询注册在其上的channel
     * 一个单独的线程管理多个channel，从而可以管理多个网络连接
     *
     * 为什么要使用Selector?
     * 使用一个线程管理所有通道，减少操作系统的上下文的切换，并且减少占用操作系统的资源；
     *
     * Selector的创建？
     * Selector selector = Selector.open();
     * 向Selector注册通道？
     * channel.configureBlocking(false);
     * SelectionKey key = channel.register(selector,
     * Selectionkey.OP_READ);
     *
     * 为了将Channel和Selector配合使用，必须将channel注册到selector上。通过SelectableChannel.register()方法来实现
     * 与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。
     *注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
     *1.Connect
     *2.Accept
     *3.Read
     *4.Write
     *
     */
}
