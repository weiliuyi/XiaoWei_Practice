package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0523 N皇后问题 回溯算法的联系
 * @Description
 * @Author weiliuyi
 * @Date 2021/5/28 3:53 下午
 **/
public class Day0523 {


    @Test
    public void testNQueen() {
        QueenNumberImpl queenNumber = new QueenNumberImpl();
        int res = queenNumber.queenNumber(8);
        System.out.println(res);
        System.out.println("------------------------");
        QueenNumberImplV2 queenNumberV2 = new QueenNumberImplV2();
        System.out.println(queenNumberV2.queenNumber(8));
    }


    private static class QueenNumberImpl implements QueenNumber {

        private int INVALID = -1;

        @Override
        public int queenNumber(int n) {
            int[] queenPosition = new int[n];
            Arrays.fill(queenPosition,-10);
            return queenNumber(n,0,queenPosition);

        }

        /**
         *
         * @param n 代表n*n的数据类型
         * @param row 代表当前操作的行数
         * @param queenPosition  queenPosition[i] = j 代表的含义为 第i行第j列放置一个皇后
         */
        //从0开始
        private int queenNumber (int n, int row, int[] queenPosition) {
            if (n == row) {
                //System.out.println(Arrays.toString(queenPosition));
                return 1;
            }
            int sum = 0;
            for (int i = 0; i < n; i++) { //i代表第几列
                boolean flag = true;

                for (int j = row - 1, k = 1; j >= 0  ; j--,k++) { //代表行j
                    if (queenPosition[j] == i  //第j行上是否有是否有i列的元素
                            || queenPosition[j] == i - k  //左上
                            || queenPosition[j] == i + k) { // 右上
                        flag = false;
                        break;
                    }
                }
                if (!flag) continue;
                queenPosition[row] = i;
                sum += queenNumber(n, row + 1, queenPosition);
                queenPosition[row] = INVALID;
            }
            return sum;
        }
    }


    private static class QueenNumberImplV2 implements QueenNumber {

        @Override
        public int queenNumber(int n) {
            char[][] board = new char[n][n];
            return queenNumber(board,0);
        }

        /**
         * @param board 代表 n * n的棋盘，
         * @param row 当前的行数
         */
        private int queenNumber (char[][] board,int row) {
            if (row == board.length) return 1;

            int sum = 0;
            for (int i = 0; i < board.length; i++) { // i；代表第i列
                if (!isValid(board,row,i))  continue;

                board[row][i] = 'Q';
                sum += queenNumber(board,row + 1);
                board[row][i] = '0';
            }
            return sum;
        }

        /**
         * 校验在第row行 第col列上放置 皇后时候满足要求
         * @param board 棋盘
         */
        private boolean isValid (char[][] board,int row,int col) {
            //同一列
            for (int i = 0; i < row; i++) {
                if (board[i][col] == 'Q') {
                    return false;
                }
            }
            //左上
            for (int i = row - 1,k = 1; i >= 0 ; i--,k++) {
                if (col - k >= 0 && board[i][col - k] == 'Q') {
                    return false;
                }
            }

            //右上
            for (int i = row-1,k = 1; i >=0 ; i--,k++) {
                if (col + k < board.length && board[i][col + k] == 'Q') {
                    return false;
                }
            }
            return true;
        }
    }



    /**
     * n个皇后在 n*n的棋盘上不能互相攻击
     */
    private static interface QueenNumber {
        int queenNumber (int n);
    }
}
