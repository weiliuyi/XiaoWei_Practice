package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0408 最小路径和
 * @Description https://mp.weixin.qq.com/s/cwunN4Uoo4ZfO13kgkHVPQ
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


    public static void main(String[] args) {
        int[][] temp = new int[3][5];
        int row = temp.length;
        int column = temp[0].length;
        System.out.println("row = " + row + " column = " + column);
    }

}
