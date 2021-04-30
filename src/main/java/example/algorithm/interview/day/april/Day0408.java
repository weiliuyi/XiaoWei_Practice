package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0408 最小路径和
 * @Description https://mp.weixin.qq.com/s/cwunN4Uoo4ZfO13kgkHVPQ 最小路径和
 * @Description https://mp.weixin.qq.com/s/MydL7eyzdfJc6jYZNwFWWw  魔塔通关
 * @Author weiliuyi
 * @Date 2021/4/29 2:37 下午
 **/
public class Day0408 {

    @Test
    public void testMinPathSum() {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        System.out.println(minPathSumBackTrace(grid, 3, 3, 0, 0));
        System.out.println(minPathSumDynamic(grid));
    }


    /**
     * 魔塔通关游戏
     * 二维数组：
     * grid[i][j] > 0 说明这个格子装着血瓶，经过它可以增加对应的生命值
     * grid[i][j] == 0 说明这是一个空格子，经过塔不会发生任何事情
     * grid[i][j] < 0 说明这个格子里有怪物，经过它会损失对应的生命值
     * <p>
     * 求解：至少需要多少初始生命值，能够让骑士从最左上角移动到最右下角，
     * 且任何时候生命值都必须大于零 最核心是这个问题？(这个问题比较棘手)
     */
    @Test
    public void testCalculateMinimumHp() {
        int[][] grid = {
                {-2, -3, 3},
                {-5, -10, 1},
                {10, 30, -5}
        };
        System.out.println(calculateMinimumHpV2(grid));
        System.out.println(calculateMinimumHpFinal(grid, 0, 0));
        System.out.println(calculateMinimumHpDynamicFinal(grid));
    }

    /**
     * 回溯算法进行求解
     */
    private int minPathSumBackTrace(int[][] grid, int row, int col, int pr, int pl) {

        if (pr == row || pl == col) return Integer.MAX_VALUE; //一旦发现有角标越界，就返回无解

        if (pr == row - 1 && pl == col - 1) return grid[pr][pl];

        //选择  向下 (pr+1,pl) 向右：(pr,pl+1)
        return Math.min(minPathSumBackTrace(grid, row, col, pr + 1, pl),
                minPathSumBackTrace(grid, row, col, pr, pl + 1)
        ) + grid[pr][pl];

    }

    /**
     * dp[i][j] 代表从（0，0） -> (i,j)最小路径和
     * base case
     * dp[0][0] = grid[0][0]; dp[0][i] = dp[0][i-1] + grid[0][i] dp[i][0] = dp[i-1][0] + grid[i][0];
     */

    private int minPathSumDynamic(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dpTable = new int[row][col];
        dpTable[0][0] = grid[0][0];
        for (int i = 1; i < row; i++) {
            dpTable[i][0] = dpTable[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < col; i++) {
            dpTable[0][i] = dpTable[0][i - 1] + grid[0][i];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dpTable[i][j] = Math.min(
                        dpTable[i - 1][j], // 上方
                        dpTable[i][j - 1]   // 左方
                ) + grid[i][j];
            }
        }
        return dpTable[row - 1][col - 1];
    }

    /**
     * 思路分析：
     * dp[i][j] 表示骑士从（0，0）到（i，j）至少需要携带的血量(负数)；
     * <p>
     * base case:dp[0][0] = grid[0][0];
     */
// 思路错误
//    private int calculateMinimumHp(int[][] grid) {
//        int row = grid.length;
//        int col = grid[0].length;
//        int[][] dpTable = new int[row][col];
//        //base case 如何保证过程中 血量大于 0
//        dpTable[0][0] = grid[0][0];
//        for (int i = 1; i < row; i++) {
//            dpTable[i][0] = Math.min(dpTable[i - 1][0] + grid[i][0],
//                    dpTable[i - 1][0]); //之所以这样初始化，过程中血量大于0； 需要的血量肯定不会比前一个少
//        }
//        for (int i = 1; i < col; i++) {
//            dpTable[0][i] = Math.min(dpTable[0][i - 1] + grid[0][i],
//                    dpTable[0][i - 1]);
//        }
//
//        for (int i = 1; i < row; i++) {
//            for (int j = 1; j < col; j++) {
//                dpTable[i][j] = Math.max(dpTable[i - 1][j],
//                        dpTable[i][j - 1]) + grid[i][j];
//            }
//        }
//
//        return -dpTable[row - 1][col - 1];
//    }


