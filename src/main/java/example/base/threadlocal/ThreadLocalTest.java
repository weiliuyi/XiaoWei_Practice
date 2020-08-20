package example.base.threadlocal;

/**
 * @description: ThreadLocal 深入了解
 * @author: weiliuyi
 * @create: 2020--20 14:40
 **/
public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {
        local.set("wly");
        System.out.printf(local.get());
    }
}
