package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0409
 * @Description 字符串匹配问题
 * @Author weiliuyi
 * @Date 2021/4/9 4:35 下午
 **/
public class Day0409 {


    @Test
    public void testForce() {
        String content = "ababcabcacbab";
        String pattern = "abcac";
        System.out.println(getPatternIndexForce(content, pattern));
        System.out.println(getPatternIndexKMP(content, pattern));
    }


    @Test
    public void testDynamic () {
        KMPDynamic kmpDynamic = new KMPDynamic("abcac");
        System.out.println(kmpDynamic.search("ababcabcacbab"));;
    }

    @Test
    public void testNextArray() {
        String pattern = "abcac";
        System.out.println(Arrays.toString(nextArrayForceV2(pattern)));
        System.out.println(Arrays.toString(nextArrayNoRecursive(pattern)));
        printNextArray(pattern);
        System.out.println("");
        int[] nextArray = new int[pattern.length()];
        Arrays.fill(nextArray, -1);
        nextArrayRecursive(pattern, nextArray, pattern.length() - 1);
        System.out.println(Arrays.toString(nextArray));
    }


    /**
     * 暴力进行字符串的匹配
     *
     * @param content 目标内容
     * @param pattern 需要匹配的模式串
     * @return 下标
     */
    private int getPatternIndexForce(String content, String pattern) {
        if (pattern.length() > content.length()) return -1;

        for (int i = 0; i <= content.length() - pattern.length(); i++) {//其实 len=content.length()- pattern.length() + 1 个字符 下标[0,len-1]
            int j = 0;
            for (; j < pattern.length(); j++) {
                if (pattern.charAt(j) != content.charAt(j + i))
                    break;
            }
            if (j == pattern.length()) return i;
        }
        return -1;
    }

    /**
     * 使用KMP进行进行匹配字符串
     *
     * @param content 字符串内容
     * @param pattern 模式串
     * @return index
     */
    private int getPatternIndexKMP(String content, String pattern) {
        //int[] next = nextArray(pattern);
        int[] next = nextArrayForceV2(pattern);
        int j = 0;
        for (int i = 0; i <= content.length(); ) {
            //这个地方 i <= content.length() - pattern.length()是错误的？？
            // 为什么？ 这是因为此时i是不同于暴力匹配的；暴力匹配是此时还没有匹配，往后的字符串就不可能进行匹配了，因为待匹配的字符串长度小于模式串；
            if (content.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                //如果此时的串的长度已经结束
                if (j == pattern.length()) {
                    return i - j; // i - (j + 1) + 1
                }
                continue;
            }
            if (j == 0) {
                i++;
                continue;
            }
            j = next[j - 1];
        }
        return -1;

        /*代码优化： 逻辑简介
         *   int i = 0,j = 0;
         *   while (i < content.length()) {
         *       if (content.charAt(i) == pattern.charAt(j)) {
         *           i++;
         *           j++;
         *           if (j == pattern.length()) return i - j;
         *       } else {
         *           if (j == 0) {
         *                 i++;
         *                continue;
         *           }
         *           j = next[j-1] + 1;
         *       }
         *
         *   }
         *
         */
    }
    //aba 错误的求解  这所求的结果，关于中心对称的结果；abcdef ........fedcab 这是不正确的
    //前缀：除了最后一个字符的所有字串
    //后缀：除了第一个字符的所有字串；
    //正确的结果：最长公共前后缀

    private int[] nextArray(String pattern) {//错误 理解错误
        int[] next = new int[pattern.length()];
        next[0] = -1;
        for (int i = 1; i < pattern.length(); i++) {
            int begin = 0, end = i;
            while (begin < end) {
                if (pattern.charAt(begin) != pattern.charAt(end)) break;
                begin++;
                end--;
            }
            next[i] = begin - 1;
        }
        System.out.println(Arrays.toString(next));
        return next;
    }


    /**
     * 暴力求解next数组
     * <p>
     * next数组：当前数组作为最后一个字符时，
     *
     * @param pattern 模式串
     * @return next数组
     */

