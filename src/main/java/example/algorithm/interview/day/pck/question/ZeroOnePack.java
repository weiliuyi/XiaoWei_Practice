package example.algorithm.interview.day.pck.question;

import org.junit.Test;

/**
 * @ClassName BaseQuestion 0-1 背包的基础问题
 * @Description https://www.kancloud.cn/kancloud/pack/70125 （背包九讲）
 * @Author weiliuyi
 * @Date 2021/4/23 8:08 下午
 **/
public class ZeroOnePack {

    /**
     * 题目：有N件物品，一个容量为V的背包，第i件物品的费用c[i],价值是w[i],求解讲哪些物品放入背包，可以使得总的价值最大；
     * <p>
     * 最基础的问题：每种物品只有一件，可以选择放或者不放
     * dp数组的定义：
     * f[i][v]:前i件物品放入到容量为v的背包可以获得最大价值为f[i][v];
     * 状态转移方程为：
     * f[i][v] = max (f[i-1][v-c[j]] + w[i],    //选择第i件物品
     * f[i-1][v]               //不选择第i件物品
     * );
     * f[i][v] = max(选择第i件物品，不选择第i件物品);
     * <p>
     * 详细解释：将前i件物品放入到容量为v的背包中，
     * 若只考虑第i件物品的策略（放或者不放），都可以转化为一个之牵扯i-1件物品的问题。
     * 如果不放入第i件物品，那么就转化为前i-1件物品放入容量为v的背包中 即求解dp[i-1][v]
     * 如果放入第i件物品，按么就转化为前i-1件物品放入容量为v-c[i]的背包中,即求解dp[i-1][v-c[i]]
     */


    @Test
    public void testZeroOnePck() {
        int[] wt = {2, 1, 3};
        int[] val = {4, 2, 3};
        System.out.println(baseZeroOnePck(4, val, wt));
        System.out.println(baseZeroOnePckV2(4, val, wt));
        System.out.println(baseZeroOnePckSpace(4, val, wt));


    }

    /**
     * 0-1 背包问题求解
     * 外循环：书包容量
     * 内循环：物品
     * 书包容量一定时，前i物品的，书包的容积的最大价值
     */
    private int baseZeroOnePck(int volume, int[] w, int[] c) {
        int[][] dpTable = new int[w.length + 1][volume + 1];
        //base case dpTable[0][*] = 0,没有物品，无论书包的容量多大，都为0
        //dpTable[*][0],书包的容量为0，无论有多少个物品，书包的最大价值都是0
        for (int v = 1; v <= volume; v++) {
            for (int i = 1; i <= c.length; i++) {
                if (v < c[i - 1]) { //当物品的体积大于书包的体积时，只有一种选择：那就是不选第i件物品；
                    dpTable[i][v] = dpTable[i - 1][v];
                } else {
                    dpTable[i][v] = Math.max(dpTable[i - 1][v - c[i - 1]] + w[i - 1] //选择第i件物品
                            , dpTable[i - 1][v]); //不选择第i件物品；
                }
            }
        }
        return dpTable[c.length][volume];
    }

    /**
     * 交换遍历顺序
     * 外循环：物品
     * 内循环：书包的体积
     * <p>
     * 当物品的数量一定时，不同书包体积，能够装载的最大价值；
     */
    private int baseZeroOnePckV2(int volume, int[] w, int[] c) {
        int[][] dpTable = new int[w.length + 1][volume + 1];
        //dp[0][*] = 0 dp[*][0] = 0;
        for (int i = 1; i <= w.length; i++) {//i代表第几个物品
            for (int j = 1; j <= volume; j++) { //j代表书包的容量
                if (j < c[i - 1]) {
                    dpTable[i][j] = dpTable[i - 1][j];
                } else {
                    dpTable[i][j] = Math.max(
                            dpTable[i - 1][j - c[i - 1]] + w[i - 1]
                            , dpTable[i - 1][j]);
                }
            }
        }
        return dpTable[c.length][volume];
    }


    /**
     * 优化空间复杂度
     * 外循环：i 第i件物品
     * 内循环：j 书包的体积为j
     * 第i次循环结束之后，使用一个数组dp[0....v] 表示dp[i][0...v];
     * dp[i][v]是由dp[i-1][v]和dp[i-1][v-c[i]]子问题递推出来的；如何得到dp[i-1][v] 和dp[i-1][v-c[i]] ???
     * 只需要从v。。。0的顺序推dp[v]即可；
     * 这样才能保证在递推dp[v]时，未更新前的dp[v]和dp[v-c[i]] 保存的状态是dp[i-1][v] dp[i-1][v-c[i]] + w[i]
     */

    private int baseZeroOnePckSpace(int volume, int[] w, int[] c) {
        int[] dpTable = new int[volume + 1];

        for (int i = 1; i <= w.length; i++) {
            //for (int j = volume; j >= 1; j--) {
//            if (j < c[i - 1]) continue;
//                if (j < c[i-1]) { 当书包的容积小于 第i物品的重量时，只能选择不放入第i物品此时dp[i][j] = dp[i-1][j] ====> dp[j] = dp[j]
//                    dpTable[j] = dpTable[j];
//                }
            for (int j = volume;j >= c[i-1];j--) { //如果 j < c[i-1] dp[j] 不需要更新
                dpTable[j] = Math.max(dpTable[j - c[i - 1]] + w[i - 1]
                        , dpTable[j]
                );
            }

        }
        return dpTable[volume];
    }


}
