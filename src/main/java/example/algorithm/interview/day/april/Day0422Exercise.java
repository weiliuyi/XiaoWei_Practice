package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0422Exercise 求最值的算法优化
 * @Description https://mp.weixin.qq.com/s/p1Mo4unEG3R_ekzdjpZIsg
 * @Author weiliuyi
 * @Date 2021/4/22 4:00 下午
 **/
public class Day0422Exercise {


    /**
     * 同时求出 最大值 和最小值
     */
    @Test
    public void testMaxMin() {
        int[] arr = {1, 3, 10, 9, 8, 13, 7, 3};
        System.out.println(Arrays.toString(maxMin(arr)));
        int[] arrV2 = {1, 2, 3};
        System.out.println(Arrays.toString(maxMinV2(arrV2)));

        System.out.println(Arrays.toString(maxMinRecursive(arrV2,0,arrV2.length-1)));

    }


    /**
     * 同时求出 最大值和 次最大值
     */
    @Test
    public void testMaxLower() {
        int[] arr = {1, 3, 10, 9, 8, 13, 7, 3};
        System.out.println(Arrays.toString(maxLower(arr)));

    }

    /**
     * 如何同时求出最大，最小值
     * 存在的问题：
     * 冗余判断：
     * 1.第一个if判断成功时，第二个if肯定不成立，此时做了冗余判断
     * 2.满足第二个if的判断条件，第一个if判断肯定不成立，冗余判断
     * <p>
     * 优化方向：减少不必要的if冗余判断；
     */

    private int[] maxMin(int[] arr) {
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i]; // 1

            if (arr[i] < min) min = arr[i]; //2
        }

        return new int[]{max, min};
    }

    /**
     * 存在问题：
     * 角标越界异常
     */
    private int[] maxMinV2(int[] arr) {
        int max = arr[0];
        int min = arr[0];
        int i = 1;
        while (i < arr.length) { // i = arr.length-2
            if (arr[i] > arr[i + 1]) {
                max = Math.max(arr[i], max);
                min = Math.min(arr[i + 1], min);
            } else {
                max = Math.max(arr[i + 1], max);
                min = Math.min(arr[i], min);
            }
            i += 2;
        }
        return new int[]{max, min};
    }

    /**
     * 使用分治思想---递归求解算法
     */
    private int[] maxMinRecursive(int[] arr, int left, int right) {

        if (right == left) return new int[]{arr[left], arr[right]};

        if (right - left == 1)
            return new int[]{Math.max(arr[left], arr[right]), Math.min(arr[left], arr[right])};
        int mid = (left + right) / 2;
        int[] leftR = maxMinRecursive(arr, left, mid);
        int[] rightR = maxMinRecursive(arr, mid + 1, right);
        return new int[] {Math.max(leftR[0],rightR[0]),Math.min(leftR[1],rightR[1])};
    }


    /**
     * 同时求出最大值和第二大值
     */
    private int[] maxLower(int[] arr) {
        int max = Integer.MIN_VALUE;
        int lower = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                lower = max;
                max = arr[i];
            } else if (arr[i] > lower) {
                lower = arr[i];
            }
        }
        return new int[]{max, lower};
    }


}