    //最长公共前后缀的 正确逻辑
    private int[] nextArrayForceV2(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            //System.out.println("subString = " + pattern.substring(0,i+1));
            // i+1 标识长度  字串的最长为 i
            for (int j = i; j > 0; j--) { //j代表 前缀或者后缀的最大长度
                int start = i - j + 1;   // start [i-j+1,i]
                int k = 0;
                //while (start < pattern.length()) { //易犯错的地方，此时是求pattern.substring(0,i+1)字串的最长前后缀长度
                //所以此时退出循环的条件是start <=i 而非 start < pattern.length();
                while (start <= i) {
                    if (pattern.charAt(start) != pattern.charAt(k)) {
                        break;
                    }
                    start++;
                    k++;
                }
                //两种情况 start >= i 或者 pattern.charAt(start) == pattern.charAt(k)
                if (start > i) {
                    next[i] = k;
                    //System.out.println("k = " + k);
                    break;
                }
                next[i] = 0;
            }
        }
        return next;
    }


    private int[] nextArrayNoRecursive(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1; i < pattern.length(); i++) { //求的每个字串的最长公共前后缀
            int temp = i;
            while (true) {
                int len = next[temp - 1];
                if (pattern.charAt(len) == pattern.charAt(i)) {
                    next[i] = len + 1;
                    break;
                }
                if (len == 0) {
                    next[i] = 0;
                    break;
                }
                temp = len;
            }
        }
        return next;
    }

    private void printNextArray(String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            System.out.print(nextArrayRecursive(pattern, i) + ",");

        }
    }

    /**
     * 使用递归的方法，进行求解next数组
     *
     * @param pattern 模式串
     * @param index   下标
     * @return 最长公共前后缀的长度
     */
    private int nextArrayRecursive(String pattern, int index) {
        if (index < 0)
            return -1;
        if (index == 0)
            return 0;
        int len = nextArrayRecursive(pattern, index - 1);
        while (len >= 0) {
            if (pattern.charAt(len) == pattern.charAt(index)) {
                return len + 1;
            }
            len = nextArrayRecursive(pattern, len - 1);
        }
        return 0;
    }

    //使用带有备忘录的递归进行求解next数组
    private int nextArrayRecursive(String pattern, int[] nextMemo, int index) {
        if (index < 0) return -1;
        if (index == 0) {
            return nextMemo[0];
        }
        if (nextMemo[index] != -1)
            return nextMemo[index];
        int len = nextArrayRecursive(pattern, nextMemo, index - 1);
        while (len >= 0) {
            if (pattern.charAt(len) == pattern.charAt(index)) {
                nextMemo[index] = len + 1;
                return nextMemo[index];
            }
            len = nextArrayRecursive(pattern, nextMemo, len - 1);
        }
        nextMemo[index] = 0;
        return 0;
    }


}

/**
 * 使用使用带台规划求解字符串匹配问题
 * 确定有限状态自动机，进行求解；
 * <p>
 * https://mp.weixin.qq.com/s/2xzHt42Bd8uoWhhnaQfduw
 */
class KMPDynamic {

    private String pattern;

    private int[][] dpTable;


    public KMPDynamic(String pattern) {
        this.pattern = pattern;
        this.dpTable = new int[pattern.length()][256];
        initDpTable(pattern);
    }


    /*
     * 使用动态规划求解KMP算法
     *
     * 学名：确定有限状态自动机
     *
     * 状态转移的行为：明确两个变量，一个是当前匹配的状态，另一个是遇到的字符
     * 描述状态转移图：定义一个二维dp数组
     * dp[j][c] = next 表示当前状态j，遇到了字符c，应该转移到状态next
     * 0 <= j < M 代表当前状态
     * 0 <= c < 256 代表遇到的字符
     * 0 <= next < M 代表下一个状态
     * dp[4]['A'] = 3 :当前状态是4，如果遇到'A' pattern 应该转移到3
     * dp[1]['B'] = 2 : 表示当前状态是1，如果遇到字符'B'，pattern应该转移到状态2
     */
    void initDpTable(String pattern) {
        //base case 其他都为0
        dpTable[0][pattern.charAt(0)] = 1;

        int x = 0;
        //状态从0 -》 pattern.length() -1;
        for (int i = 1; i < pattern.length(); i++) {

            for (int ch = 0; ch < 256; ch++) {
                if (pattern.charAt(i) == ch) { //状态推进
                    dpTable[i][ch] = i+1;
                    continue;
                }
                //状态重置
                dpTable[i][ch] = dpTable[x][ch];
            }
            //更新影子的状态
            x = dpTable[x][pattern.charAt(i)];

        }
    }

    /**
     * content匹配字符串pattern
     *
     * @param content txt
     * @return start index
     */
    public int search(String content) {
        int m = pattern.length();
        int n = content.length();
        int j = 0;
        for (int i = 0;i < n;i++) {
            j = dpTable[j][content.charAt(0)];

            if (j == m)
                return i - m + 1;
        }
        return -1;
    }


}
