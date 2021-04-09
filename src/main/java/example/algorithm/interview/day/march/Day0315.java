package example.algorithm.interview.day.march;

/**
 * @description: 最大回文子串
 * @author: weiliuyi
 * @create: 2021--15 13:40
 **/
public class Day0315 {

    public static void main(String[] args) {
        System.out.println(getLongestForce("weiliuyiiyuiliew"));
        System.out.println(getLongestPalindromeByCenter("wei1liuyiiyuiliew"));
        System.out.println(getLongestPalindromeByDynamic("wei1liuyiiyuiliew"));
    }


    /**
     * 暴力求解,
     */

    public static String getLongestForce (String content) {
        if (content == null || content.length() < 2) {
            return content;
        }
        //所有的子串
        int maxlen = 0;
        String maxContent = "";
        for (int i = 0; i < content.length() -1 ;i++) { //开始字符
            for (int j = i;j < content.length();j++) { //结束字符
               /* System.out.println("i  = " + i + " j = " + j + " subString = " + content.substring(i,j+1));*/
                if (!isPalindrome(content,i,j)) {
                    continue;
                }
                if (maxlen < (j - i + 1)) {
                    maxlen = j-i + 1;
                    maxContent = content.substring(i,j+1);
                }
            }
        }
//        System.out.println("maxContent = " + maxContent + " maxLen = " + maxlen);
        return maxContent;
    }

    private static boolean isPalindrome (String content,int start,int end) {
        while (start < end) {
            if (content.charAt(start) != content.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }




    /**
     * 中心扩散
     */






    public static String getLongestPalindromeByCenter (String content) {
        if (content == null || content.length() < 2) {
            return content;
        }
        int maxLen = 0;
        int start = 0;
        for (int i = 0; i < content.length();) {
            int left = i;
            int right = i;
            while (right < content.length()-1 && content.charAt(right +1) == content.charAt(right) ) {
                right++;
            }
            i = right+1;
            while (left > 0 && right < content.length()-1 && content.charAt(left-1) == content.charAt(right+1)) {
                left--;
                right++;
            }
            if (right -  left + 1 > maxLen) {
                maxLen = right - left + 1;
                start = left;
            }
        }
        return content.substring(start,start + maxLen);
    }


    /**
     * 动态规划(i < j ) 对角线的上部分
     * dp[i][j] = 0 代表 从下标i---->j 不是回文串
     * dp[i][j] = 1 代表 从下标i----->j是回文串
     *
     * if (dp[i][j] == 1 && content.charAt(i-1) == content.chatAt(j+1)) {
     *     dp[i-1][j+1]  = 1;
     * } else {
     *     dp[i-1][j+1] = 0
     * }
     *
     *
     * 初始化状态：
     * i == j dp[i][j] == 1
     */

    public static String getLongestPalindromeByDynamic (String content) {
        if (content == null || content.length() < 2) {
            return content;
        }
        int[][] dp = new int[content.length()][content.length()];
        //初始化dp数组
        for (int i = 0; i <content.length();i++) {
            dp[i][i] = 1;
            int right = i;
            while (right < content.length() -1 && content.charAt(right) == content.charAt(right+1)) {
                dp[i][++right] = 1;
            }
        }
        int maxStart = 0;
        int maxLen = 0;
        for (int i = 0; i < content.length();i++) {

            int left = i;
            int right = i;
            while (right < content.length() -1 && dp[left][right+1]  == 1) {
                right++;
            }
            while(left > 0 && right < content.length()-1 && dp[left][right] == 1 && content.charAt(left-1) ==content.charAt(right+1) ){
                dp[--left][++right] = 1;
            }
            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }
        }
        return content.substring(maxStart,maxStart+maxLen);
    }

}
