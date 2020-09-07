package example.algorithm.interview.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 一个字符串中最大回文子串
 * @author: weiliuyi
 * @create: 2020--03 16:50
 **/
public class PlalinDromeString {


    @Test
    public void testV1() {
        System.out.println(isPlalindromV1("weiew"));
        System.out.println(isPlalindromV1("weiiew"));
    }

    @Test
    public void testV2() {
        System.out.println(isPlalindromV2("weiew"));
        System.out.println(isPlalindromV2("weiiew"));
    }

    @Test
    public void testV3() {
        String content = "weiiew";
        System.out.println(isPlalindromV3(content, 0, content.length() - 1));
        System.out.println(isPlalindromV3(content, 0, content.length() - 1));
    }


    @Test
    public void testV4() {
        System.out.println(isPlalindromeV4(12344321));
    }


    @Test
    public void testV5() {
        System.out.println(isPlalindromeV4(12344321));
    }


    @Test
    public void testV6() {
        System.out.println(getPlalindromeSeq("abccbadefgaaaa"));
    }

    @Test
    public void testV7() {
        System.out.println(getPlalindromeSeqVer2("abccbadefgaaaa"));
    }

    @Test
    public void testV8() {
        char c1 = 'c';
        char c2 = 'c';
        System.out.println(c1 == c2);
    }


    /**
     * 获得回文子串，中心遍历法；
     * 中心遍历法有两种情况：1.奇数(对称中心为一个字母)，2 偶数(对称中心是两个字母)
     */
    private String getPlalindromeSeqVer2(String content) {
        String result = "";
        for (int i = 0; i < content.length(); i++) {
            String r1 = getPlalindromeSeqByCenter(content, i, i + 1);
            String r2 = getPlalindromeSeqByCenter(content, i, i);
            String temp1 = r1.length() > r2.length() ? r1 : r2;
            result = temp1.length() > result.length() ? temp1 : result;
        }
        return result;
    }

    /**
     * 根据中心向两边扩展，找出最大回文串；
     * @param content 字符串
     * @param cen1 left 指针
     * @param cen2 right 指针
     * @return 返回回文串
     */
    private String getPlalindromeSeqByCenter (String content,int cen1,int cen2) {
        while (cen1 >= 0 && cen2 <= content.length()-1) {
            if (content.charAt(cen1) != content.charAt(cen2)) {
                return content.substring(cen1+1,cen2);
            }
            cen1--;
            cen2++;
        }
       //这个地方比较特殊，跳出循环的时候 cen1 == -1 或者 cen2 == content.length 这两种情况，但是此时是回文串，所有要进行处理，不能返回
        return content.substring(cen1+1,cen2);
    }


    /**
     * 如何找到字符串中的最大回文子串；
     * 使用暴力求解  找到所有的字符串，并且逐个判断是否是回文子串；
     * 优化版本：从大的子串向小的子串遍历；----一旦是回文子串就跳出循环
     */
    private String getPlalindromeSeq(String content) {
        String result = "";
        for (int i = 0; i < content.length(); i++) {
            for (int j = i + 1; j < content.length(); j++) {
                String sub = content.substring(i, j + 1);
                if (isPlalindromV2(sub) && result.length() < sub.length()) {
                    result = sub;
                }
            }
        }
        return result;
    }


    /**
     * 如何判断一个数字是不是回文数
     */
    private boolean isPlalindromeV4(int num) {
        List<Integer> bit = new ArrayList<>();
        while (num > 0) {
            bit.add(num % 10);
            num /= 10;
        }
        for (int i = 0, j = bit.size() - 1; i < j; i++, j--) {
            if (bit.get(i) != bit.get(j)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 使用递归的方式，进行判断；
     *
     * @param content 字符串
     * @param from    字符串的起始位置
     * @param end     字符串的结束位置
     * @return 返回是否是回文子串
     */
    private boolean isPlalindromV3(String content, int from, int end) {
        if (from >= end) {
            return true;
        }
//        if (content.charAt(from) != content.charAt(end)) {
//            return false;
//        } else {
//            return isPlalindromV3(content,++from,--end);
//        }
        //上面注释的简化版本
        return content.charAt(from) == content.charAt(end) && isPlalindromV3(content, ++from, --end);

    }


    /**
     * 逆序字符串，然后比较这两个字符串是否相等
     *
     * @param content 字符串的内容
     * @return 返回是否是字符串
     */
    private boolean isPlalindromV2(String content) {
        if (content == null || content.length() == 0) {
            return false;
        }
        String rContent = new StringBuilder(content).reverse().toString();
        return content.equals(rContent);
    }


    /**
     * 使用for循环，双指针的方式进行判断是否是回文串
     *
     * @param content 字符串的内容
     * @return 返回是否是字符串
     */
    private boolean isPlalindromV1(String content) {
        if (content == null || content.length() == 0) {
            return false;
        }
        for (int left = 0, right = content.length() - 1; left < right; left++, right--) {
            if (content.charAt(left) != content.charAt(right)) {
                return false;
            }
        }
        return true;
    }
}
