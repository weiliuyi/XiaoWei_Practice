package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.*;

/**
 * @ClassName Day0619 BFS的暴力求解
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485383&idx=1&sn=4cd4b5b70e2eda33ad66562e5c007a1e&chksm=9bd7f9cfaca070d93c7ba83d1c821d06b9bfdc00eabe2710437f05ee7a5a0a67f7cb684402b8&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/29 11:06 上午
 **/
public class Day0619 {

    @Test
    public void testBFS() {
        BFSGame<int[][], Integer> myGame = new MyBFSGame();
        int[][] boards = {
                {4, 1, 2},
                {5, 0, 3}
        };
        int[][] target = {
                {1, 2, 3},
                {4, 5, 0}
        };

        BFSGame<int[][], Integer> finalBFSGame = new FinalBFSGame();
        System.out.println(myGame.game(boards, target));
        System.out.println(finalBFSGame.game(boards,target));
    }

    private static class FinalBFSGame implements BFSGame<int[][], Integer> {

        int[][] neighbor = {
                {1, 3},
                {0, 4, 2},
                {1, 5},
                {0, 4},
                {3, 1, 5},
                {4, 2}
        };

        @Override
        public Integer game(int[][] boards, int[][] target) {
            String targetStr = serialize(target), startStr = serialize(boards);
            Queue<String> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.add(startStr);
            int steps = 0;
            while (!queue.isEmpty()) {
                int qz = queue.size();
                for (int i = 0; i < qz; i++) {
                    String tempStr = queue.remove();
                    if (targetStr.equals(tempStr)) {
                        return steps;
                    }
                    int zeroIndex = findZeroPosition(tempStr);
                    for (int j = 0; j < neighbor[zeroIndex].length; j++) {
                        String str = swap(tempStr, zeroIndex, neighbor[zeroIndex][j]);
                        if (!visited.contains(str)) {
                            visited.add(str);
                            queue.add(str);
                        }
                    }

                }
                steps++;
            }

            return null;
        }

        String swap(String str,int i,int j) {
            char[] resArr = str.toCharArray();
            char temp = resArr[i];
            resArr[i] = resArr[j];
            resArr[j] = temp;
            return String.valueOf(resArr);
        }

        int findZeroPosition(String str) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') return i;
            }
            return -1;
        }

        /**
         * 将二维数组序列化成一维的字符串
         */
        private String serialize(int[][] arr) {
            StringBuilder builder = new StringBuilder();
            for (int[] intArr : arr) {
                for (int i : intArr) {
                    builder.append(i);
                }
            }
            return builder.toString();
        }
    }

    /**
     * 思路很简单，但是编码过程很繁琐；
     * 1。可以转化成一维数组，并且把映射关系记录下来
     */
    private static class MyBFSGame implements BFSGame<int[][], Integer> {

        @Override
        public Integer game(int[][] boards, int[][] target) {
            Queue<int[][]> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.add(boards);
            String targetStr = serialize(target);
            int steps = 0, rb = boards.length, cb = boards[0].length;
            while (!queue.isEmpty()) {
                int qz = queue.size();
                for (int i = 0; i < qz; i++) {
                    int[][] tempArr = queue.remove();
                    String tempArrStr = serialize(tempArr);
                    if (targetStr.equals(tempArrStr)) {
                        return steps;
                    }
                    int[] zeroPosition = findZeroPosition(tempArr);
                    int row = zeroPosition[0];
                    int col = zeroPosition[1];
                    //up
                    if (row > 0) {
                        int[][] upArr = up(tempArr, row, col);
                        String upStr = serialize(upArr);
                        if (!visited.contains(upStr)) {
                            visited.add(upStr);
                            queue.add(upArr);
                        }
                    }
                    //down
                    if (row < rb - 1) {
                        int[][] downArr = down(tempArr, row, col);
                        String downStr = serialize(downArr);
                        if (!visited.contains(downStr)) {
                            visited.add(downStr);
                            queue.add(downArr);
                        }

                    }
                    // left
                    if (col > 0) {
                        int[][] leftArr = left(tempArr, row, col);
                        String leftStr = serialize(leftArr);
                        if (!visited.contains(leftStr)) {
                            visited.add(leftStr);
                            queue.add(leftArr);
                        }

                    }
                    //right
                    if (col < cb - 1) {
                        int[][] rightArr = right(tempArr, row, col);
                        String rightStr = serialize(rightArr);
                        if (!visited.contains(rightStr)) {
                            visited.add(rightStr);
                            queue.add(rightArr);
                        }
                    }

                }
                steps++;
            }
            return -1;
        }

        /**
         * i ,j 表示0 的位置,up表示0和上边的数组交换
         */
        int[][] up(int[][] arr, int i, int j) {
            int[][] res = copyArr(arr);
            res[i][j] = res[i - 1][j];
            res[i - 1][j] = 0;
            return res;
        }


        int[][] down(int[][] arr, int i, int j) {
            int[][] res = copyArr(arr);
            res[i][j] = res[i + 1][j];
            res[i + 1][j] = 0;
            return res;
        }

        int[][] left(int[][] arr, int i, int j) {
            int[][] res = copyArr(arr);
            res[i][j] = res[i][j - 1];
            res[i][j - 1] = 0;
            return res;
        }

        int[][] right(int[][] arr, int i, int j) {
            int[][] res = copyArr(arr);
            res[i][j] = res[i][j + 1];
            res[i][j + 1] = 0;
            return res;
        }


        int[][] copyArr(int[][] arr) {
            int[][] res = new int[arr.length][arr[0].length];
            for (int i = 0; i < arr.length; i++) {
                System.arraycopy(arr[i], 0, res[i], 0, arr[i].length);
            }
            return res;
        }


        int[] findZeroPosition(int[][] arr) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    if (arr[i][j] == 0) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[0];
        }

        /**
         * 将二维数组序列化成一维的字符串
         */
        private String serialize(int[][] arr) {
            StringBuilder builder = new StringBuilder();
            for (int[] intArr : arr) {
                for (int i : intArr) {
                    builder.append(i);
                }
            }
            return builder.toString();
        }
    }

    private interface BFSGame<E, R> {
        R game(E e, E target);

    }
}
