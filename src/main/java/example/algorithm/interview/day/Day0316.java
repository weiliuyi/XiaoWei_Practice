package example.algorithm.interview.day;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description: 动态规划---框架思维,凑零钱
 * 动态规划，一般是求最值的问题
 * @author: weiliuyi
 * @create: 2021--16 18:08
 **/
public class Day0316 {

    /**
     * 斐波那契数列 --- 重叠子问题
     * 1.暴力递归
     * 2.使用备忘录优化递归---自顶而下
     * 3.使用动态规划---------字底向上
     * 4.优化动态规划---时间复杂度O(1)
     */
    @Test
    public void testFibo() {
        System.out.println(fiboForce(5));
        System.out.println(fiboMemo(5,new int[6]));
        System.out.println(fiboDynamic(5));
        System.out.println(fiboDynamicOptimize(5));
    }


    /**
     * 凑零钱 --- 最优子结构
     * <p>
     * 给你k种面值的硬币，面值分别为c1, c2 ... ck，每种硬币的数量无限，
     * 再给一个总金额amount，问你最少需要几枚硬币凑出这个金额，如果不可能凑出，算法返回 -1 。算法的函数签名如下
     * <p>
     * 1.递归解法
     * 2.动态规划
     */
    @Test
    public void testCollectCoin() {
        int[] ints = new int[10];
        int[] coinOrder = {2, 3};
        Arrays.sort(coinOrder);
        collectCoins(coinOrder, 1, 6, ints);
        System.out.println(Arrays.toString(ints));
    }


    /**
     * 暴力求解
     */

    private int fiboForce (int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fiboForce(n-1) + fiboForce(n-2);
    }


    /**
     * 带备忘录的递归解法------- 重叠子问题
     */

    private int fiboMemo (int n,int[] memo) {
        if (n == 1 || n == 2) {
            return 1;
        }
        if (memo[n] == 0)
            memo[n] = fiboForce(n - 1) + fiboForce(n - 2);
        return memo[n];
    }

    /**
     * 动态规划求解
     */

    private int fiboDynamic(int n) {
        int[] dpTable = new int[n + 1];
        /**
         * 初始化dp数组
         */
        dpTable[1] = 1;
        dpTable[2] = 1;

        for (int i = 3; i <= n; i++) {
            dpTable[i] = dpTable[i - 1] + dpTable[i - 2];
        }
        return dpTable[n];
    }

    /**
     * 优化动态规划求解
     * dp[n]的值只与dp[n-1] 和dp[n-2]相关，所以只与它的前两个状态有关
     * 空间复杂度为O(1)
     *
     * @param n
     * @return
     */
    private int fiboDynamicOptimize(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int before = 1;
        int current = 1;
        for (int i = 3; i <= n; i++) {
            int temp = before + current;
            before = current;
            current = temp;
        }
        return before;
    }


    /**
     * 凑零钱的   递归解法
     *
     * @param orderCoins 硬币面值列表,并且面值大小，按照从小到大进行排序
     * @param endIndex   遍历硬币列表的默认指针
     * @param targetNum  目标总额
     * @param coinCount  硬币字典
     * @return 是否能够凑成 targetNum
     */
    private boolean collectCoins(int[] orderCoins, int endIndex, int targetNum, int[] coinCount) {
        if (targetNum == 0) {
            return true;
        }
        //从后向前进行遍历 这是因为orderCoins从小到大的顺序
        for (int i = endIndex; i >= 0; i--) {
            //面值大的尽可能的多选择
            while (targetNum >= 0 && targetNum >= orderCoins[i]) {
                coinCount[orderCoins[i]]++;
                targetNum -= orderCoins[i];
            }
            //递归调用
            boolean r1 = collectCoins(orderCoins, i - 1, targetNum, coinCount);
            if (r1) {
                return true;
            }
            //当前面值不能凑到targetNum,撤销一个orderCoins[i] 再次进行递归
            while (coinCount[orderCoins[i]] > 0) {
                coinCount[orderCoins[i]]--;
                targetNum += orderCoins[i];
                boolean r2 = collectCoins(orderCoins, i - 1, targetNum, coinCount);
                if (r2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] ints = new int[4];
        ints[0] = 4;
        ints[1] = 1;
        ints[2] = 3;
        ints[3] = 7;
        Arrays.sort(ints);
        System.out.println(Arrays.toString(ints));
    }

}
