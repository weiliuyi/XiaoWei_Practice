package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0419 有趣且有用的位操作
 * @Description https://mp.weixin.qq.com/s/Z37PpJ5ZuK3pwEvaFuBxBg
 * @Author weiliuyi
 * @Date 2021/4/19 2:46 下午
 **/
public class Day0419 {

    @Test
    public void test () {


        System.out.println(Integer.toBinaryString(' ') + " " + Integer.valueOf(' '));
        System.out.println(Integer.toBinaryString('_') + " " + Integer.valueOf('_'));

        // 利用或操作 ｜ 和空格 将英文字符转化位消协
        System.out.println("----------大写转化为小写-----------");
        System.out.println( (char)('A' | ' '));
        System.out.println( (char)('A' + 32));
        System.out.println( (char)('A' | ' '));
        System.out.println( (char)('我' | ' '));

        //利用 与操作 &和 下划线 将英文字母转化位大写
        System.out.println("------------小写转化为大写----------");
        System.out.println((char)('a' & '_'));
        System.out.println((char)('B' & '_'));
        System.out.println("a binary = " + Integer.toBinaryString('a') + " _ binary= " + Integer.toBinaryString(('_')));

        //利用 抑或操作 ^和空格进行英文字符大小写的互换
        System.out.println("------------大写小写字符互换--------");
        System.out.println((char)('d' ^ ' '));
        System.out.println((char)('D' ^ ' '));

        System.out.println("-----------判断两个整数是否异号---------------");
        System.out.println((-1 ^ 2) < 0);
        System.out.println((1 ^ 2) < 0);


        System.out.println("---------------交换两个数-------------------");
        int a = 1,b = 2;
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("a = " + a + " b = " + b);


        System.out.println("-------------加一操作-----------------------");
        int n = 1;
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(~n));
        System.out.println(Integer.toBinaryString(-~n));

        System.out.println("-------------减一操作-----");
        int n2 = 2;
        n2 = ~-n;
        System.out.println(n2);

    }


    /**
     *  算法中常见的操作 n&(n-1):
     *  作用是消除数字n的二进制表示中的最后一个1
     *
     *  示例：
     *  20  10100
     *  20-1（右侧第一个1变为0，右侧的零都变为1）
     *      10011
     *  20 &（20 -1）
     *      10000
     *
     */
    @Test
    public void test2 () {

    }
}
