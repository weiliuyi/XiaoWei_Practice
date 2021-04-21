package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Exercise
 * @Description
 * @Author weiliuyi
 * @Date 2021/4/21 10:58 上午
 **/
public class Day0421Exercise {


    /**
     * KMP练习
     */
    @Test
    public void testNextArray() {
        String pattern = "abcac";
        System.out.println(Arrays.toString(nextArrForce(pattern)));

        System.out.println(Arrays.toString(nextArrNoRecur(pattern)));

        int[] next = new int[pattern.length()];
        Arrays.fill(next, -1);
        nextArrRecur(pattern, next, pattern.length() - 1);
        System.out.println(Arrays.toString(next));
    }

    /**
     * KMP 使用next数组进行求解
     */

    @Test
    public void testKMP() {
        System.out.println(strMatchForce("abcweiliuyiajdakn", "weiliuyi"));

        System.out.println(strMatchKMP("abcweiliuyiajdakn", "weiliuyi"));

        System.out.println(searchDynamic("abcweiliuyiajdakn","weiliuyi"));
    }


    /**
     * 暴力匹字符串
     */
    private int strMatchForce(String content, String pattern) {
        if (pattern.length() > content.length()) return -1;

        for (int i = 0; i < content.length() - pattern.length() + 1; i++) {
            /*int k = i,j=0;
            while (j < pattern.length()) {
                if (content.charAt(k) != pattern.charAt(j)) break;
                k++;
                j++;
            }
            if (j == pattern.length()) return i;*/
            //更优雅的写法 相比上边的写法，少生命了一个变量
            int j = 0;
            while (j < pattern.length()) {
                if (content.charAt(j + i) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) return i;
        }
        return -1;
    }

    /**
     * 使用KMP算法 进行匹配字符串
     */
    private int strMatchKMP(String content, String pattern) {
        int[] next = nextArrForce(pattern); //next[i] = a 代表 pattern串的从下标[0，i]的最长的 前缀 后缀 长度
        int j = 0;// pattern字符串的下标
        int i = 0;//content字符串的下标
        while (i < content.length()) {
            if (content.charAt(i) != pattern.charAt(j)) {
                if (j == 0) {
                    i++;
                } else {
                    j = next[j];
                }
                continue;
            }

            i++;
            j++;

            if (j == pattern.length()) return i - j;
        }

        /* 相比的优点：
         *  1.没有使用else
         *  2.代码结构更优化
         * int i = 0,j = 0;
         * while (i < content.length) {
         *     if (content.chatAt(i) == pattern.charAt(j)) {
         *         i++;
         *         j++;
         *         if (j == pattern.length()) return i - j;
         *     }
         *     if (j == 0) {
         *         i++;
         *         continue;
         *     }
         *     j = next[j];
         * }
         */
        return -1;
    }

    /**
     * 暴力求解next数组
     */
    private int[] nextArrForce(String pattern) {
        int[] next = new int[pattern.length()]; //next 数组中存放最长公共前缀 后缀的长度
        next[0] = 0;
        for (int i = 1; i < pattern.length(); i++) { // i 表示next数组的下标
            //模式串的 [0,i]的可能最长的前缀 后缀的长度为i
            for (int j = i; j > 0; j--) {  //j表示模式串从[0,i]的匹配的长度，j从大到小，一旦匹配成功，立刻终止循环
                int start = i - j + 1;
                int k = 0;
                while (start <= i) {
                    if (pattern.charAt(start) != pattern.charAt(k)) {
                        break;
                    }
                    k++;
                    start++;
                }
                if (start > i) {
                    next[i] = k;//其实是[0,k-1]长度为k
                    break;
                }
            }
        }
        return next;
    }

    private int[] nextArrNoRecur(String pattern) {
        int[] nextArr = new int[pattern.length()];

        for (int i = 1; i < pattern.length(); i++) {
            int j = nextArr[i - 1];
            while (pattern.charAt(j) != pattern.charAt(i) && j > 0) {
                j = nextArr[j - 1];
            }
            // 三种情况：
            // 不相等 j==0     arr[i] = 0;
            // 相等 j > 0     arr[i] = j + 1;
            // 相等 j == 0    arr[i] = j + 1;
//            if (j != 0) { 错误的思考逻辑
//                nextArr[i] = j + 1;
//            }
            if (pattern.charAt(j) == pattern.charAt(i)) {
                nextArr[i] = j + 1;
                continue;
            }
            nextArr[i] = 0;// 这一步可以省略，int[] 数组的默认值就是0
        }
        return nextArr;

        /* 第二种 编码方式
         * 1.变量命名
         * 2.逻辑更加简单
         * for (int i = 1;i < pattern.length();i++) {
         *      int len = nextArr[i-1];
         *      while (true) {
         *          if (content.charAt(i)  == pattern.charAt(len)) {
         *              next[i] = len + 1; //存的是长度，[0,len]的长度是len + 1;
         *              break;
         *          }
         *          if (len == 0) {
         *              next[i] = 0;
         *              break;
         *          }
         *          len = nextArr[len -1];
         *      }
         * }
         *
         */

    }

    private int nextArrRecur(String pattern, int[] next, int endIndex) {
        if (endIndex < 0) return -1;

        if (endIndex == 0) return 0;

        if (next[endIndex] != -1) return next[endIndex];

        int len = nextArrRecur(pattern, next, endIndex - 1);
        while (len != -1 && pattern.charAt(len) != pattern.charAt(endIndex)) {
            len = nextArrRecur(pattern, next, len - 1);
        }
        if (len != -1) {
            next[endIndex] = len + 1;
            return len + 1;
        }
        next[endIndex] = 0;
        return 0;

    }

    /**
     * dpTable[i][c] = next
     * 0 <= i < pattern.length
     * c代表字符     0<= c <= 255
     * next 表示下一个状态
     * 状态i 遇到字符c 下一个状态next
     */

    private int[][] patternDpTable(String pattern) {
        int[][] dpTable = new int[pattern.length()][256];

        dpTable[0][pattern.charAt(0)] = 1;
        int x = 0;
        for (int i = 1; i < pattern.length(); i++) {
            for (int c = 0; c < 256; c++) {
                if (pattern.charAt(i) == c) {
                    dpTable[i][c] = i + 1;
                } else {
                    dpTable[i][c] = dpTable[x][c];
                }
            }
            x = dpTable[x][pattern.charAt(i)];
        }
        return dpTable;
    }

//
//
//    错误的思路
//    private int searchDynamic (String content,String pattern) {
//        int[][] dpTable = patternDpTable(pattern);
//        int j = 0;
//        for (int i = 0; i < content.length(); ) {
//            if (content.charAt(i) == pattern.charAt(j)) {
//                j++;
//                i++;
//            } else {
//                j = dpTable[j][content.charAt(i)];
//            }
//        }
//    }

    /**
     * 使用动态规划进行搜索
     */
    private int searchDynamic(String content, String pattern) {
        int[][] dpTable = patternDpTable(pattern);
        int j = 0;
        for (int i = 0; i < content.length(); i++) {

            j = dpTable[j][content.charAt(i)];

            if (j == pattern.length()) return i - pattern.length() + 1;
        }
        return -1;
    }

}
