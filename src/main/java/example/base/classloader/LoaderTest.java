package example.base.classloader;

import org.junit.Test;

import java.util.stream.Stream;

public class LoaderTest {
    @Test
    public void test1 () {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<BootStrap ClassLoader>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Stream.of(System.getProperty("sun.boot.class.path").split(";")).
                forEach(System.out::println);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<ExtClassLoader>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Stream.of(System.getProperty("java.ext.dirs").split(";")).
                forEach(System.out::println);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<AppClassCloader>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Stream.of(System.getProperty("java.class.path").split(";")).
                forEach(System.out::println);
    }

    @Test
    public void test2 () {
        ClassLoader classLoader = this.getClass().getClassLoader();
    }
}
