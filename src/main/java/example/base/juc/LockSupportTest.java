package example.base.juc;

import java.util.concurrent.locks.LockSupport;

/**
 * @description:  LockSupport
 * @author: weiliuyi
 * @create: 2020--07 19:36
 **/
public class LockSupportTest {


}

/**
 * stop和resume如果顺序反了，会出现死锁现象。而park和unpark却不会。这又是为什么呢？还是看一个例子
 * t1内部有休眠1s的操作，所以unpark肯定先于park的调用，但是t1最终仍然可以完结。这是因为park和unpark会对每个线程维持一个许可（boolean值）
 * unpark调用时，如果当前线程还未进入park，则许可为true
 *  park调用时，判断许可是否为true，如果是true，则继续往下执行；如果是false，则等待，直到许可为true
 *
 */
class LockSupportDemo2 {

    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("被中断了");
                }
                System.out.println("继续执行");
            }
        }
    }

    public static void main(String[] args) {
        t1.start();
        LockSupport.unpark(t1);
        System.out.println("unpark invoked");
    }
}

class LockSupportDemo {

    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }
        @Override public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("被中断了 " + getName());
                }
                System.out.println("继续执行 " + getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(3000L);
        t1.interrupt();
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}