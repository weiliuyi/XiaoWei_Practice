package example.algorithm.interview.day.pck.question;

import org.junit.Test;

/**
 * @ClassName MultiplePackage  多重背包问题
 * @Description https://www.kancloud.cn/kancloud/pack/70127
 * @Author weiliuyi
 * @Date 2021/4/25 3:42 下午
 **/
public class MultiplePackage {

    /**
     * 有n中物品，容量为v的背包，第i件物品最多有n[i],每件的费用c[i],价值为w[i],
     * 求解将哪些物品放入背包可以使这些物品总费用总和小于书包容量，并且使得价值最大；
     * <p>
     * 和完全背包的区别：对每件物品的数量有限制；
     * 状态转移方程为：
     * dpTable[i][j] = max(dpTable[i-1][j - k * c[i]] + k * w[i]
     * , dpTable[i-1][j]
     * );
     * <p>
     * 为什么无法使用第i层的子问题？dpTable[i][j] = dpTable[i][j-c[i]] + w[i]
     * 这是因为无法确定第i件物品还是否有剩余；
     */

    @Test
    public void testMultiplePackage() {
        int[] c = {1, 2, 3, 4};
        int[] w = {2, 4, 4, 5};
        int[] n = {3, 1, 3, 2};

        System.out.println(multiplePackage(6, w, c, n));
        System.out.println(multiplePackageV2(6, w, c, n));
    }


    /**
     * 基本解法
     */
    private int multiplePackage(int volume, int[] w, int[] c, int[] n) {
        int[] dpTable = new int[volume + 1];

        for (int i = 1; i <= w.length; i++) { //第i件物品

            for (int j = volume; j >= 1; j--) { //j 代表书包的容量
                for (int k = 1; k <= n[i - 1] && k <= j / c[i - 1]; k++) { //第i件物品的数量
                    dpTable[j] = Math.max(dpTable[j - k * c[i - 1]] + k * w[i - 1] //选取k件第i件商品
                            , dpTable[j] //不选第i件商品
                    );
                }
            }
        }

        return dpTable[volume];
    }


    /**
     * 转化为01背包的问题
     * 仍然使用二进制思想：把第i件物换成若干物品，使得原问题中第i中物品的可取的每种策略 ---
     * 0。。。n[i]  均能等价于取若干件代换以后的物品；另外取超过n[i]件的策略必不会出现；
     * <p>
     * 方法是：将第i件物品分成若干物品，其中每件物品又一个系数，这个物品的费用和价值均是原来的费用和价值乘以这个系数。
     * 使得这些系数分别为 1，2，4，。。。 2^(k-1),n[i]-2^k+1,且k满足 n[i]-2^k+1>0的最大整数。例如，n[i]为13，
     * 这个物品分成的系数分别为 1，2，4，6
     * 分成的这几件物品的系数和为n[i],表明不可能多余n[i]件的第i件物品；另外这种方法也能保证对于0。。。n[i]间的每一个整数，
     * 均可以使用这若干个数表示
     * <p>
     * 伪代码
     * procedure MultiplePack(cost,weight,amount)
     * if cost*amount>=V
     * CompletePack(cost,weight) //完全背包
     * return
     * integer k=1
     * while k<amount
     * ZeroOnePack(k*cost,k*weight) //01背包
     * amount=amount-k
     * k=k*2
     * ZeroOnePack(amount*cost,amount*weight)//01背包
     */

    private int multiplePackageV2(int volume, int[] w, int[] c, int[] n) {
        int[] dpTable = new int[volume + 1];
        for (int i = 1; i <= w.length; i++) {
            if (volume <= n[i - 1] * c[i - 1]) {
/*
                此时为转化为 完全背包问题
                for (int j = c[i - 1]; j <= volume; j++) {
                    dpTable[j] = Math.max(dpTable[j],
                            dpTable[j - c[i - 1]] + w[i - 1]);
                }
*/
                completePack(volume, c[i - 1], w[i - 1], dpTable);
            continue;
        }
        //多重背包问题
        int k = 1;
        int num = n[i - 1];
        while (k < num) {
/*
                0-1背包问题
                for (int j = volume; j >= k * c[i - 1]; j--) {
                    dpTable[j] = Math.max(dpTable[j], dpTable[j - k * c[i - 1]] + k * w[i - 1]);
                }
*/
            zeroOnePack(volume, c[i - 1] * k, w[i - 1] * k, dpTable);
            num -= k;
            k = k * 2;
        }
      /*  num - 2 ^ k + 1 进行01背包
            for (int j = volume; j >= num * c[i - 1]; j--) {
                dpTable[j] = Math.max(dpTable[j], dpTable[j - num * c[i - 1]] + num * w[i - 1]);
            }*/
        zeroOnePack(volume, c[i - 1] * num, w[i - 1] * num, dpTable);
    }

        return dpTable[volume];
}

    /**
     * 01背包问题
     */
    private void zeroOnePack(int volume, int cost, int value, int[] dpTable) {
        for (int j = volume; j >= cost; j--) {
            dpTable[j] = Math.max(dpTable[j], dpTable[j - cost] + value);
        }
    }

    /**
     * 完全背包问题
     */
    private void completePack(int volume, int cost, int value, int[] dpTable) {
        for (int j = cost; j <= volume; j++) {
            dpTable[j] = Math.max(dpTable[j - cost] + value, dpTable[j]);
        }
    }

}
