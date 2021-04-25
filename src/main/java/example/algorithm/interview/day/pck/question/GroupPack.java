package example.algorithm.interview.day.pck.question;

/**
 * @ClassName GroupPack 分组的背包问题
 * @Description https://www.kancloud.cn/kancloud/pack/70130
 * @Author weiliuyi
 * @Date 2021/4/25 7:48 下午
 **/
public class GroupPack {

    /**
     * 有N件物品，和一个容量为v的背包，第i件物品的费用为c[i],价值为w[i],这些物品被分成若干组，每组中的商品相互冲突，最多选择一件。
     * 求解将哪些物品放入背包使得这些物品的总费用不超过书包容量，且价值总和最大；
     * 算法思想：这个物品变成了每组物品有若干中策略，
     * 1。是选择本组的某一件，
     * 2。本组一件也不选
     * dp[k][v]:表示前k组物品在书包容量为v能取的的最大值；
     *
     *  状态转移方程：
     *  dp[k][v] = max (
     *  dp[k-1][v-c[i]] + w[i], //i：为第k商品组中，第i个商品
     *  dp[k-1][v]
     *  ) ;
     *
     *  伪代码：
     *  for 所有的分组k
     *      for v=V...0
     *          for 所有i属于组k
     *              dp[v]  = max(dp[v],
     *                  dp[v-c[i]] + w[i]);
     *
     *
     */


}
