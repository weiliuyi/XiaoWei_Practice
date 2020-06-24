package example;

import java.util.HashMap;

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
}
