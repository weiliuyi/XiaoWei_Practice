package example.algorithm.interview;

import org.junit.Test;

/**
 * @description: 抑或运算的算法
 * @author: weiliuyi
 * @create: 2020--09 11:38
 **/
public class BitOrElse {

    /**
     * 抑或的性质
     * N ^ N  = 0  N ^ 0 = N  (偶数个N进行抑或运算，是0，奇数个N抑或运算 为N)
     * 抑或运算满足 交换律 和 结合律
     *
     */



    /**
     * 怎么把一个Int类型的数，提取出来最右侧的1
     *  n & (~n+1)
     *  示例：
     *            10010
     *  取非      01101
     *  加一操作：01110
     *  相与操作：00010    获得最右侧的1
     *
     * 1. 为什么是这样的？ 假设一个数的第n位1，右边的都是0，取反操作的  第n为0，右边都是1
     * 2. 此时加上1，导致第n未右边的都变成了0，第n位变成1，
     *  当1， 2 的结果相与之后，由于第n位左侧，都相反，相与为0，2 第n位右侧都为0，相与的结果为0，第n位 都是1，相与未1
     *
     *
     */
    @Test
    public void test1 () {
        System.out.println(Integer.toBinaryString(4));
        //取非操作
        System.out.println(Integer.toBinaryString(~4));

        System.out.println(Integer.toBinaryString(4 & ((~4) + 1)));
    }


    /**
     * 在一个数组中，一种数出现奇数次，其他都出现偶数次，请打印这个出现奇数次的数；
     *
     */
    @Test
    public void test2 () {
        int [] array = new int[] {1,1,3,3,3,3,44,44,88,88,88,5,5,5,5};
        System.out.println(getOddNumber(array));
    }

    /**
     * 获取出现奇数次的数(只有一种)  使用性质：偶数个相同的数相抑或都为0，奇数个相同的数相抑或都为 这个数；
     * @param array 测试数组
     * @return 返回出现奇数次的数
     */
    private int getOddNumber (int [] array) {
        int result = 0;
        for (int anArray : array) {
            result ^= anArray;
        }
        return result;
    }

    /**
     * 一个数组中，有两种数出现奇数次，其他都出现了偶数次
     *
     * 1.如果把所有的所有的数进行抑或，就是等价于这个奇数次的相抑或
     * 2.如果相抑或之后我们能得到哪些信息呢？
     *    2.2 加入某一位为1的话，那么我们就就可以知道 这个两个这出现奇数次的数，在这一位是不相等的；
     *    2.3 我们就可以根据这一位将整个数组分两部分；
     *    对这两类数组分别进行抑或？？？？
     *    分别得到这个连个技术
     */

    @Test
    public void test3 () {
        int [] array = new int[] {1,1,3,3,3,3,44,44,88,88,88,5,5,5,5,5};
        printDoublOddNumber(array);
    }

    private void printDoublOddNumber (int[] array) {
        int result = 0;
        for (int num :array) { //数组中每一个数相抑或，结果就是可以得到这两个奇数的抑或 A ^ B
            result ^= num;
        }
        int rightOne = result & ((~result) + 1);
        int a = 0;
        for (int num : array) {
            if ((rightOne & num) != 0) {
                a ^= num;
            }
        }
        System.out.println("number a : " + a + " number b : " + (result ^ a));
    }

    /**
     * 不适用中间变量，交换两个变量
     *
     */

    @Test
    public void test4 () {
        int a = 1,b = 2;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a = " + a + " b = " + b);
    }

}
