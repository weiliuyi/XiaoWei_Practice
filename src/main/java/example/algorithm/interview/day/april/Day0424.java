package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0424 0-1背包问题
 * @Description https://mp.weixin.qq.com/s/RXfnhSpVBmVneQjDSUSAVQ
 * 背包九讲   https://www.kancloud.cn/kancloud/pack/70126
 * @Author weiliuyi
 * @Date 2021/4/23 3:26 下午
 **/
public class Day0424 {

    /**
     * 书包可装载的重量为W
     * 背包里面的N个物品
     */
    @Test
    public void test() {
        int[] wt = {2, 1, 3};
        int[] val = {4, 2, 3};
        System.out.println(getPackageMaxValue(4, wt, val));

        System.out.println(getPackageMaxValueRecursive(4, wt, val, wt.length - 1));
    }

    /**
     * 状态：书包重量，选择物品
     * 选择：物品的价值列表
     * 明确dp[i][j] = num, 当书包重为i,选择第j个物品的物品的时候，最大价值为num
     * 详细说明：将前j件物品放入承重为i的书包中，若只考虑第i件物品的策略（放或者不放），就可以转化为只牵涉到前i-1个物品的问题；
     * 如果不放第j件，那么问题转化为： 前j-1件物品放入承重为i的背包 dp[i][j-1]
     * 如果放入第j件，那么问题转化为： 前j-1件物品放入承重为i-weight[j]的背包； dp[i-weight[j]][j-1] + value[j]
     *
     *
     * <p>
     * 0 , 1, 2, 3, 4, 5
     * 0
     * 1
     * 2
     * 3
     * <p>
     * <p>
     * 状态转移方程：
     * 选择第j个，不选择第j个
     * 选择第j个  dp[i-weight[j]][j-1] + value[j]
     * 不选择第j个 dp[i][j-1]
     *
     *
     *
     */

    private int getPackageMaxValue(int packNum, int[] weights, int[] values) {
        int[][] dpTable = new int[packNum + 1][values.length + 1];
        // weight[0][*] = 0;
        for (int i = 1; i <= packNum; i++) {
            for (int j = 1; j <= weights.length; j++) {
                //这个地方，这样处理的方式是错误的，可能会得出错误的结果
                //特例：第j个物品很重时，超过书包的容量，但是此时的书包容量为i的能装的最大价值不等于0；
                //if (i < weights[j-1]) continue;

                if (i < weights[j - 1]) { //此时说明书包容量装不下第j个物品，只能选择不装
                    dpTable[i][j] = dpTable[i][j - 1];
                    continue;
                }

                dpTable[i][j] = Math.max(
                        dpTable[i - weights[j - 1]][j - 1] + values[j - 1], //选择第i个物品
                        dpTable[i][j - 1]); // 不选择第i个物品

            }
        }
        return dpTable[packNum][weights.length];
    }


    /**
     * 使用递归实现0-1背包问题
     */
    private int getPackageMaxValueRecursive(int packNum, int[] weights, int[] values, int endIndex) {

        if (packNum <= 0) return 0;

        if (endIndex == 0) {
            if (packNum >= weights[endIndex]) {
                return values[endIndex];
            } else {
                return 0;
            }
        }

        if (weights[endIndex] > packNum) { //如果endIndex物品的重量大于packNum,只能不选择endIndex对应的物品
            return getPackageMaxValueRecursive(packNum, weights, values, endIndex - 1);
        }

        return Math.max(getPackageMaxValueRecursive(packNum, weights, values, endIndex - 1) //不选择endIndex对应的物品
                , getPackageMaxValueRecursive(packNum - weights[endIndex], weights, values, endIndex - 1) + values[endIndex]);
                    //选择endIndex对应的物品
    }

}
