package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0415 动态规划---之编辑距离；
 * @Description https://mp.weixin.qq.com/s/uWzSvWWI-bWAV3UANBtyOw
 * @Author weiliuyi
 * @Date 2021/4/15 2:48 下午
 **/
public class Day0415 {

    @Test
    public void testEditDistance() {
        String s1 = "intention";
        String s2 = "execution";
        System.out.println(minDistanceRecur(s1, s2, s1.length() - 1, s2.length() - 1));
        System.out.println(minDistanceMemo(s1, s2, s1.length() - 1, s2.length() - 1, new int[s1.length()][s2.length()]));
        System.out.println(minDistanceDynamic(s1, s2));
    }

    /*
     * 给定两个字符串s1和s2，将s1替换成s2最少的操作数。
     * 你可一对一个字符进行三纵操作
     * 1。插入一个字符
     * 2。删除一个字符
     * 3。替换一个字符
     *
     * 解决两个字符串的动态规划问题：一般都是用两个指针i，j分别指向两个字符串的最后，然后一步一步的往前走，缩小问题的规模；
     *
     * 状态是什么？ s1->i s2->j
     *
     * 选择：插入，删除，替换
     *
     * base i--->-1 插入（s1走完 s2还没有走完，将s2剩余的部分插入）
     *      j--->-1 删除（s1没有走完，s2走完，删除s1剩余的部分）
     *
     */


    /**
     * 使用递归解法
     */
    private int minDistanceRecur(String s1, String s2, int i, int j) {
        if (i == -1) return j + 1;
        if (j == -1) return i + 1;
        if (s1.charAt(i) == s2.charAt(j)) {
            return minDistanceRecur(s1, s2, i - 1, j - 1);//跳转
        }
        int replace = minDistanceRecur(s1, s2, i - 1, j - 1);
        int insert = minDistanceRecur(s1, s2, i, j - 1);
        int del = minDistanceRecur(s1, s2, i - 1, j);
        return Math.min(Math.min(replace, insert), del) + 1;

    }

    /**
     * 使用备忘录，通过备忘录进行剪枝，提升递归的效率；
     */
    private int minDistanceMemo(String s1, String s2, int i, int j, int[][] memo) {
        if (i == -1) return j + 1; //如果s1提前结束，需要把s2的 0 ----j（j+1）字符插入s1
        if (j == -1) return i + 1; //如果s2提前结束，需要把s1的 0-----i（i+1）字符删除

        if (memo[i][j] != 0) return memo[i][j];

        if (s1.charAt(i) == s2.charAt(j)) { //相等 直接跳过 不计算步数
            return minDistanceMemo(s1, s2, i - 1, j - 1, memo);
        }

        //删除s1中s1.charAt(i)字符,i向前移动一位 j不变
        int del = minDistanceMemo(s1, s2, i - 1, j, memo);
        //替换s1中i位置的字符替换为s2.charAt(j)，i，j 都向前移动一位
        int replace = minDistanceMemo(s1, s2, i - 1, j - 1, memo);
        //在s1中的i位置后插入s2.charAt(j),j向前移动一位，i不变
        int insert = minDistanceMemo(s1, s2, i, j - 1, memo);
        return Math.min(del, Math.min(replace, insert)) + 1;
    }

    /**
     * 动态规划求解 最小编辑距离
     * dp[i][j] = n:含义为：s1[0,i-1] 替换成 s2[0,j-1] 最少需要n个操作
     */
    private int minDistanceDynamic(String s1, String s2) {
        int[][] dpTable = new int[s1.length() + 1][s2.length() + 1];

        //base case
        for (int i = 1; i <= s1.length(); i++) {
            dpTable[i][0] = i;//s1和s2（空串）进行匹配时
        }
        for (int j = 1; j <= s2.length(); j++) {
            dpTable[0][j] = j; //s1是空串 和s2进行匹配是
        }
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dpTable[i][j] = dpTable[i - 1][j - 1];
                    continue;
                }
                dpTable[i][j] = Math.min(
                        dpTable[i - 1][j - 1],//替换
                        Math.min(dpTable[i - 1][j], //删除
                                dpTable[i][j - 1]) //插入
                ) + 1;
            }
        }
        return dpTable[s1.length()][s2.length()];
    }
}
