package example.algorithm.interview.test;

import java.util.Arrays;

/**
 * @description: 输入数组n，  返回出符合要求的 i < k < j  array[i] + array[j] != 2 * array[k]的 长度为N 数组；
 * 如果数组满足条件 2 * array[i] + 1 是满足条件的  2 * array[i] 满足条件
 *  2 * array......2 * array  也是满足条件的 奇数 + 偶数 = 奇数 所以一定成立
 * 使用逆递归的思想来解决；
 *
 * @author: weiliuyi
 * @create: 2020--24 17:18
 **/
public class ArraysFormat {

    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 7, 9};
        //System.out.printf(" " + checkArrayFormat(array));
        int[] formatArray = getFormatArray(5);
        System.out.println(Arrays.toString(formatArray));
        System.out.println(checkArrayFormat(formatArray));

    }


    private static int[] getFormatArray(int n) {
        if (n == 1) {
            return new int[]{1};
        }
        int halfSize = (n + 1) / 2; //向上取整
        int[] formatArray = getFormatArray(halfSize);
        int[] array = new int[n];
        for (int i = 0; i < halfSize; i++) {
            array[i] = 2 * formatArray[i] + 1;
        }
        int j = halfSize;
        for (int i = 0; j < n && i < halfSize; i++) {
            array[j] = 2 * formatArray[i];
            j++;
        }
        return array;
    }


    private static boolean checkArrayFormat(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int k = i + 1; k < array.length; k++) {
                for (int j = k + 1; j < array.length; j++) {
                    if (array[i] + array[j] == 2 * array[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
