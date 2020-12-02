package example.base.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--02 19:04
 **/
public class Main {
    public static void main(String[] args) {
        Annotation[] annotations = Son.class.getAnnotations();
    }

    @Test
    public void test1 () {
        Annotation[] annotations = Son.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
        // A ,B
    }

    @Test
    public void test2 () {
        Annotation[] annotations = Son.class.getDeclaredAnnotations();
        System.out.println(Arrays.toString(annotations));
        // A,B
    }

    @Test
    public void test3 () {
        Class<? super Son> father = Son.class.getSuperclass();
        System.out.println(father);
        //father
    }

}
