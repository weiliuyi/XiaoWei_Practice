package example.base.juc;

import org.junit.Test;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--27 18:51
 **/
public class ThreadTest {

    /**
     *Thread类中调用interrupt()  interrupted() isInterrupted()
     * interrupt() 终端当前线程，只是将终端标志设置为true;  对象方法
     * interrupted 测试当前的中断状态，是Thread类的静态方法，并且清除中断状态；
     * isInterrupted() 返回当前线程的状态
     */


    @Test
    public void main() throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();
        thread.interrupt(); //对象方法
        Thread.sleep(1000);
        //当前线程的中断状态，如果中断，并且清楚终端状态  ---- 当前线程是main线程而不是thread线程
        System.out.println("------------------" + thread.interrupted());
        System.out.println("------------------" +  thread.interrupted());

    }

    @Test
    public void test2() throws InterruptedException {

        Thread.currentThread().interrupt();
        System.out.println("-------------" + Thread.interrupted());
        System.out.println("------------------" +  Thread.interrupted());
        System.out.println("------------------" +  Thread.interrupted());

    }

}


class Task  implements  Runnable{

    @Override
    public void run() {
        for (int i = 0;i < 100000;i++) {
            System.out.println(i);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}