    /**
     * 逻辑也是错误的；
     */
    private int calculateMinimumHpV2(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Entry[][] dpTable = new Entry[row][col];
        dpTable[0][0] = new Entry(grid[0][0], grid[0][0]);

        //base case 如何保证过程中 血量大于 0
        for (int i = 1; i < row; i++) {
            Entry last = dpTable[i - 1][0];
            int now = last.now + grid[i][0];
            int max = Math.min(now, last.max);
            dpTable[i][0] = new Entry(now, max);

        }

        for (int i = 1; i < col; i++) {
            Entry last = dpTable[0][i - 1];
            int now = last.now + grid[0][i];
            int max = Math.min(now, last.max);
            dpTable[0][i] = new Entry(now, max);
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {

                // below 选择上方的状态
                Entry below = dpTable[i - 1][j];
                int bNow = below.now + grid[i][j];
                int bMax = Math.min(bNow, below.max);

                //left 选择左侧的状态
                Entry left = dpTable[i][j - 1];
                int lNow = left.now + grid[i][j];
                int lMax = Math.min(lNow, left.max);

                // 如果选择 指针max较大的选择能能保证结果最优么？
                if (lMax > bMax) {
                    dpTable[i][j] = new Entry(lNow, lMax);
                } else if (lMax == bMax) {
                    dpTable[i][j] = lNow > bNow ? new Entry(lNow, lMax) : new Entry(bNow, bMax);
                } else {
                    dpTable[i][j] = new Entry(bNow, bMax);
                }
            }
        }
        for (int i = 0; i < row; i++) {
            System.out.println(Arrays.toString(dpTable[i]));
        }
        return -(dpTable[row - 1][col - 1].max);
    }

    /**
     * 逆向思考
     * dp[i][j] 到右下角的需要的至少血量
     */
    private int calculateMinimumHpFinal(int[][] grid, int i, int j) {
        int row = grid.length;
        int col = grid[0].length;

        if (i == row - 1 && j == col - 1) return grid[i][j] >= 0 ? 1 : -grid[i][j] + 1;

        if (i == row || j == col) return Integer.MAX_VALUE;

        int res = Math.min(calculateMinimumHpFinal(grid, i, j + 1),
                calculateMinimumHpFinal(grid, i + 1, j)) - grid[i][j];

        return res <= 0 ? 1 : res;

    }

    private int calculateMinimumHpDynamicFinal(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dpTable = new int[row][col];

        dpTable[row - 1][col - 1] = grid[row - 1][col - 1] > 0 ? 1 : -grid[row - 1][col-1] + 1;
        for (int i = col - 2; i >= 0; i--) {
            int res = dpTable[row-1][i+1] - grid[row-1][i];
            dpTable[row-1][i] = res > 0 ? res : 1;
        }

        for (int i = row-2; i >=0; i--) {
            int res = dpTable[i+1][col-1] - grid[i][col-1];
            dpTable[i][col-1] = res > 0 ? res : 1;
        }

        for (int i = row-2; i >=0 ; i--) {
            for (int j = col-2; j >=0 ; j--) {
                int res = Math.min(dpTable[i+1][j],dpTable[i][j+1]) - grid[i][j];
                dpTable[i][j] = res > 0 ? res : 1;
            }
        }
        return dpTable[0][0];
    }


    /**
     * 每次都是去当前血量的最小值（绝对值最大）
     */
    private static class Entry {
        int now; // 当前的状态；
        int max; // 记录当前至少需要的血量 负数

        public Entry(int now, int max) {
            this.now = now;
            this.max = max;
        }

        @Override
        public String toString() {
            return "{" + "now=" + now + ", max=" + max + '}';
        }
    }


    public static void main(String[] args) {
        int[][] temp = new int[3][5];
        int row = temp.length;
        int column = temp[0].length;
        System.out.println("row = " + row + " column = " + column);
    }

}
