package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0429 详解最公共子序列的问题；秒杀三道动态规划的题目
 * @Description https://mp.weixin.qq.com/s/ZhPEchewfc03xWv9VP3msg
 * @Author weiliuyi
 * @Date 2021/4/29 10:24 上午
 **/
public class Day0429Exercise {

    /**
     * 最长公共子序列（longest common subsequence）
     */
    @Test
    public void testLCS() {
        String s1 = "zabcde";
        String s2 = "acez";
        System.out.println(lcsDynamic(s1, s2));
        System.out.println(lcsBackTrack(s1, 0, s2, 0));

    }

    /**
     * 两个字符串的删除操作
     * <p>
     * 给定两个单词s1和s2，找到使得s1和s2相同所需的最小步数，每步可以删除任意一个字符串中的一个字符
     */
    @Test
    public void testStrDelete() {
        String s1 = "sea";
        String s2 = "eat";
        System.out.println(minDistance(s1, s2));
        System.out.println(minDistanceV2(s1, s2));
        System.out.println(minDistanceBackTrace(s1, 0, s2, 0));
        System.out.println(minDistanceBackTraceV2(s1, s1.length() - 1, s2, s2.length() - 1));
    }


    /**
     * 给定两个字符串s1，s2，找到使这两个字符串相等所需要删除字符的ASCII值的最小和；
     */

    @Test
    public void testMinimumDeleteSum() {
        String s1 = "sea";
        String s2 = "eat";
        System.out.println(minimumDeleteSumDynamic(s1, s2));
        System.out.println(minimumDeleteSumBackTrace(s1, 0, s2, 0));
        System.out.println(minimumDeleteSumBackTraceV2(s1, s1.length()-1, s2, s2.length()-1));
    }

