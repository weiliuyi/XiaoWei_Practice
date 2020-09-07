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
        System.out.println(isPlalindromV3(content,0,content.length()-1));
        System.out.println(isPlalindromV3(content,0,content.length()-1));
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







    /**
     * 如何找到字符串中的最大回文子串；
     * 使用暴力求解  找到所有的字符串，并且逐个判断是否是回文子串；
     * 优化版本：从大的子串向小的子串遍历；----一旦是回文子串就跳出循环
     *
     */
    private String getPlalindromeSeq (String content) {
        String result = "";
        for (int i = 0;i < content.length();i++) {
            for (int j = i+1; j < content.length();j++) {
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
     *
     */
    private boolean isPlalindromeV4(int num) {
        List<Integer> bit = new ArrayList<>();
        while(num > 0) {
            bit.add(num % 10);
            num /= 10;
        }
        for (int i = 0,j = bit.size()-1;i < j;i++,j--) {
            if (bit.get(i) != bit.get(j)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 使用递归的方式，进行判断；
     * @param content 字符串
     * @param from 字符串的起始位置
     * @param end 字符串的结束位置
     * @return 返回是否是回文子串
     */
    private boolean isPlalindromV3(String content,int from,int end) {
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
