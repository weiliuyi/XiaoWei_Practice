package example.algorithm.interview;

import org.junit.Test;

import java.util.Arrays;

/**
 * @description: 20201031
 * @author: weiliuyi
 * @create: 2020--31 10:35
 **/
public class Practice {

    /**
     * 取出最右侧的1
     *
     * 1.将numA取反，得到numB
     * 2.将numB加上1,的结果与numA相与
     */

    @Test
    public void test1 () {
        Integer numA = 14;
        System.out.println(Integer.toBinaryString(numA));
        Integer numB = ~numA;
        System.out.println(Integer.toBinaryString(numB));
        System.out.println(Integer.toBinaryString(numA & (numB + 1)));
    }


    /**
     * 在一个数组中，一种数出现奇数次，其他都出现偶数次，请打印这个出现奇数次的数；
     *  ~ 这个符号是取非的符号 ^ 抑或或符号
     */
    @Test
    public void test2 () {
        int [] array = new int[] {1,1,3,3,3,3,44,44,88,88,88,5,5,5,5};
        int result = 0;
        for (int anArray : array) result ^= anArray;
        System.out.println(result);
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
     *
     *
     */
    @Test
    public void test3() {
        int [] array = new int[] {1,1,3,3,3,3,44,44,88,88,88,5,5,5,5,5};
        int[] oddNums = getOddNums(array);
        System.out.println(Arrays.toString(oddNums));
    }

    /**
     * 不适用第三个变量，进行交换两个变量的值
     */
    @Test
    public void test4 () {
        int a = 0;int b = 4;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a = " + a + " b= " + b);
    }

    /**
     * 一个字符串是否包含另外一个字符串  包含条件：和顺序无关，只要各个字母的数量相等就认为包含的
     */

    @Test
    public void test5 () {

    }

    /**
     * test5 进行暴力求解
     */

    private boolean isContain(String content,String target) {

//        String target;
//        for (int i = 0;i < content.length() - target.length() + 1) {
//
//        }
        return false;
    }







    private int[] getOddNums(int[] array) {
        int orElseArray = getOrElse(array);
        int rightOne = getLatestRightOne(orElseArray);

        int one = 0;
        int two = 0;
        for (int num : array) {
         if ((rightOne & num) == rightOne ) {
             one ^= num;
         } else {
             two ^= num;
         }
        }
        System.out.println("one = " + one + " two=" + two);
        return new int[] {one,two};
    }

    /**
     * 获取最右侧的1
     */

    private int getLatestRightOne (int num) {
        return num & (~num + 1);
    }

    /**
     * 对数组的每一个元素进行异或
     */
    private int getOrElse(int[] array) {
        int result = 0;
        for (int num : array) {
            result ^= num;
        }
        return result;
    }





}