    /**
     * 动态规划的思路：
     * 状态：i，j
     * i：代表文本串s1的下标
     * j：代表文本串s2的下标
     * <p>
     * 含义
     * dp[i][j] = num 文本串s1[0,i]和文本串s2[0,j]最长的公共字串为num
     * <p>
     * 状态转移方程：
     * if (s1[i] == s2[j]) {
     * dp[i][j] = dp[i-1][j-1] + 2
     * } else {
     * dp[i][j] = max(dp[i-1][j],dp[i][j-1]);
     * }
     * dp[i-1][j-1]  dp[i-1][j]
     * dp[i][j-1]   dp[i][j]
     * <p>
     * base case dp[0][0] = 0;dp[0][*] = 0,dp[*][0] = 0;
     */
    private int lcsDynamic(String s1, String s2) {
        int[][] dpTable = new int[s1.length() + 1][s2.length() + 1];
        //base case dpTable[0][*] = 0 dpTable[*][0] = 0;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dpTable[i][j] = dpTable[i - 1][j - 1] + 1;
                } else {
                    dpTable[i][j] = Math.max(dpTable[i - 1][j], dpTable[i][j - 1]);
                }
            }
        }
        return dpTable[s1.length()][s2.length()];
    }

    /**
     * LCS 回溯算法
     */

    private int lcsBackTrack(String s1, int i, String s2, int j) {
        if (i == s1.length() || s2.length() == j) return 0;

        if (s1.charAt(i) == s2.charAt(j)) {
            return lcsBackTrack(s1, i + 1, s2, j + 1) + 1;
        } else {

            //s1[i] != s2[j]  ，str1[i] 和str2[j]中至少有一个字符不在lcs中；
            return Math.max(
                    Math.max(
                            lcsBackTrack(s1, i + 1, s2, j),
                            lcsBackTrack(s1, i, s2, j + 1)
                    ),
                    lcsBackTrack(s1, i + 1, s2, j + 1));
        }

    }


    /**
     * 两个字符串的删除操作
     * <p>
     * 状态 i，j 分别是s1和s2中第i个字符 和第j个字符
     * <p>
     * dp[i][i] = num 文本串s1[0,i)和文本串s2[0,j)的删除任意字符所需要的最小步数为num
     * <p>
     * 状态转移方程：
     * if (s1[i-1]==s2[j-1]) {
     * dp[i][j] = dp[i-1][j-1];
     * } else {
     * dp[i][j] = min(dp[i-1][j],dp[i][j-1]);
     * }
     * base case dp[0][0] = 0; dp[0][*] = *,dp[*][0] = *
     */
    private int minDistance(String s1, String s2) {
        int[][] dpTable = new int[s1.length() + 1][s2.length() + 1];
        //base case
        //dp[*][0] = *;
        for (int i = 1; i <= s1.length(); i++) {
            dpTable[i][0] = i;
        }
        //dp[0][*] = *
        for (int i = 1; i <= s2.length(); i++) {
            dpTable[0][i] = i;
        }
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dpTable[i][j] = dpTable[i - 1][j - 1];
                } else {
                    dpTable[i][j] = Math.min( //最小步数
                            dpTable[i - 1][j], // 删除s1的字符
                            dpTable[i][j - 1]  //删除s2的字符
                    ) + 1;
                }
            }
        }
        return dpTable[s1.length()][s2.length()];
    }

    /**
     * 两个字符串的删除操作
     * 利用最长公共子序列进行计算
     */

    private int minDistanceV2(String s1, String s2) {
        int len = lcsDynamic(s1, s2);
        return s1.length() - len + s2.length() - len;
    }

    /**
     * 两个字符串的删除操作
     * 使用回溯算法
     */
    private int minDistanceBackTrace(String s1, int i, String s2, int j) {
        if (i == s1.length() && j == s2.length()) return 0;

        if (s2.length() == j && i < s1.length()) return s1.length() - i;

        if (s1.length() == i && j < s2.length()) return s2.length() - j;

        if (s1.charAt(i) == s2.charAt(j)) {
            return minDistanceBackTrace(s1, i + 1, s2, j + 1);
        } else {
            return Math.min(minDistanceBackTrace(s1, i + 1, s2, j)
                    , minDistanceBackTrace(s1, i, s2, j + 1)
            ) + 1;
        }

    }

    /**
     * 换一种进行方式进行回溯算法
     */
    private int minDistanceBackTraceV2(String s1, int i, String s2, int j) {
        if (i == -1 && j == -1) return 0;
        if (j == -1 && i >= 0) return i + 1;
        if (i == -1 && j >= 0) return j + 1;

        if (s1.charAt(i) == s2.charAt(j)) {
            return minDistanceBackTraceV2(s1, i - 1, s2, j - 1);
        } else {
            return Math.min(minDistanceBackTraceV2(s1, i - 1, s2, j)
                    , minDistanceBackTraceV2(s1, i, s2, j - 1)
            ) + 1;
        }

    }


    /**
     * 两个字符串中最小的ASCII删除和
     * 动态规划
     */
    private int minimumDeleteSumDynamic(String s1, String s2) {
        int[][] dpTable = new int[s1.length() + 1][s2.length() + 1];
        //base case dp[0][0] = 0 dp[0][j] = s2.charAt(j-1) + dp[0][j-1]
        //dp[i][0] = dp[i-1][0] + s1.charAt(i-1);
        for (int i = 1; i <= s1.length(); i++) {
            dpTable[i][0] = dpTable[i - 1][0] + s1.charAt(i - 1);
        }
        for (int j = 1; j <= s2.length(); j++) {
            dpTable[0][j] = dpTable[0][j - 1] + s2.charAt(j - 1);
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dpTable[i][j] = dpTable[i - 1][j - 1];
                } else {
                    dpTable[i][j] = Math.min(
                            s1.charAt(i - 1) + dpTable[i - 1][j],
                            s2.charAt(j - 1) + dpTable[i][j - 1]
                    );
                }
            }
        }
        return dpTable[s1.length()][s2.length()];
    }

    /**
     * 两个字符串中最小的ASCII删除和
     * 使用回溯算法
     */
    private int minimumDeleteSumBackTrace(String s1, int i, String s2, int j) {
        if (i == s1.length() && j == s2.length()) return 0;

        if (j == s2.length() && i < s1.length()) { // 此时s2已经到达末尾，但是s1还没有结束，需要被删除；
            int temp = 0;
            for (int k = i; k < s1.length(); k++) {
                temp += s1.charAt(k);
            }
            return temp;
        }

        if (i == s1.length() && j < s2.length()) { //s1已经到达末尾，但是s2还没有结束，s2[j,s2.length-1]需要被删除
            int temp = 0;
            for (int k = j; k < s2.length(); k++) {
                temp += s2.charAt(k);
            }
            return temp;
        }
        if (s1.charAt(i) == s2.charAt(j)) {
            return minimumDeleteSumBackTrace(s1, i + 1, s2, j + 1);
        } else {
            return Math.min(minimumDeleteSumBackTrace(s1, i + 1, s2, j) + s1.charAt(i),
                    minimumDeleteSumBackTrace(s1, i, s2, j + 1) + s2.charAt(j)
            );
        }
    }

    /**
     * 两个字符串中最小的ASCII删除和
     * 使用回溯算法 版本2
     */
    private int minimumDeleteSumBackTraceV2(String s1, int i, String s2, int j) {
        if (i == -1 && j == -1) return 0;

        if (j == -1 && i >= 0) {
            int temp = 0;
            for (int k = 0; k <= i; k++) {
                temp += s1.charAt(k);
            }
            return temp;
        }

        if (i == -1 && j >= 0) {
            int temp = 0;
            for (int k = 0; k <= j; k++) {
                temp += s2.charAt(k);
            }
            return temp;
        }

        if (s1.charAt(i) == s2.charAt(j)) {
            return minimumDeleteSumBackTraceV2(s1, i - 1, s2, j - 1);
        } else {
            return Math.min(s1.charAt(i) + minimumDeleteSumBackTraceV2(s1, i - 1, s2, j),
                    s2.charAt(j) + minimumDeleteSumBackTraceV2(s1, i, s2, j - 1)
            );
        }
    }


}


