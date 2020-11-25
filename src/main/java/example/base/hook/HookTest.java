package example.base.hook;

/**
 * @description: 钩子函数，可以用来停机前进行释放资源；
 * @author: weiliuyi
 * @create: 2020--23 14:00
 **/
public class HookTest {

    /**
     * 钩子函数的作用：
     * 1. 释放程序占用的资源，释放db连接池的连接
     */

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("钩子函数执行了");
        }));

        Thread.sleep(10000L);
    }
}
