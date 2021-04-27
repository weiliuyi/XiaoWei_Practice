package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0427 动态规划 和 回溯算法
 * @Description https://mp.weixin.qq.com/s/OyqQXQnyH_UzmxdytK2rTA
 * @Author weiliuyi
 * @Date 2021/4/27 2:38 下午
 **/
public class Day0427 {


    /**
     * 目标和
     * 给定一个非负的整数数组，a1,a2,a3,...an和一个目标数 S，现在你有两个符号 + 和-，
     * 对于数组中的任意一个整数，你都可以从 + 或者 - 选择一个符号增加在前面；
     * <p>
     * 返回可以使最终数组和为目标数S的所有添加符号的方法数；
     */

    @Test
    public void testTargetSum() {
        int[] num = {1, 1, 1, 1, 1};
        int s = 3;
        System.out.println(targetSumBackTrace(num, num.length - 1, s));

        //System.out.println(targetSumDynamic(num, 3));

        System.out.println(findTargetSumWays(num,3));
    }


    /**
     * 分割等和子集
     * 给定一个只包含正整数的非空数组，是否可以将这个数组分割成两个子集，使得两个子集的元素和相等
     *
     * 思路分析：1。能够分成两个子集，并且子集和相等，sum一定是二的倍数
     *         2。 原问题可以等价于，数组中能否凑成 sum / 2;
     * 状态:数组中前i个元素，目标拼成 j
     * dp含义：dp[i][j] = num 数组前i个元素凑成j方法数为num
     *
     * 状态转移方程：dp[i][j] = dp[i-1][j] + dp[i-1][j-arr[i]]
     *
     * base case : dp[0][*] = 0; dp[*][0] = 1;
     *
     * 将二维数组进行一位数组压缩：
     * j ---> arr[i]
     * dp[j] = dp[j] + dp[j-arr[i]]
     */
    @Test
    public void testCanPartition () {
        int[] arr = {1,5,11,5};
        System.out.println(canPartition(arr));

        int[] arr2 = {1,2,3,5};
        System.out.println(canPartition(arr2));


    }


    /**
     * 回溯算法
     */
    private int targetSumBackTrace(int[] arr, int endIndex, int target) {
        if (endIndex == -1 && target == 0) return 1;

        //if (endIndex < 0 || target <0)  return 0; 这个逻辑是错误的，
        // 当target为负数的时不是终止条件，后面的数组也可以通过添加-

        if (endIndex < 0) return 0;// endIndex小于0，但是 target > 0 或者 target < 0

        int addNum = targetSumBackTrace(arr, endIndex - 1, target - arr[endIndex]); //添加加号

        int minusNum = targetSumBackTrace(arr, endIndex - 1, target + arr[endIndex]);//添加减号
        return addNum + minusNum;
    }

    /**
     * 动态规划
     * 错误思路
     * 状态 数组角标长度i，和目标和s
     * dp[i][j] = num ：前i个元素凑到目标j的方法数为num
     * <p>
     * 状态转移方程：
     * dp[i][j] = dp[i-1][j-arr[i]] + dp[j+arr[i]]
     * 状态方程存在问题 j+arr[i] 有可能超过target,并且这个target不容易确定
     * <p>
     * 目标求解：dp[arr.length][s]
     * <p>
     * base case dp[0][0] = 1; dp[0][*] = -1;
     */

