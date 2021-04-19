package example.algorithm.interview.day.march;

import org.junit.Test;

import java.util.Arrays;

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
        System.out.println(fiboMemo(5, new int[6]));
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

        System.out.println(collectCoinsVersion2(6, coinOrder));
        int[] memo = new int[7];
        Arrays.fill(memo, 7);

        System.out.println(collectCoinVersion3(7, 6, memo, coinOrder));

        System.out.println(coinChange(7, coinOrder));
    }


    /**
     * 暴力求解
     */

    private int fiboForce(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fiboForce(n - 1) + fiboForce(n - 2);
    }


    /**
     * 带备忘录的递归解法------- 重叠子问题
     */

    private int fiboMemo(int n, int[] memo) {
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
     * 贪心算法
     * <p>
     * 这种递归的思路：面值比较大的的尽可能多的选择，然后在选择面值低的硬币
     * 如果无法凑成金额，减去一个面值大的，再去选择小面值的硬币
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

    /**
     * 递归版本二
     * <p>
     * <p>
     * 明确状态 ---- 定义dp数组/函数的含义------明确选择-------明确basecase
     * 1。明确状态----  硬币的数量是不限制的，那么唯一的状态就是目标金额
     * 2。dp函数的定义----- 函数dp(n)表示 当前目标金额n，至少需要dp(n)个金额凑出金额
     * 3。确定选择并且择优  每个硬币不限制数量，所以选择的范围就是  硬币面值的集合
     * 4。最后明确base case 目标金额小于零时，所需硬币的数量为零，当目标金额小于零时，返回-1
     *
     * @param n
     */
    public int collectCoinsVersion2(int n, int[] coins) {
        if (n == 0) return 0;
        if (n < 0) return -1;
        int result = Integer.MAX_VALUE;
        for (int i = 1; i < coins.length; i++) {
            int temp = collectCoinsVersion2(n - coins[i], coins);
            if (temp == -1) {
                continue;
            }
            result = min(result, temp + 1);
        }
        return result;
    }

    /**
     * 使用备忘录的进行优化递归，对递归树进行剪纸，
     * 子问题的数量---就是递归树节点的个数
     *
     * @param
     * @param target 目标金额
     * @param memo   备忘录
     * @param coins  硬币的面值列表
     * @return 最少需要硬币的数量
     */
    public int collectCoinVersion3(int max, int target, int[] memo, int[] coins) {
        if (target == 0) return 0;
        if (target < 0) return -1;
        //进行剪枝
        //if (memo[target] != max) return memo[target];
        int result = max;
        for (int i = 0; i < coins.length; i++) {
            //必须判断target-coins[i],否则会发生数组角标越界
            if (target - coins[i] < 0 || memo[target - coins[i]] == -1) {
                continue;
            }
            //使用memo进行剪纸
            if (memo[target - coins[i]] == max) {
                memo[target - coins[i]] = collectCoinVersion3(max, target - coins[i], memo, coins);
            }

            result = min(result, memo[target - coins[i]] + 1);
        }
        return result;

    }

    /**
     * 使用dp数组进行 求解换零钱问题
     *
     * @param target 目标金额
     * @param coins  硬币列表
     */
    private int coinChange(int target, int[] coins) {
        //dp[n] = count 目标金额为n时，最少需要count个硬币
        int invalid = target + 1;
        int[] dpTable = new int[target + 1];
        dpTable[0] = 0;

        for (int i = 1; i <= target; i++) {
            int count = invalid;
            for (int coin : coins) {
                if (i - coin < 0) continue;
                count = Math.min(count, dpTable[i - coin] + 1);
            }
            dpTable[i] = count;
        }
        return dpTable[target] == invalid ? -1 : dpTable[target];
    }


    private int min(int a, int b) {
        return Math.min(a, b);
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
