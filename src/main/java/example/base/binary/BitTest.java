package example.base.binary;

import org.junit.Test;

/**
 * @description: 二进制的一些运算
 * @author: weiliuyi
 * @create: 2020--26 10:00
 **/
public class BitTest {

    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static int RESIZE_STAMP_BITS = 16;

    public static void main(String[] args) {
        System.out.println(Math.pow(2,17)-1);
        System.out.println(Integer.toBinaryString(131072 -1));
    }

    @Test
    public void test1 () {
        System.out.println(16 >>> 2);
        System.out.println(16 >> 2);
        System.out.println(Integer.toBinaryString(16));
        System.out.println(Integer.toBinaryString(-16));
        System.out.println(Integer.toBinaryString(-16 >>> 2));
    }

    @Test
    public void test2 () {
        System.out.println(Math.pow(2,17)-1);
        System.out.println(Integer.toBinaryString(131072 -1));

        //移位的优先级是最低的
        System.out.println(16 << 1);
        System.out.println(16 << 1 + 1);
        System.out.println(16 + 16 << 1 + 1);
    }


    @Test
    public void test3 () {
        System.out.println(16 + (16 >> 1) + 1);
        System.out.println(tableSizeFor(25));
        System.out.println(tableSizeFor(29));

        System.out.println(tableSizeFor(63));
    }

    @Test
    public void test4 () {
     System.out.println(resizeStamp(16));
     System.out.println(Integer.numberOfLeadingZeros(16));
    }


    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

    private static  int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /*
     * >> 和 >>>的区别是什么？
     * >> 表示右移，如bai果该数为正du，则高位补0，若为zhi负数，则高位补1。如：int i=15; i>>2的结果是3，移出的部分将被抛弃
     * 转为二进制的形式可能更好理解，0000 1111(15)右移2位的结果是0000 0011(3)，0001 1010(18)右移3位的结果是0000 0011(3)
     * >>>表示无符号右移，也叫逻辑右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0
     * 按二进制形式把所有的数字向右移动对应位数，低位移出（舍弃），高位的空位补零。对于正数来说和带符号右移相同，对于负数来说不同
     *
     * a &= b; // 其实就是a = a & b; 其中&是按位与运算
     * a |= b; // 其实就是a = a | b; 其中|是按位或运算
     *  | 按位或
     *  &  按位与
     *
     */

    @Test
    public void test5 () {
        System.out.println(Integer.toBinaryString(4));
        //取非操作
        System.out.println(Integer.toBinaryString(~4));
    }

    @Test
    public void test6 () {

    }
}
