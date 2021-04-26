package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0425 最大连续子数组的和
 * @Description https://mp.weixin.qq.com/s/nrULqCsRsrPKi3Y-nUfnqg
 * @Author weiliuyi
 * @Date 2021/4/26 4:51 下午
 **/
public class Day0425 {

    /**
     * 滑动窗口算法是专门用来处理字串和子数组问题的，这里不就是子数组问题？
     * 但是这道题不能使用滑动窗口算法，因为数组中的数字可以为负数
     *
     * 滑动窗口算法无非就是双指针形成的窗口扫描整个数组或者字串，但是你的清楚的知道什么时候移动右指针扩大窗口，什么时间移动左指针来减少窗口；
     */



    /**
     * 给定一个整数数组arr，找到一个具有最大和的连续子数组(子数组至少包含一个元素)
     * <p>
     * 使用动态规划
     * dp[i] = num,前i个元素的最大的连续子数组的和为num
     * 有dp[i-1] 无法推户dp[i],这是因为这样定义可能导致子数组不连续
     * <p>
     * 重新定义dp数组
     * dp[i] = num :代表以arr[i]为结尾的子数组的最大和
     *
     *
     * 要么和和前面的子数组链接，形成一个和更大的的子数组，要么不与前面的子数组链接，自成一派
     * <p>
     * if (dp[i-1] > 0) { //如何dp[i-1] > 0 那么加上一个数arr[i]就可以构成 就可以构成以arr[i]为结尾的最大连续子数组
     * dp[i] = dp[i-1] + num[i]
     * } else {
     * dp[i] = num[i]
     * }
     */

    @Test
    public void testMaxSubsequence() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubsequence(arr));
    }



    private int maxSubsequence(int[] arr) {
        int[] dpTable = new int[arr.length];
        dpTable[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
// 方式一        if (dpTable[i-1] > 0) {
//                dpTable[i] = dpTable[i-1] + arr[i];
//            } else {
//                dpTable[i] = arr[i];
//            }
// 方式二         dpTable[i] = dpTable[i-1] >0 ? dpTable[i-1] + arr[i] : arr[i];
// 方式三  这三种方式都是等价的；
            dpTable[i] = Math.max(dpTable[i - 1] + arr[i], arr[i]);
        }
        return Arrays.stream(dpTable).max().getAsInt();
    }

}
