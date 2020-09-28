package example.algorithm.interview.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @description: 归并排序 递归和非递归
 * @author: weiliuyi
 * @create: 2020--22 12:47
 **/
public class MergeSorted {


    @Test
    public void test1() {
        int[] array = {3, 2, 1, 5, 8, 6, 5, 4};
        mergeSorted(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 对数组进行归并排序
     *
     * @param array 目标数组
     */
    private void mergeSorted(int[] array) {
        if (array == null || array.length == 1) {
            return;
        }
        processArray(array, 0, array.length - 1);
    }

    /**
     * 递归实现归并排序
     *
     * @param array 目标数组
     * @param l     数组的左侧指针
     * @param r     数组的右侧指针
     */
    private void processArray(int[] array, int l, int r) {
        if (l == r) { //当左右指针相等时，只有一个元素，此时是有序的
            return;
        }
        int mid = (l + r) / 2;
        processArray(array, l, mid); //对左侧进行排序
        processArray(array, mid + 1, r); //对右侧进行排序
        mergerArray(array, l, r, mid); //合并左右两侧的数组
    }


    /**
     * 将数组左侧[有序l,mid] 和 右侧有序[mid+1,r]进行合并
     *
     * @param array 目标数组
     * @param l     数组的左侧指针
     * @param r     数组的右侧指针
     * @param mid   中间值
     */
    private void mergerArray(int[] array, int l, int r, int mid) {
        int[] help = new int[r - l + 1];
        int h_i = 0;
        int p1 = l, p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            help[h_i++] = array[p1] <= array[p2] ? array[p1++] : array[p2++];
        }
        while (p1 <= mid) { //右侧的数组先结束
            help[h_i++] = array[p1++];
        }
        while (p2 <= r) { //左侧的数组先结束
            help[h_i++] = array[p2++];
        }
       /* while (h_i > 0) {  //这样做容易出错
            array[r--] = help[--h_i];
        }*/
        // h_i 0,1,2,3, ... h_i-1
        IntStream.range(0, h_i).forEach(i -> array[l + i] = help[i]);
    }


    /**
     * 归并排序的非递归方式
     */
    @Test
    public void test2() {
        int[] array = {3, 2, 1, 5, 8, 6, 5, 4};
        mergeSorted2(array);
        System.out.println(Arrays.toString(array));
    }


    /**
     * 归并排序的非递归方式
     *
     * @param array 目标数组
     */
    private void mergeSorted2(int[] array) {
        if (array == null || array.length == 1) {
            return;
        }
        int L = 0, R = array.length - 1, mind = (L + R) / 2;
        int half = 1;
        //while (half <= mind) { //这是错误的  half <= mind不一定是循环退出的条件   例如数组长度是6 ，half = 4 不应该是
        while (half < array.length) { //必须是 一半的长度必须小于R,才可以继续循环，
            int subL = 0;
            while (subL < R) {
                int subMind = subL + half - 1;  //中间节点
                int length = 2 * half;
                int subR = subL + length - 1 < R ? subL + length - 1 : R;
                if (subMind >= subR) break;
                mergerArray(array, subL, subR, subMind);
                subL = subR + 1;
            }
            half *= 2;
        }
    }


    /**
     * 常见面试题深入理解一下归并排序的精髓
     * 在一个数组中，一个数左边比它小的数总和，叫做数的小和，所有数的小和加起来，称为数组小和
     * 例子[1,3,4,2,5]
     * 1 的左边比它小的数 ：没有
     * 3 的左边比3小的数  ：1
     * 4 的左边比4小的数  ：1 ，3
     * 2 的左边比2小的数  ：1
     * 5 的左边比5小的数  ：1,3,4,2
     * <p>
     * 方法一：进行暴力求解，遍历数组的每一位，然后求每一个数的小和，进行累加
     * <p>
     * 方法二：可以找到所有构成数组小和的数字(会发生重复)，而不是求得数组中每一位的小和进行累加  这是等价的；
     * 构成小和的数字会发生重复，其中重复的个数就是比这个数组大的个数；  ----  就是找到每个数比这个数大的个数，并且累加
     * <p>
     * 归并排序的数组的merger过程：
     * 1.如果左侧数组小于右侧数组的的数，那么左侧这个数字一定可以组成数组小和， right-当前index + 1
     * 2.如果左侧数组等于右侧数组的数，那么左侧数组的数自此时不能组成数组小和，需要将右侧数组数字copy到help数组
     * 3 如果左侧数组数字大于右侧数组数字，那么左侧的数组的数，不能组成数组小和，需要将左侧数字copy到help数组
     */

    @Test
    public void test3() {
        int[] array = {1, 3, 4, 2, 5};
        //array = new int[] {1,1,1,1,1,1,};
        System.out.println(getMinSumInArray(array));
    }


    private int getMinSumInArray(int[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }
        return getMinSumInArray(array, 0, array.length - 1);
    }

    private int getMinSumInArray(int[] array, int L, int R) {
        if (R == L) {
            return 0;
        }
        int mind = (L + R) / 2;
        int lMin = getMinSumInArray(array, L, mind);  //左侧数组的小和
        int rMin = getMinSumInArray(array, mind + 1, R); //右侧数组的小和
        int mergerMin = mergerMinSum(array, L, R, mind);  //合并成一个数组的小和
        return lMin + rMin + mergerMin;
    }


    /**
     * 获得合并时的最小和
     *
     * @param array 目标数组
     * @param L     左侧指针
     * @param R     右侧指针
     * @param mind  中间中间
     * @return 返回合并过程中的最小和
     */
    private int mergerMinSum(int[] array, int L, int R, int mind) {
        int result = 0;
        int[] help = new int[R - L + 1]; //将左右数组合并之后放入到help数组中
        int p1 = L, p2 = mind + 1, i_h = 0;
        while (p1 <= mind && p2 <= R) {
            if (array[p1] < array[p2]) {
                result += (array[p1] * (R - p2 + 1));
                help[i_h++] = array[p1++];
            } else {
                help[i_h++] = array[p2++];
            }
        }
        while (p1 <= mind) {
            help[i_h++] = array[p1++];
        }
        while (p2 <= R) {
            help[i_h++] = array[p2++];
        }
        IntStream.range(0, i_h).forEach(i -> array[L + i] = help[i]);  //将排序之后的数组放原数组
        return result;
    }

}
