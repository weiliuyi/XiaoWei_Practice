package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day04282 正则通配符算法
 * @Description https://mp.weixin.qq.com/s/rnaFK05IcFWvNN1ppNf2ug
 * @Author weiliuyi
 * @Date 2021/4/28 9:59 上午
 **/
public class Day04282 {
    /**
     * 实现一个简单的正则匹配算法，其中包括 「.」通配符和「*」通配符
     * 通配符解释：
     * 「.」可以匹配任何字符
     * 「*」可以让之前的那个字符重复任意次
     * 示例： 模式串".a*b" 可以匹配 "zaaab" 也可以匹配 "cb"
     * 模式串"a..b" 可以匹配文本 "amnb";
     * 而模式串".*" 可以匹配任何文本
     */
    @Test
    public void testStrMatch() {
        String pat = ".a*b";
        String str = "zaaab";
        String str2 = "cb";
        System.out.println(isMatchForce(str, pat));
        System.out.println(isMatchForce(str2, pat));

        String str3 = "amnb";
        String pat3 = "a..b";

        System.out.println(isMatchForce(str3, pat3));

        System.out.println("----------------");
        String pat4 = ".*";
        System.out.println(isMatchForce(str, pat4));

        System.out.println(isMatchForce(str2, pat4));

        System.out.println(isMatchForce(str3, pat4));
    }


    /**
     * 回溯算法
     */
    @Test
    public void testStrMatchBackTrack() {
        String pat = ".a*b";
        String str = "zaaab";
        String str2 = "cb";
        System.out.println(isMatchBackTrack(str, str.length() - 1, pat, pat.length() - 1));
        System.out.println(isMatchBackTrack(str2, str2.length() - 1, pat, pat.length() - 1));

        String str3 = "amnb";
        String pat3 = "a..b";

        System.out.println(isMatchBackTrack(str3, str3.length() - 1, pat3, pat3.length() - 1));

        System.out.println("----------------------------");
        String pat4 = ".*";
        System.out.println(isMatchBackTrack(str, str.length() - 1, pat4, pat4.length() - 1));

        System.out.println(isMatchBackTrack(str2, str2.length() - 1, pat4, pat4.length() - 1));

        System.out.println(isMatchBackTrack(str3, str3.length() - 1, pat4, pat4.length() - 1));

    }


    @Test
    public void testStrMatchDynamic () {
        String pat = ".a*b";
        String str = "zaaab";
        String str2 = "cb";
        System.out.println(isMatchDynamic(str, pat));
        System.out.println(isMatchDynamic(str2, pat));

        String str3 = "amnb";
        String pat3 = "a..b";

        System.out.println(isMatchDynamic(str3, pat3));

    }


    /**
     * 字符匹配的情况
     * 1.字符相等肯定是匹配的
     * 2.「.」与字符进行匹配
     * 3。字符不匹配但是字符后面有「*」 可以跳过不进行匹配
     * 4. 字符匹配到有「*」时的逻辑，
     * 如果*前的一个字符是「.」就匹配 或者 「*」前的字符和str中的字符匹配
     */
    private boolean isMatchForce(String str, String pat) {
        int i = 0, j = 0;
        while (i < str.length() && j < pat.length()) {
            if (str.charAt(i) == pat.charAt(j) //字符相等
                    || pat.charAt(j) == '.') { //字符等于「.」
                i++;
                j++;
            } else if (j < pat.length() - 1 && pat.charAt(j + 1) == '*') { //字符串不匹配，但是不匹配的字符后面有*
                j = j + 2;
            } else if (pat.charAt(j) == '*') {   // 当前字符为*，且前pat前一个字符和str匹配，或者pat前一个字符为*
                if (pat.charAt(j - 1) == '.' || pat.charAt(j - 1) == str.charAt(i)) {
                    i++;
                    if (i == str.length()) j++;
                } else {
                    j++;
                }
            }
        }
        return i == str.length() && j == pat.length();
    }


    /**
     * 使用回溯算法
     */
    private boolean isMatchBackTrack(String str, int strIndex, String pat, int patIndex) {
        if (strIndex == -1 && patIndex == -1) return true;
        if (strIndex == -1 && patIndex == 1 && pat.charAt(0) == '.') return true;

        // 字符相等 或者 模式串的字符等于「.」
        if (str.charAt(strIndex) == pat.charAt(patIndex) || pat.charAt(patIndex) == '.') {
            return isMatchBackTrack(str, strIndex - 1, pat, patIndex - 1);
        }

        //字符不匹配时，
        if (pat.charAt(patIndex) == '*' && patIndex - 1 >= 0) {
            if (pat.charAt(patIndex - 1) == str.charAt(strIndex) || pat.charAt(patIndex - 1) == '.') {
                return isMatchBackTrack(str, strIndex - 1, pat, patIndex);
            } else {
                return isMatchBackTrack(str, strIndex, pat, patIndex - 2);
            }
        }
        return false;

    }

    /**
     * 思路分析：
     * 状态有哪些 i，j  i：代表模式串第i个字符，j：代表文本串的第j个字符
     * dp[i][j] = 1  表示模式串的第i个字符和文本串的第j个字符是匹配的；
     * <p>
     * 目标dp[pat.length][str.length]
     * dp[i-2][j]
     * dp[i-1][j-1]
     * dp[i][j-1]     dp[i][j]
     * <p>
     * 状态转移方程：
     * <p>
     * if (pat.charAt(i) == pat.charAt(j) || pat.charAt(i) == '.') {
     * dp[i][j] = dp[i-1][j-1]
     * } else if (pat.charAt(i) == '*' && i -1 > 0) {
     * if (pat.charAt(i-1) == '.' || pat.charAt(i-1) == str.charAt(j)) {
     * dp[i][j] = dp[i][j-1];
     * } else {
     * dp[i][j] = dp[i-2][j]
     * }
     * }
     */


    private boolean isMatchDynamic(String str, String pat) {

        int[][] dpTable = new int[pat.length()+1][str.length() + 1];
        //dp[0][*] = 0; dp[0][0] = 1;
        dpTable[0][0] = 1;
        for (int i = 1; i <= pat.length(); i++) {
            for (int j = 1; j <= str.length(); j++) {

                if (pat.charAt(i-1) == str.charAt(j-1) || pat.charAt(i-1) == '.') {
                    dpTable[i][j] = dpTable[i - 1][j - 1];
                } else if (pat.charAt(i-1) == '*' && i - 2 >= 0) {

                    if (pat.charAt(i - 2) == '.' || pat.charAt(i - 2) == str.charAt(j-1)) {
                        dpTable[i][j] = dpTable[i][j - 1];
                    } else {
                        if (j-2 >= 0) {
                            dpTable[i][j] = dpTable[i - 2][j];
                        }
                    }
                } else {
                    dpTable[i][j] = 0;
                }
            }

        }
        return dpTable[pat.length()][str.length()] == 1;
    }

}
