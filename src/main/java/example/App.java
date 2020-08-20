package example;

import org.junit.Test;
import sun.misc.Launcher;

import java.time.Instant;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        System.out.println(1 << 1);
        System.out.println(3 >> 1);
        System.out.println(3 >>> 1);
        System.out.println(Integer.toBinaryString(-3));
        System.out.println(-3 >>> 1);
        System.out.println(Integer.toBinaryString(-3 >>> 1));
    }


    @Test
    public void classLoaderTest () {
        ClassLoader classLoader = this.getClass().getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
        System.out.println(java.lang.ClassLoader.getSystemClassLoader());
    }

    /*
    * 在程序运行过程中，一个类并不是简单由其二进制名字（binary name）定义的，
    * 而是通过其二进制名和其定义加载器所确定的命名空间（run-time package）所共同确定的
    * */

    @Test
    public void classLoaderTest2 () throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ClassLoader classLoader = new Launcher().getClassLoader();
        Class<?> clazz = classLoader.loadClass("example.App");
         App app = (App) clazz.newInstance();
         //ClassCastException example.App cannot  be cast to exmaple.App


        //tip:自己创建的应用类加载器，和操作系统使用的类加载器不是同一个；
    }

    @Test
    public void classLoaderTest3 () throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new Launcher().getClassLoader();
        Class<?> clazz = classLoader.loadClass("java.lang.String");
        System.out.println(classLoader);
        System.out.println(this.getClass().getClassLoader());
        Object obj = clazz.newInstance();
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(clazz.newInstance().toString());
    }
    
    @Test
    public void test3 () {
        int i = 0;
        while (i < 100) {
            System.out.println((int)((Math.random()*9+1)*100000));
            i++;
        }
    }


    @Test
    public void test4 () {
        System.out.println(Instant.now().toEpochMilli());
    }

    @Test
    public void test5() {
        System.out.println(String.valueOf("ab3efg42").getBytes().length);
    }
}
