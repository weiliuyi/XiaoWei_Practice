package example.base.java8;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--27 19:25
 **/
public class ForEachTest {

    /**
     * stream.foreach()中只能使用return,只是跳过本次循环，进行下次循环；相当于continue;
     */
    @Test
    public void test () {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.forEach(str -> {
            if (str == "5") {
                return; //继续下次循环，而不是跳出循环
            }
            System.out.println(str);
        });

    }
}
