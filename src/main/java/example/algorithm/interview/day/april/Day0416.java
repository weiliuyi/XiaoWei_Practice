package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0416 动态规划---博弈问题
 * @Description https://mp.weixin.qq.com/s/_d1Y7g1jnj7VFSxbo9YWSw
 * @Author weiliuyi
 * @Date 2021/4/16 11:18 上午
 **/
public class Day0416 {

    @Test
    public void testStoneGame() {
        int [] piles = new int[]{3,9,1,2};
        System.out.println(stoneGame(piles));
    }


    /**
     * 递归的思路：在二维dp的基础上使用元组分别存储两个人的博弈结果。
     *
     * 博弈问题的难点在于，两个人轮流选择，而且都是贼精明，应该如何编程表示这个过程？
     *
     * dp[i][j]= tuple,
     * tuple.first 表示对于piles[i,j] 这部分石头堆，先手能获得的最高分数；
     * tuple.second 表示对于piles[i,j] 这部分石头堆，后手能获得的最高分数；
     *
     * 举例理解 piles = [3,9,1,2] 索引从0开始
     * dp[0][1].first = 9 表示在[3,9] 这部分石头堆 先手能够获得最高分是9
     * dp[0][1].second = 3 表示【3，9】这部分石头堆，后手能够获取的最高分是3
     *
     * 状态转移方程：
     * dp[i][j].first = max(dp[i+1][j].second + piles[i],dp[i][j-1].second + piles[j]);
     * dp[i][j].first = max(选择最左侧的石头堆，选择最右侧石头堆);
     * 我作为先手，面对piles[i...j]时，有两种选择：
     * 要么我选择最左侧的石头堆，然后面对piles[i+1....j]
     * 但是此时轮到对方，相当于我变成后手
     * 要么我选择最右侧的石头堆，然后面对piles[i....j-1]
     * 但是此时轮到对方，相等于我变成后手；
     *
     * if 先手选择左边：
     *     dp[i][j].second = dp[i+1][j].first
     * fi 先手选择右边：
     *     dp[i][j].second = dp[i][j-1].first
     *
     * 如何斜着遍历二维数组？？？？
     * (0,0)(0,1)(0,2) (0,3)
     *      (1,1)(1,2) (1,3)
     *           (2,2) (2,3)
     *                 (3,3)
     */
    public int stoneGame(int[] piles) {
        Tuple[][] dpTable = new Tuple[piles.length][piles.length];
        for (int i = 0; i < piles.length; i++) {
            dpTable[i][i] = new Tuple(piles[i], 0);
        }
        //这种方式进行斜着遍历太繁琐
        for (int count = 1; count < piles.length; count++) {
            int column = count;
            for (int row = 0; row < piles.length - count ; row++) {
                //计算 [row,column]
                int right = dpTable[row][column-1].second + piles[column];
                int left = dpTable[row+1][column].second + piles[row];
                if (right > left) {
                    dpTable[row][column] = new Tuple(right,dpTable[row][column-1].first);
                }
                if (right <= left) {
                    dpTable[row][column] = new Tuple(left,dpTable[row+1][column].first);
                }
                column++;
            }
        }
        Tuple result = dpTable[0][piles.length - 1];
        for (int i = 0; i < piles.length; i++) {
            System.out.println(Arrays.toString(dpTable[i]));
        }
        return result.first - result.second;
    }


    //元组
    static class Tuple {
        int first;
        int second;

        public Tuple(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }
}
