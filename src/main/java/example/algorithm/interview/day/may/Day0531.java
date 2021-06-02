package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Day0531 前缀和
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/2 4:09 下午
 **/
public class Day0531 {

    /**
     * 前缀和主要适用的场景是原始数组不会被修改的情况下频繁查询某一个区间的累加和
     * <p>
     * <p>
     * 算法题：
     * 给定一个整数数组和一个整数k，你需要找到该数组中和为K的连续的子数组的个数；
     * <p>
     * 使用前缀和的思想：如何解决呢？
     */

    @Test
    public void testPrefixSum() {
        int[] arr = {1, 1, 1};
        ForceArraySequenceSum forceArraySum = new ForceArraySequenceSum();
        System.out.println(forceArraySum.getArraySequenceSum(arr, 2));

        System.out.println("----------------------");
        MyPrefixSumSequenceSum prefixArraySum = new MyPrefixSumSequenceSum();
        System.out.println(prefixArraySum.getArraySequenceSum(arr,2));

        System.out.println("-----------------------");
    }


    /**
     * 优化解法
     * for (int i = 1;i <= n;i++) {
     *      for(int j = 0;j < i;j++){
     *          if (prefix[i] - prefix[j] == k) {
     *              count++;
     *          }
     *      }
     * }
     * 翻译一下第二层for循环，计算有几个j能够使得sum[i]-sum[j]的差为k，每找到一个这样的j，就把结果加一；
     * 把if语句里的条件判断移向，这样写：
     * if (sum[j] == sum[i] - k) {}
     *
     * 优化思路：直接记录下有几个sum[j] 和sum[i]-k相等，直接更新结果，就避免了内层的for循环
     *
     */
    private static class FinalPrefixSumSequenceSum implements ArraySequenceSum {

        @Override
        public int getArraySequenceSum(int[] array, int target) {
            Map<Integer, Integer> prefixArraySumCountMap = new HashMap<>();  //map:前缀和 -> 前缀和出现的次数
            prefixArraySumCountMap.put(0,1);//
            int count = 0,sum=0;
            for (int j : array) {
                sum += j;
                //sum - target 这是我们要找的前缀和
                if (prefixArraySumCountMap.containsKey(sum - target)) { //如果前边有这个前缀和，直接更新结果
                    count += prefixArraySumCountMap.get(sum - target);
                }
                prefixArraySumCountMap.put(sum, prefixArraySumCountMap.getOrDefault(sum, 0) + 1); //把前缀和num[0...i]加入并且记录出现的次数；
            }
            return count;
        }
    }

    /**
     * 这种方式更不容易出错  时间复杂度为O(n^2)
     */
    private static class PrefixSumSequenceSum implements ArraySequenceSum {


        @Override
        public int getArraySequenceSum(int[] array, int target) {
            int count = 0;
            int[] prefixArray = new int[array.length + 1];
            for (int i = 0; i < array.length; i++) { //初始化前缀和数组
                prefixArray[i+1]  = prefixArray[i] + array[i];
            }
            for (int i = 1; i < prefixArray.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (prefixArray[i] - prefixArray[j] == target) {
                        count++;
                    }
                }
            }
            return count;
        }
    }


    //容易出错 很难理解
    private static class MyPrefixSumSequenceSum implements ArraySequenceSum {

        @Override
        public int getArraySequenceSum(int[] array, int target) {
            int count = 0;
            int[] prefixSumArr = getPrefixSumArr(array);
            for (int len = 1; len <= array.length; len++) {// 原数组的长度 1。。。。。。array.length len 1   0,1 len 2,0 2
                for (int i = 0; i < prefixSumArr.length - len; i++) {
                    if (prefixSumArr[i + len] - prefixSumArr[i] == target) {
                        count++;
                    }
                }
            }
            return count;
        }

        private int[] getPrefixSumArr(int[] arr) {
            int[] res = new int[arr.length + 1];
            for (int i = 0; i < arr.length; i++) {
                res[i + 1] = res[i] + arr[i];
            }
            return res;
        }

    }


    /**
     * 暴力求解 时间复杂度O(n^3)
     */
    private static class ForceArraySequenceSum implements ArraySequenceSum {

        @Override
        public int getArraySequenceSum(int[] array, int target) {
            int count = 0;
            for (int len = 1; len <= array.length; len++) {
                for (int i = 0; i < array.length - len + 1; i++) {
                    if (target == getSum(array, i, i + len - 1)) {
                        System.out.println("left_index = " + i + " right_index = " + (i + len - 1));
                        count++;
                    }
                }
            }
            return count;
        }

        private int getSum(int[] arr, int i, int j) {
            int sum = 0;
            for (int k = i; k <= j; k++) {
                sum += arr[k];
            }
            return sum;
        }
    }

    /**
     * 连续数组累加和接口类
     */
    private interface ArraySequenceSum {
        int getArraySequenceSum(int[] array, int target);
    }
}
