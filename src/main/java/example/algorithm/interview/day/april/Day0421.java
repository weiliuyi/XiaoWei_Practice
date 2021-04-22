package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0421 打家劫舍系列问题
 * @Description https://mp.weixin.qq.com/s/z44hk0MW14_mAQd7988mfw
 * @Author weiliuyi
 * @Date 2021/4/21 5:30 下午
 **/
public class Day0421 {


    @Test
    public void testHouseRobber() {
        int[] coins = new int[]{2, 7, 9, 3, 1};
        System.out.println(robCoinRecursive(coins, 0));

        int[] coinsV2 = new int[]{1, 2, 3, 1};
        System.out.println(robCoinRecursive(coinsV2, 0));

        System.out.println(robCoinDynamic(coins));
        System.out.println(robCoinDynamic(coinsV2));

        System.out.println(robCoinDynamicV2(coins));
        System.out.println(robCoinDynamicV2(coinsV2));

    }

    /**
     * 环形房子
     */
    @Test
    public void testHouseRobberCycle() {
        int[] coins = new int[]{2, 3, 2};
        System.out.println(robCoinRecursiveCycle(coins));
    }

    /**
     * 树形房子
     * 下标为 i的
     * left child: 2 * i + 1
     * right child : 2 * i + 2
     */
    @Test
    public void testHouseRobberTree() {
        int[] coinTree = new int[]{3, 2, 3, -1, 3, -1, 1};
        System.out.println(robCoinRecursiveTree(coinTree, 0));

        int[] coinTreeV2 = new int[]{3,4,5,1,3,-1,1};
        System.out.println(robCoinRecursiveTree(coinTreeV2,0));
    }

    private int robCoinRecursive(int[] coins, int start) {
        if (start >= coins.length) return 0;
        return Math.max(
                coins[start] + robCoinRecursive(coins, start + 2), //抢劫coins[start]
                robCoinRecursive(coins, start + 1)); //不抢劫coins[start]

    }

    /**
     * 定义dp数组的含义
     * dp[i] = coinNum,以数组coins的i下标为结尾，最大抢劫的金额；
     */
    private int robCoinDynamic(int[] coins) {
        int[] dpTable = new int[coins.length];
        dpTable[0] = coins[0];
        for (int i = 1; i < coins.length; i++) {
            dpTable[i] = Math.max(
                    coins[i] +
                            (i >= 2 ? dpTable[i - 2] : 0), //抢了第i个

                    dpTable[i - 1]); //不抢第i个
        }
        return Arrays.stream(dpTable).max().getAsInt();
    }

    private int robCoinDynamicV2(int[] coins) {
        int[] dpTable = new int[coins.length + 2];

        for (int i = coins.length - 1; i >= 0; i--) {
            dpTable[i] = Math.max(coins[i] + dpTable[i + 2],
                    dpTable[i + 1]);
        }

        return Arrays.stream(dpTable).max().getAsInt();
    }

    /**
     * 循环coins求解
     */
    private int robCoinRecursiveCycle(int[] coins) {
        return Math.max(
                robCoinRecursiveCycle(coins, 0, coins.length - 2),
                robCoinRecursiveCycle(coins, 1, coins.length - 1)
        );
    }

    /**
     * 循环coins求解
     */
    private int robCoinRecursiveCycle(int[] coins, int start, int end) {

        if (start == end) return coins[start];

        if (start > end) return 0;

        return Math.max(
                coins[start] + robCoinRecursiveCycle(coins, start + 2, end),//抢劫第start个
                robCoinRecursiveCycle(coins, start + 1, end)); //不抢劫第start个
    }


    private int robCoinRecursiveTree(int[] coinTree, int root) {
        if (root >= coinTree.length //超出树的范围
                || coinTree[root] == -1 //结点为null
        ) return 0;
        //不选择root
        int leftChild = 2 * root + 1;
        int rightChild = 2 * root + 2;
        int noSelect = robCoinRecursiveTree(coinTree, leftChild) + //左子树
                robCoinRecursiveTree(coinTree, rightChild);//右子树
        int select = robCoinRecursiveTree(coinTree, 2 * leftChild + 1) + robCoinRecursiveTree(coinTree, 2 * leftChild + 2) +
                robCoinRecursiveTree(coinTree, 2 * rightChild + 1) + robCoinRecursiveTree(coinTree, 2 * rightChild + 2) + coinTree[root];
        return Math.max(noSelect, select);
    }

}
