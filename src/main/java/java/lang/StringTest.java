package java.lang;

import sun.misc.Launcher;

public class StringTest {


    //不允许使用java.lang报名；
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = new Launcher().getClassLoader();
            Class<?> clazz = classLoader.loadClass("java.lang.String");
            System.out.println(classLoader);
            Object obj = clazz.newInstance();
            System.out.println(obj.getClass().getClassLoader());
            System.out.println(clazz.newInstance().toString());
        } catch (Exception e) {

        }
    }
}
