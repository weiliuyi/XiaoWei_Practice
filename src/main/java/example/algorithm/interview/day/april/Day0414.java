package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0414 动态规划之高楼扔鸡蛋
 * @Description https://mp.weixin.qq.com/s/xn4LjWfaKTPQeCXR0qDqZg
 * @Author weiliuyi
 * @Date 2021/4/14 10:44 上午
 **/
public class Day0414 {


    /**
     * 如何理解本题：最坏情况，至少需要？？？？
     * 最坏情况：鸡蛋破碎一定发生在搜索区间穷尽时（一定策略穷举所有情况）
     * 至少：要求最优的策略
     * <p>
     * https://mp.weixin.qq.com/s/CrXEZTc5mrL6gFzS1YXnpw
     * <p>
     * 逻辑中拆分出数学模型 状态 选择 穷举
     * 明确状态 当前拥有的鸡蛋数和需要测试的楼层数n；随着测试的进行，鸡蛋的个数减少，楼层的搜索范围会减小，这就是状态的变化；
     * 明确dp含义
     * 明确选择 ：其实就是选择哪层楼扔鸡蛋，不同的选择会造成状态的转移；
     * base case
     * <p>
     * 总结：肯定是个二维的dp数组或者带有两个状态参数的dp函数来表示状态的转移；
     * 外加一个for循环来遍历所有选择，择最优的选择更新结果；
     * 代码的框架：
     * def dp (K,N)
     * int res;
     * for 1 <= i <= N:
     * res = min(res,这次在第i层扔鸡蛋)
     * return res;
     */

    @Test
    public void testFloor() {
        int eggNum = 2;
        int floorNum = 101;
        System.out.println(superEggDrop(eggNum, floorNum, new int[eggNum + 1][floorNum + 1]));
    }

    /**
     * https://mp.weixin.qq.com/s/iMTXf_A4kW_272LehC4wvQ
     * 3层楼2个鸡蛋帮助理解
     * 效率比较低；
     *
     * dp[eggNum][floorNum] = num; eggNum各鸡蛋，在floorNum层楼，至少需要扔num次；
     */

    private int superEggDrop(int eggNum, int floorNum, int[][] memo) {
        if (eggNum == 1) return floorNum; //当只有一个鸡蛋的时候，只能一层一层的向上试

        if (floorNum == 0) return 0; //楼层为0时，需要试的次数为0；

        //使用memo来提升效率
        if (memo[eggNum][floorNum] != 0) return memo[eggNum][floorNum];

        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= floorNum; i++) {//穷举所有的可能
            result = Math.min( //至少需要
                    result, Math.max( //这一楼层最坏情况
                    superEggDrop(eggNum, floorNum - i, memo), //鸡蛋没有碎
                    superEggDrop(eggNum - 1, i - 1, memo) // 鸡蛋碎了
            ) + 1);
        }

        memo[eggNum][floorNum] = result;

        return result;
    }

    /**
     * 优化动态规划的效率：
     * https://mp.weixin.qq.com/s/7XPGKe7bMkwovH95cnhang
     * 1。二分查找搜索优化
     * 2。重新定义状态
     */





}
