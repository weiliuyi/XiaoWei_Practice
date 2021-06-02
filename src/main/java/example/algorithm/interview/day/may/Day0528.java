package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0528 搞懂回溯算法-解决数独问题
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485097&idx=1&sn=a5e82da8646cd8985de6b2b0950de4e2&chksm=9bd7f8a1aca071b7b72e23013bc2a7c528ee913fded9278e2058bc98d7c746e439737d7abb5b&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/2 10:29 上午
 **/
public class Day0528 {

    /**
     * 什么是数独问题？
     * <p>
     * 核心思想很简单：就是对于每一个空着的格子穷举1，9，如果遇到不合法的数字（同一行或者同一列或者3x3的区域中存在相同的数字），则跳过，如果找到一个合法的数字，则继续穷举下一个空格；
     */

    @Test
    public void testBackTrace() {

    }

    private static class MySudokuGame implements SudokuGame {

        @Override
        public void solveSudokuGame(int[][] board) {

        }

        private boolean sudokuGame(int[][] board, int row, int col) {
            if (col >= 9) {
                return sudokuGame(board, row + 1, 0);
            }
            if (row == 9) {
                return true;
            }
            for (int i = row; i < 9; i++) { //代表行
                for (int j = col; j < 9; j++) { //代表列
                    if (board[row][col] != 0) {
                        //continue; 之前已经放了数字  这两种方式有区别么？？？？？？？？
                        return  sudokuGame(board,i,j+1);
                    }

                    for (int k = 1; k < 9; k++) {
                        if (!isValid(board, i, j, k)) continue;

                        board[i][j] = k;
                        if (sudokuGame(board, i, j + 1)) {
                            return true;
                        }
                        board[i][j] = 0;
                    }
                    return false;
                }
            }
            return false;
        }

        private boolean isValid(int[][] board, int row, int col, int target) {
            //同一行或者同一列
            for (int j = 0; j < 9; j++) {
                if (board[row][j] == target || board[j][col] == target) return false;

                // (row / 3) * 3 宫的上边一行（最小行） 宫的最左列 j / 3
                if (board[(row / 3) * 3 + j / 3][(col / 3) * 3 + j % 3] == target) return false;
            }

            // 0 1 2 第0公  3， 4，5 第一宫  6 7 8 属于第二宫
            return true;
        }
    }


    /**
     * 数独问题接口
     */
    private interface SudokuGame {
        void solveSudokuGame(int[][] board);
    }
}
