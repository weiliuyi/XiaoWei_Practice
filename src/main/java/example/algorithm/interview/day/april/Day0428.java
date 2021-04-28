package example.algorithm.interview.day.april;

/**
 * @ClassName Day0428 动态规划的状态压缩
 * @Description https://mp.weixin.qq.com/s/SnyN1Gn6DTLm0uJyp5l6CQ
 * @Author weiliuyi
 * @Date 2021/4/27 7:05 下午
 **/
public class Day0428 {

    /**
     * 动态规划的状态压缩
     *
     * 1。能够使用状态压缩的动态规划都是二维dp问题，如果计算状态dp[i][j],都是需要dp[i][j]的相邻状态，那么可以使用状态压缩技巧；
     *
     * 如何压缩呢？ 就是将二维转化为一维；空间复杂度O(n^2)---> O(n)
     * 什么是dp[i][j]的相邻状态？
     *  示例最长回文子序列：
     *      dp[i][j] = dp[i+1][j-1] + 2
     *      dp[i][j] = max(dp[i+1][j],dp[i][j-1]);
     *
     *      相对位置：
     *      dp[i][j-1]   dp[i][j]
     *      dp[i+1][j-1] dp[i+1][j]
     *
     * 状态压缩的核心：将二维转化为一维
     *
     * 投影
     *
     */
}
