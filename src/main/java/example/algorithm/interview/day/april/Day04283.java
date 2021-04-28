package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day04283 最小代价构造回文串
 * @Description https://mp.weixin.qq.com/s/C14WNUpPeBMVSMqh28JdfA
 * @Author weiliuyi
 * @Date 2021/4/28 3:19 下午
 **/
public class Day04283 {

    /**
     * 给你一个字符串s，每一次操作你都可以在字符串的任意位置插入任意字符
     * 请你返回让s称为回文串的最小操作次数
     * <p>
     * 示例：s = "abcea" 算法返回2，因为在s中插入2个字符变成回文串 "abeceba" 或者 "aebcbea"
     * 如果输入s = "aba",算法返回 0
     */


    @Test
    public void testMiniPalindrome() {
        String s = "abcea";
        String s2 = "aba";
        System.out.println(minInsertionBackTrace(s, 0, s.length() - 1));
        System.out.println(minInsertionBackTrace(s2, 0, s2.length() - 1));

        System.out.println("----------");
        System.out.println(minInsertionDynamic(s));
        System.out.println(minInsertionDynamic(s2));

    }

    /**
     * 回溯的思路
     */
    private int minInsertionBackTrace(String s, int start, int end) {
        if (start > end) return 0;

        if (s.charAt(start) == s.charAt(end)) {
            return minInsertionBackTrace(s, ++start, --end); //不需要插入字符
        } else {
 //这种方式是错误的
 // 这是因为，调用第一个方法的时候已经++start了，调用第二个方法的时候start是已经更新过的值了
//            return Math.min(minInsertionBackTrace(s, ++start, end) //右侧插入一个字符
//                    , minInsertionBackTrace(s, start, --end) //左侧插入一个字符
//            ) + 1;

            return Math.min(minInsertionBackTrace(s, start + 1, end),
                    minInsertionBackTrace(s, start, end-1)) + 1;
        }
    }

    /**
     * 动态规划
     * 状态i，j 文本串的开始和结束的位置
     * dp[i][j] 对字符串s[i..j],至少需要dp[i][j]次插入才能转化为回文串；
     *
     * if (s.charAt(i) == s.charAt(j)) {
     *     dp[i][j] = dp[i+1][j-1];
     * } else {
     *     dp[i][j] = Math.max( dp[i+1][j]
     *     ,dp[i][j-1]
     *     )+1;
     * }
     *
     *
     *       dp[i][j-1]   dp[i][j]
     *       dp[i+1][j-1] dp[i+1][j]
     *
     *  base case
     *  dp[i][j] == 0 i==j
     *  dp[0][0] = 0
     *
     */
    private int minInsertionDynamic (String s) {
        int[][] dpTable = new int[s.length()][s.length()];
        for (int i = s.length()-2; i >=0; i--) {
            for (int j = i + 1; j < s.length() ; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dpTable[i][j] = dpTable[i+1][j-1];
                } else {
                    dpTable[i][j] = Math.min(dpTable[i+1][j],dpTable[i][j-1]) + 1;
                }
            }
        }
        return dpTable[0][s.length()-1];
    }


}
