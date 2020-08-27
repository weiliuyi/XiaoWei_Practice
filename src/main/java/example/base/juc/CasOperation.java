package example.base.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:  cas的操作
 * @author: weiliuyi
 * @create: 2020--20 17:02
 **/
public class CasOperation {
    /**
     * cas 的缺点：1. aba问题  2. 只能保证一个变量的原子操作 3. 消耗CPU的资源
     *      优点：减少CPU的上下文的切换，适用范围：更新操作比较快的
     *
     */

    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println(AtomicInteger.class.getDeclaredField("value"));

    }
}
