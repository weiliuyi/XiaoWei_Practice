package example.algorithm.interview.day.pck.question;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName CompletePack 完全背包问题
 * @Description https://www.kancloud.cn/kancloud/pack/70126 背包九讲
 *              https://blog.csdn.net/siyu1993/article/details/52858940 完全背包讲解
 * @Author weiliuyi
 * @Date 2021/4/25 11:01 上午
 **/
public class CompletePack {

    /*
     * 有n件物品，容量为v的背包，每种物品有无限件可用，第i件物品的费用为c[i]，价值为v[i],
     * 求解将那些物品放入到书包中可以使得物品的总费用总和不超过背包的容量，且价值总和
     * <p>
     * 和0-1背包的区别就在于：0-1背包每件物品只有一件，完全背包对于每件物品的数量没有限制；
     * 思路解析：0-1背包问题对于第i件物品，只有两种选择，要么选择，要么不选择；
     * 对于完全背包问题来说： 有取0件，取1件，取2件，。。。。等很多种，按照01背包的思路，dp[i][j]表示前i种物品恰放入容量为j的背包的最大权值；
     * 仍然可以按照每种物品不同策略写出状态转移方程，像这样：
     * dp[i][j] = max(
     * dp[i-1][v - k *c[i]] + k * w[i]  | 0 <= k * c[i] <= v
     * );
     */

    /**
     * 对于二维数组实现的01背包问题，第二层循环（遍历背包容量）可以正序，也可以逆序。
     * 一维数组的01背包问题，第二层循环必须逆序。
     * 对于完全背包问题，无论二维还是一维数组实现，都必须正序。
     */

    @Test
    public void testCompletePack() {
        int[] c = {3, 4, 5, 3, 6};
        int[] w = {4, 5, 6, 3, 5};
        System.out.println(completePackBase(10, w, c));
        System.out.println(completePackageV2(10, w, c));

        System.out.println(completePackageV3(10, w, c));

        System.out.println(completePackageV4(10, w, c));
    }

    /**
     * 常规思路：
     * 状态转移方程：
     * dp[i][j] = max (
     * max(dp[i-1][j- k *c[i]] + k * w[i] ) // 0 <= k <= j / w[i]
     * );
     */
    private int completePackBase(int volume, int[] w, int[] c) {
        int[] dpTable = new int[volume + 1];
        for (int i = 1; i <= w.length; i++) {
            for (int j = volume; j >= w[i - 1]; j--) {

                for (int k = 1; k <= j / c[i - 1]; k++) { //不选择时，dp[i]，选择 1件 2件，3件。。。。。
                    dpTable[j] = Math.max(dpTable[j],
                            dpTable[j - k * c[i - 1]] + k * w[i - 1]);
                }
            }
        }
        return dpTable[volume];
    }


    /**
     * 转化成01背包问题---将一个物品拆成多个物品
     * 思想：考虑到第i件物品最多选择v/c[i]件，于是可以将第i件物品转化成v/c[i]件费用以及价值不变的物品，然后求解01背包问题
     * <p>
     * 更加高效的转化方法，二进制的思想，因为不管最优策略选择几件第i件物品，总是可以表示成若干个2^k件物品的和。
     * 具体操作：把第i件物品拆成c[i]*2^k,价值w[i]*2^k的若干物品，其中k满足 c[i]*2^k件物品的和，这样把每件物品拆成 log(v/c[i])
     */

    private int completePackageV2(int volume, int[] w, int[] c) {
        int[] nw = new int[w.length * volume];
        int[] nc = new int[c.length * volume];
        int count = 0;
        //把第i物品转化为v/c[i]件费用以及价值相等的物品
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < volume / c[i]; j++) {
                nc[count] = c[i];
                nw[count] = w[i];
                count++;
            }
        }
        //按照01背包的问题进行求解；
        return zeroOnePackBase(volume, nw, nc);
    }

    /**
     * 拆成幂的形式
     */
    private int completePackageV3(int volume, int[] w, int[] c) {
        int[] nw = new int[w.length * volume];
        int[] nc = new int[nw.length];
        int count = 0;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j <= log(volume / c[i], 2); j++) {
                nw[count] = (int) Math.pow(2, j) * w[i];
                nc[count] = (int) Math.pow(2, j) * c[i];
                count++;
            }
        }
        return zeroOnePackBase(volume, nw, nc);
    }

    /**
     * 转换新的思路：
     * dp[i][j] = max (
     * dp[i-1][j - k*c[i]] + k * w[i] // 选取一件 2件 3件。。。。
     * ,  dp[i-1][j]
     * );
     * 求解dp[i][j]的过程中，用到若干dp[i-1][0...V],这个方程的核心在于若要求解dp[i][j]必须遍历第i-1层中需要的部分dp[i-1][j-k*c[i]],
     * 找出该子问题的基础上，k取何值所取得dp[i][j]最大；
     * <p>
     * 存在的问题：
     * 对于求解dp[i][j]，我们仅仅利用了i-1层的数据dp[i-1][v-k*c[i]],忽略了dp[i][0....v-1]
     * <p>
     * 优化方向：dp[i][j]之前的子问题包括dp[i-1][j - k * c[i]]和dp[i][0..v-1],这些子问题都是正确的结果，
     * 把第i层的子问题应用到状态转移方程中去；
     * <p>
     * dp[i][0...v-1]和dp[i][v]的共同点在于，他们都包涵物品i，所以考虑的问题转化为"每次添加一个物品i的状态转移函数"
     * 1。如果dp[i][v]包含物品i，那么dp[i][v-c[i]]的物品数量一定比dp[i][v]少1，dp[i][j] = dp[i][j-c[i]] + w[i]
     * 2。如果dp[i][v]不包含物品i，那么dp[i][j] = dp[i-1][j]
     * <p>
     * 新的状态转移方程为：
     * dp[i][j] = max (dp[i][j - c[i]] + w[i]
     * , dp[i-1][j]
     * );
     */


    private int completePackageV4(int volume, int[] w, int[] c) {
        int[] dpTable = new int[volume + 1];

        for (int i = 1; i <= w.length; i++) {
            for (int j = c[i - 1]; j <= volume; j++) {
                dpTable[j] = Math.max(dpTable[j - c[i - 1]] + w[i - 1],
                        dpTable[j]);
            }
        }
        return dpTable[volume];
    }


    /**
     * 最基本的01背包问题
     */
    private int zeroOnePackBase(int volume, int[] w, int[] c) {
        //0-1基础问题；
        int[] dpTable = new int[volume + 1];
        for (int i = 1; i <= c.length; i++) {
            for (int j = volume; j >= c[i - 1]; j--) {
                dpTable[j] = Math.max(dpTable[j - c[i - 1]] + w[i - 1]
                        , dpTable[j]
                );
            }
        }
        System.out.println(Arrays.toString(dpTable));
        return dpTable[volume];
    }

    private static int log(int value, int base) {
        return (int) (Math.log(value) / Math.log(base));
    }

    public static void main(String[] args) {
        System.out.println(log(7, 2));
        System.out.println(2 ^ 3);
    }

}
