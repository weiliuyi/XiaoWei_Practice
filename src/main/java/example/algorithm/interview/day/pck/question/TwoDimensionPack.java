package example.algorithm.interview.day.pck.question;

import org.junit.Test;

/**
 * @ClassName TwoDimensionPack 二维费用背包问题
 * @Description https://www.kancloud.cn/kancloud/pack/70129
 * @Author weiliuyi
 * @Date 2021/4/25 7:35 下午
 **/
public class TwoDimensionPack {
    /**
     * 二维背包问题：
     * 对于每件物品，具有两种不同的费用，选择这个物品必须付出两种代价，对于每种代价，都有一个可以付出的最大值（例如：书包容量）
     * 设这两种代价分别为代价a和代价b，第i个物品所需的两种代价分别为a[i]和b[i],两种代价可付出的最大值分别为v,u,物品的价值为w[i]；
     *
     * dp[i][v][u]表示前i件物品付出的代价分别为v和u时可获得最大价值；
     *
     * 状态转移方程为：
     * dp[i][v][u] = max(
     *  dp[i-1][v-a[i]][u-b[i]] + w[i], //选择第i件物品
     *  dp[i-1][u][v]  //不选择第i件物品
     * );
     *
     *
     */

    @Test
    public void testTwoDimensionPack () {

    }
}
