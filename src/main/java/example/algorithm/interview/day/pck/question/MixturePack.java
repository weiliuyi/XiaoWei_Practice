package example.algorithm.interview.day.pck.question;

import org.junit.Test;

/**
 * @ClassName MixturePack 混合背包问题 01背包混合，完全背包混合，多重背包混合
 * @Description https://www.kancloud.cn/kancloud/pack/70128
 * @Author weiliuyi
 * @Date 2021/4/25 6:39 下午
 **/
public class MixturePack {


    /**
     * 混合背包的问题
     *
     *01背包和完全背包混合伪代码：
     * for i=1...N
     *      if 第i件物品是01背包
     *           for v=v...0
     *              dp[v] = max(dp[v],dp[v-c[i]]+w[i]);
     *      else if 第i件属于完全背包问题
     *           for v=0...v
     *              dp[v] = max(dp[v],dp[v-c[i]]+w[i])
     *
     * 01背包 完全背包 多重背包 混合的伪代码
     * for i=1...N
     *  if 第i件属于01背包
     *      zeroOnePack(c[i],w[i]);
     *  else if 第i件属于完全背包
     *      completePack(c[i],w[i]);
     *  else if 第i件属于多重背包
     *      multiplePack(c[i],w[i],n[i]);
     *
     */
    @Test
    public void testMixturePack () {

    }
}
