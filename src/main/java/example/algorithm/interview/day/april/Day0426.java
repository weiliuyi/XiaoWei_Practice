package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0426 戳气球问题
 * @Description https://mp.weixin.qq.com/s/I0yo0XZamm-jMpG-_B3G8g
 * @Author weiliuyi
 * @Date 2021/4/26 2:26 下午
 **/
public class Day0426 {


    /**
     * 戳气球 数组的长度为n
     * <p>
     * 状态是什么？？？？
     * 1。戳破第i个气球，此时n-1个气球构成了子问题
     * <p>
     * 如何定义dp数组呢？
     * 这种定义方式是有问题
     * dp[n] = num：有n个气球时，最大值为num
     * baseCase : dp[0] = 0;
     */

    @Test
    public void testBurstBalloon() {
        int[] numbers = {3, 1, 5, 8};
        System.out.println("回溯算法 = " + burstBalloon(numbers));


        System.out.println("动态规划(斜着遍历) = " + burstBalloonDynamic(numbers));
        System.out.println("动态规划（横着遍历）= " + burstBalloonDynamicV2(numbers));
    }

    /**
     * 使用递归的方式解决
     * 思考方式：如果选中第i个元素，那么数组中剩余的arr.length-1个元素 也构成了一个新的子问题
     */
    public int burstBalloon(int[] arr) {
        if (arr.length == 0) return 0;

        int result = -1;
        for (int i = 0; i < arr.length; i++) {
            int left, right;
            if (i == 0) {
                left = 1;
            } else {
                left = arr[i - 1];
            }

            if (i == arr.length - 1) {
                right = 1;
            } else {
                right = arr[i + 1];
            }

            int temp = left * arr[i] * right;
            result = Math.max(result,
                    temp + burstBalloon(delIndex(arr, i))); // 求解每个子问题
        }
        return result;
    }

    /**
     * 只要遇到求最值的算法问题？首先要思考的就是：如何穷举出所有的可能的结果
     * <p>
     * 穷举主要有两种算法：
     * 回溯算法：暴力穷举             正向思考（戳破一个气球，就转化成子问题了）
     * 动态规划：根据状态转移方程推导   逆向思考 （如果想获得最大值，戳破哪一个气球，最后一个被戳破的会是哪个气球）
     * <p>
     * dp[i][j]表示：戳破气球i和气球j之间（开区间 不包括i，j）的所有气球，可以获得最高分数为x
     * 哪一个气球最有可能最后一个被戳破呢？等可能
     * 如果第k个最后一个戳破 区间就变成了dp[i][k] dp[k][j]  arr[i] * arr[k] * arr[j]
     * <p>
     * 状态转移方程：
     * dp[i][j] = dp[i][k] + dp[k][j] + arr[i] * arr[k] * arr[j];
     * <p>
     * k = 5
     * dp[3][6] = dp[3][5] + dp[5][6] + arr[3] * arr[k] * arr[6]
     */
    private int burstBalloonDynamic(int[] arr) {
        //将数组的长度延长
        int[] pointer = new int[arr.length + 2];

        pointer[0] = pointer[arr.length + 1] = 1;
        for (int i = 0; i < arr.length; i++) {
            pointer[i + 1] = arr[i];
        }
        //dp[0][arr.length+1]  目标
        int[][] dpTable = new int[arr.length + 2][arr.length + 2];

        //base case dp[i][j] = 0 (i >= j,j-i == 1)

        for (int num = 1; num <= arr.length + 1; num++) {  //num 有两种含义 1。第一种需要循环的次数 2。斜着遍历的起始列
            for (int j = num; j <= arr.length + 1; j++) { //代表斜着遍历的列，
                int i = j - num;
                //i --- j 已经出现
                for (int k = i + 1; k < j; k++) {
                    dpTable[i][j] = Math.max(dpTable[i][j],
                            dpTable[i][k] + dpTable[k][j] + pointer[i] * pointer[k] * pointer[j]
                    );
                }
            }
        }
        return dpTable[0][arr.length + 1];
    }


    private int burstBalloonDynamicV2 (int[] arr) {
        int[] pointer = new int[arr.length + 2];
        pointer[0] = pointer[arr.length + 1] = 1;
        for (int i = 0; i < arr.length; i++) {
            pointer[i+1] = arr[i];
        }
        //dp[i][j] = num 从数组的下标i到j（不包含i，j）的最大值为 num;
        int[][] dpTable = new int[pointer.length][pointer.length];

        // base case dp[i][j] = 0; i>=j 或者j-i=1

        for (int i = pointer.length-2; i >=0 ; i--) { //i表示第几行

            for (int j = i+1; j < pointer.length; j++) { //   对角线右边的第一个元素 相当于i+1

                for (int k = i+1; k <j ; k++) {
                    dpTable[i][j] = Math.max(dpTable[i][j],
                            dpTable[i][k] + dpTable[k][j] + pointer[i] * pointer[k] * pointer[j]);
                }
            }
        }
        return dpTable[0][pointer.length-1];
    }



    /**
     * 删除指定数组的下标的元素，并且返回一个新的数组
     */
    private int[] delIndex(int[] arr, int index) {
        int[] newIndex = new int[arr.length - 1];
        for (int i = 0; i < arr.length; i++) {
            if (i < index) {
                newIndex[i] = arr[i];
            } else if (i > index) {
                newIndex[i - 1] = arr[i];
            }
        }
        return newIndex;
    }

    @Test
    public void main() {
        int[] arr = {1, 2, 3, 4};
        System.out.println(Arrays.toString(delIndex(arr, 2)));
    }

}
