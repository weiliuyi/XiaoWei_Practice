package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0422 子序列的解题模版
 * @Description https://mp.weixin.qq.com/s/zNai1pzXHeB2tQE6AdOXTA
 * @Author weiliuyi
 * @Date 2021/4/22 10:53 上午
 **/
public class Day0422 {

    /**
     * 最长递增子序列
     */
    @Test
    public void testMaxIncrementSubArr() {
        int[] arr = {1, 3, 8, 4, 2, 9, 9, 10};
        System.out.println(maxIncrementSubArr(arr));
    }

    /**
     * 最长公共子序列（Longest Common Subsequence)简称 LCS
     */
    @Test
    public void testLCS() {
        String str1 = "abcde";
        String str2 = "ace";
        System.out.println(longestCommonSubsequence(str1, str2));
    }

    /**
     * 最长回文字串
     * https://mp.weixin.qq.com/s/zNai1pzXHeB2tQE6AdOXTA
     */
    @Test
    public void testLongestPalindromeSubsequence () {
        String content = "bbbab";
        System.out.println(longestPalindromeSubsequence(content));
    }


    /**
     * 最长递归子序列
     * dp数组的含义：
     * 在子数组array[0...i]中，以array[i]结尾的目标子序列(最长递增子序列)的长度为dp[i]
     */
    private int maxIncrementSubArr(int[] arr) {
        int[] dpTable = new int[arr.length];
        dpTable[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            int temp = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] <= arr[j]) continue;

                temp = Math.max(temp, dpTable[j] + 1);
            }
            dpTable[i] = temp;
        }
        System.out.println(Arrays.toString(dpTable));
        return Arrays.stream(dpTable).max().getAsInt();
    }




    /**
     * 最长公共子序列
     * str1:下标 i str2：下标 j
     * 三种情况：
     * str1.charAt(i) == str2.charAt(j)  转化为str1[0,i-1] 与str2[0,j-1]的匹配问题
     * <p>
     * str1.charAt(i) != str2.charAt(j) 转化为
     * str1[0,i-1] 与str2[0,j]的匹配问题
     * str2[0,i] 与str2[0,j-1]的匹配问题
     */
    private int longestCommonSubsequence(String str1, String str2) {
        int[][] dpTable = new int[str1.length() + 1][str2.length() + 1];
        //base case dp[0][*] = 0     dp[*][0] = 0
        for (int i = 1; i <= str1.length(); i++) {

            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dpTable[i][j] = dpTable[i - 1][j - 1] + 1;
                } else {
                    dpTable[i][j] = Math.max(dpTable[i - 1][j], dpTable[i][j - 1]);
                }
            }
        }
        return dpTable[str1.length()][str2.length()];
    }


    /**
     * 状态转移需要归纳思维，说白了就是从已知的结果推出未知的部分
     *
     *
     * 最长回文子序列
     * dp[i][j] 子数组array[i……j]中，我们要求的子序列（最长回文子序列）的长度为dp[i][j]
     *
     * 如何进行状态转移
     * str.charAt(i) == str.charAt(j)
     *  愿问题就变成了 子数组array[i+1,j-1]中，求最长子序列 dp[i+1][j-1] + 2
     *
     * str.charAt(i) != str.charAt(j)
     *  问题可以拆分成两个子问题的最大值
     *      1。 子数组array[i+1,j]中求解 最大回文子序列
     *      2。 子数组array[i,j-1]中求解 最大回文子序列
     * 
     * 假设j == 3
     *  (0,0),(0,1),(0,2),(0,3),(0,4)
     *        (1,1),(1,2),(1,3),(1,4)
     *              (2,2),(2,3),(2,4)
     *                   ,(3,3),(3,4)
     *                          (4,4)
     *       
     */
    private int longestPalindromeSubsequence(String content) {
        int[][] dpTable = new int[content.length()][content.length()];
        //base case 将对角线初始化为1
        for (int i = 0; i < content.length(); i++) {
            dpTable[i][i] = 1;
        }

        for (int i = content.length()-2; i >=0 ; i--) {
            for (int j = i+1; j < content.length() ; j++) {
                if (content.charAt(i) == content.charAt(j)) {
                    dpTable[i][j] = (j - i == 1 ? 0 : dpTable[i + 1][j - 1]) + 2;
                } else {
                    dpTable[i][j] = Math.max(dpTable[i + 1][j], dpTable[i][j - 1]);
                }
            }
        }
        
//        遍历的方式不对
//        for (int j = 1; j < content.length(); j++) {
//            for (int i = 0; i < j; i++) {
//                if (content.charAt(i) == content.charAt(j)) {
//                    dpTable[i][j] = (j - i == 1 ? 0 : dpTable[i + 1][j - 1]) + 2;
//                } else {
//                    dpTable[i][j] = Math.max(dpTable[i + 1][j], dpTable[i][j - 1]);
//                }
//            }
//        }
        
        return dpTable[0][content.length() - 1];
    }
}