    private int targetSumDynamic(int[] arr, int target) {
        //int sum = Arrays.stream(arr).sum();
        int[][] dpTable = new int[arr.length + 1][target + 1];
        //base case
        dpTable[0][0] = 1;
        for (int i = 1; i <= target; i++) {
            dpTable[0][i] = 0;
        }

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                if (j - arr[i - 1] < 0) {
                    dpTable[i][j] = dpTable[i - 1][j + arr[i - 1]];  //存在问题
                    continue;
                }
                dpTable[i][j] = dpTable[i - 1][j + arr[i - 1]] + dpTable[j - 1][j - arr[i - 1]]; //存在问题
            }
        }
        return dpTable[arr.length][target];
    }


    /**
     * 动态规划正确版本：转化问题的形式
     * 将arr划分成两个自己A和B，分别代表着 + 的数和 - 的数；他们和target存在一下关系：
     *
     * sum(A) - sum(B) = target
     * sum(A) = target + sum(B)  两边同时加上sum(A)
     * sum(A) + sum(A) = sum(A) + target + sum(B)
     * 2*sum(A) = target + sum(arr)
     * 可以推出：
     *      sum(A) = (target + sum(arr)) / 2;
     *
     * 原问题就转化为arr中存在几个子集A，使得A中元素和为(target + sum(arr)) / 2
     */


    /**
     * 数组A的子集元素和为target的个数；
     */

    int findTargetSumWays(int[] num, int target) {
        int sum = Arrays.stream(num).sum();
        if (sum < target || (sum + target) % 2 == 1) {
            return 0;
        }
        //return subSetsBackTrack(num,(sum + target) / 2,num.length-1);
        return subSetsDynamic(num,(sum+target)/2);
    }


    /**
     * 回溯算法版本
     */
    int subSetsBackTrack(int[] num, int target, int endIndex) {
        if (target == 0) return 1;

        if (endIndex < 0) return 0;

        return subSetsBackTrack(num, target, endIndex - 1) + subSetsBackTrack(num, target - num[endIndex], endIndex - 1);
    }

    /**
     * 状态：数组的角标，target
     * <p>
     * dp含义：dp[i][j] = num; 前i个元素恰好凑出j的子集的数量为num
     * <p>
     * 状态转移方程：
     * dp[i][j] = dp[i-1][j] + dp[i-1][j-arr[i]]
     * <p>
     * base case
     * dp[0][0] = 1; 0个元素，抽出目标为0的方法数为 1
     * dp[0][*] = -1; 0个元素，凑出目标*的方法数为-1（不存在）无解
     * <p>
     * <p>
     * dp[*][0] = 1;  *个元素，凑出目标0的方法数为1 （一个都不全）
     */

    int subSetsDynamic(int[] arr, int target) {
        int[][] dpTable = new int[arr.length + 1][target + 1];
        //base case
        dpTable[0][0] = 1;
        for (int i = 1; i <= target; i++) {
            dpTable[0][i] = -1;
        }
        for (int i = 1; i <= arr.length; i++) {
            dpTable[i][0] = 1;
        }

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (j - arr[i - 1] < 0) {
                    dpTable[i][j] = dpTable[i - 1][j];
                } else {
                    if (dpTable[i - 1][j] == -1 && dpTable[i - 1][j - arr[i - 1]] == -1) {
                        dpTable[i][j] = -1;
                    } else {
                        dpTable[i][j] = (dpTable[i - 1][j] == -1 ? 0 : dpTable[i - 1][j]) +
                                (dpTable[i - 1][j - arr[i - 1]] == -1 ? 0 : dpTable[i - 1][j - arr[i - 1]]);
                    }
                }
            }
        }
        return dpTable[arr.length][target];
    }

    /**
     * dp[0][*] = -1; 0个元素，凑出目标*的方法数为-1（不存在）无解
     * 用-1表示会增加程序的复杂性；
     * 使用0表示无解就可以，可以简化计算；
     */
    private int subSetsDynamicV2(int[] arr, int target) {
        int[][] dpTable = new int[arr.length + 1][target + 1];
        //base case
        for (int i = 0; i < arr.length; i++) {
            dpTable[i][0] = 1;
        }

        for (int i = 1; i <= arr.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (j - arr[i - 1] < 0) {
                    dpTable[i][j] = dpTable[i - 1][j];
                } else {
                    dpTable[i][j] = dpTable[i - 1][j] + dpTable[i - 1][j - arr[i - 1]];
                }
            }
        }
        return dpTable[arr.length][target];
    }


    /**
     * 分割等和子集
     *
     *
     */
    private boolean canPartition (int[] arr) {
        int sum = Arrays.stream(arr).sum();
        if ( sum % 2 == 1) {
            return false;
        }
        int target = sum / 2;
        int[] dpTable = new int[target + 1];
        dpTable[0] = 1;

        for (int i = 1; i <= arr.length ; i++) {
            for (int j = target; j >= arr[i-1]; j--) {
                dpTable[j] = dpTable[j] + dpTable[j-arr[i-1]];
            }
        }
        return dpTable[target] > 0;
    }


}
