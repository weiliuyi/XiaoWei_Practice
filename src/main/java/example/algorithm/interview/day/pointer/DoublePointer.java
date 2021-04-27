package example.algorithm.interview.day.pointer;

import example.algorithm.interview.day.april.Day0420;
import example.algorithm.interview.test.SlidingWindow;
import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName DoublePointer 双指针
 * @Description https://mp.weixin.qq.com/s/ioKXTMZufDECBUwRRp3zaA 滑动窗口
 * @Description https://mp.weixin.qq.com/s/yLc7-CZdti8gEMGWhd0JTg 双指针技巧
 * @Author weiliuyi
 * @Date 2021/4/26 5:50 下午
 * @see SlidingWindow
 * @see Day0420
 **/
public class DoublePointer {

    /**
     * 滑动窗口防滑剂
     * <p>
     * 链表字串数组题，用双指针别犹豫；
     * 双指针家三兄弟，各个都是万人迷；
     * <p>
     * 快慢指针最神奇，链表操作无压力；
     * 归并排序找中点，链表成环搞判定；
     * <p>
     * 左右指针最常见，左右两端相向行；
     * 反转数组要靠它，二分搜索是弟弟；
     * <p>
     * 滑动窗口老猛男，字串问题全靠它，
     * 左右指针滑窗口，一前一后齐头进；
     * <p>
     * 滑动窗口伪代码：
     */


    /**
     * 此写法都有一个bug，那就是pat(模式串)都不能包含重复的字符串
     * 这是应为我们通用的判定条件都是：pat.length == valid;
     *
     * 正确的判断逻辑是：模式串中不同字符的数量(不应该是pat的长度) == valid
     */
    @Test
    public void testSlidingWindow() {
        String source = "ADOBECODEBANC";
        String target = "ABC";
        System.out.println(slidingWindow(source, target));
        System.out.println(slidingWindowFormat(source, target));

        System.out.println(slidingWindowV2(source, target));
    }


    @Test
    public void testCheckInclusion() {
        String s1 = "ab";
        String s2 = "eidbaooo";
        System.out.println(checkInclusion(s1, s2));

        String s11 = "ab";
        String s22 = "eidboaoo";
        System.out.println(checkInclusion(s11, s22));

        System.out.println(checkInclusionV2(s1, s2));
        System.out.println(checkInclusionV2(s11, s22));
    }


    @Test
    public void testFindAnagram() {
        String s = "cbaebabacd";
        String p = "abc";
        System.out.println(Arrays.toString(findAnagrams(s,p)));
    }


    @Test
    public void longestSubstringLength () {
        String s1 = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(s1));
        System.out.println(lengthOfLongestSubstringV2(s1));

        String s2 = "bbbbb";
        System.out.println(lengthOfLongestSubstring(s2));
        System.out.println(lengthOfLongestSubstringV2(s2));

        String s3 = "pwwkew";
        System.out.println(lengthOfLongestSubstring(s3));
        System.out.println(lengthOfLongestSubstringV2(s3));
    }


    /**
     * 给你一个字符串S，和一个字符环T，请在S中找出：包含所有字母的最小字串；
     * <p>
     * 滑动窗口的思路：
     * 1。我们在字符串S使用双指针的左右指针技巧，初始化 left=right=0,把索引左开右闭[left,right)称为一个窗口
     * 2。我们不断增加right指针扩大窗口[left,right),直到窗口中的字符串符合要求（包含了T中所有的字符）
     * 3。此时我们停止增加right，转而不断增加left指针缩小窗口[left,right),直到窗口中的字符串不在符合要求（不包含T中的所有字符）
     * 同时，每次增加left，我们都要更新一轮结果；
     * 4。重复第2步和第3步，直到right到达字符串S的尽头
     * <p>
     * 第2步：相当于寻找一个「可行解」，然后第3步优化这个「可行解」，最终找到最优解；
     */

    private String slidingWindow(String source, String target) {
        int[] window = new int[256];
        int count = 0;
        for (int i = 0; i < target.length(); i++) {
            window[target.charAt(i)]++;
            count++;
        }
        String result = null;

        int left = 0, right = 0;

        while (right < source.length()) {

            char c = source.charAt(right++);// 右指针
            if (window[c] > 0) {
                count--;
            }
            window[c]--;

            while (count == 0) {

                if (result == null || result.length() > (right - left)) {
                    result = source.substring(left, right);
                }

                if (window[source.charAt(left)] >= 0) {
                    count++;
                }
                window[source.charAt(left)]++;
                left++;
            }

        }

        return result;
    }

    /**
     * 精简代码，更加简练
     */
    private String slidingWindowFormat(String source, String target) {
        int[] window = new int[256];
        int windowCount = 0;
        for (int i = 0; i < target.length(); i++) {
            window[target.charAt(i)]++;
            windowCount++;
        }
        int left = 0, right = 0;
        String result = null;

        while (right < source.length()) {

            int rc = source.charAt(right++);
            if (window[rc]-- > 0) {
                windowCount--;
            }

            while (windowCount == 0) {
                if (result == null || result.length() > right - left) {
                    result = source.substring(left, right);
                }

                int lc = source.charAt(left++);
                if (window[lc]++ >= 0) {
                    windowCount++;
                }
            }

        }
        return result;
    }

    /**
     * 思路和上边类似，有略微差别
     * 1.当移动right扩大窗口时，即加入字符，应该更新哪些数据
     * 2。什么情况下，窗口应该停止扩大，开始移动left缩小窗口
     * 3。当移动left缩小窗口，即移除字符时，应该更新哪些数据；
     * 4。我们的结果应该在扩大窗口时还是缩小窗口时进行更新；
     */
    private String slidingWindowV2(String source, String target) {
        int[] targetMap = new int[256], sourceMap = new int[256];
        for (int i = 0; i < target.length(); i++) {
            targetMap[target.charAt(i)]++;
        }
        int left = 0, right = 0, valid = 0;
        String result = null;

        while (right <= source.length()) {
            int rc = source.charAt(right++);
            if (targetMap[rc] != 0) {
                if (targetMap[rc] == ++sourceMap[rc]) valid++;
            }

            while (valid == target.length()) {
                if (result == null || result.length() > right - left) {
                    result = source.substring(left, right);
                }

                int lc = source.charAt(left++);
                if (targetMap[lc] != 0) {
                    if (targetMap[lc] == sourceMap[lc]--) {
                        valid--;
                    }
                }

            }
        }
        return result;

    }


//    private String slidingWindowForce (String source,String target) {
//        for (int i = 0; i < source.length(); i++) {
//            for (int j = i+1; j < source.length(); j++) {
//                if (source.substring(i,j+1)) {}
//            }
//        }
//    }


    /**
     * 判断两个字符串s1和s2，写一个函数判断s2是否包好s1的排列
     * 换句话说：第一个字符串的排列之一是否是第二个字符串的字串；
     */
    private boolean checkInclusion(String s1, String s2) {
        int[] s1Map = new int[256];
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            s1Map[s1.charAt(i)]++;
            count++;
        }

        int left = 0, right = 0;
        while (right < s2.length()) {
            if (count == 0) return true;

//            char rc = s2.charAt(right++); //此时一定要注意right的值，已经变化，
//            if (right <= s1.length()) {   再次使用right 容易产生bug
            char rc = s2.charAt(right);
            if (right++ < s1.length()) {
                if (s1Map[rc]-- > 0) count--;
                continue;
            }

            char lc = s2.charAt(left++);

            if (s1Map[rc]-- > 0) count--;
            if (s1Map[lc]++ >= 0) count++;
        }

        return false;

    }

    /**
     * 思路更加巧妙，而且更加容易理解
     * <p>
     * 1。本题移动left缩小窗口的时机是窗口大小大于或者等于s1.length()时，因为是排列，显然长度应该是一样的
     * 2。当发现valid == s1.length()时，就说明窗口中就是一个合法排列
     */
    private boolean checkInclusionV2(String s1, String s2) {
        int[] s1Map = new int[256], s2Map = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            s1Map[s1.charAt(i)]++;
        }
        int left = 0, right = 0, valid = 0;

        while (right < s2.length()) {
            int rc = s2.charAt(right++);
            if (s1Map[rc] > 0) {
                if (++s2Map[rc] == s1Map[rc]) {
                    valid++;
                }
            }

            while (right - left >= s1.length()) {
                if (valid == s1.length()) return true;

                int lc = s2.charAt(left++);
                if (s1Map[lc] > 0) {
                    if (s1Map[lc] == s2Map[lc]--) valid--;
                }
            }

        }

        return false;
    }


    /**
     * 给定一个字符串s和一个非空字符串p，找到s中所有是p的字母异位词的字串，返回这些字串的起始索引。
     * 字母异位词指字母相同，但是排列不同的字符串
     */
    private int[] findAnagrams (String s,String p) {
        int[] sMap = new int[256],pMap = new int[256];
        for (int i = 0; i < p.length(); i++) {
            pMap[p.charAt(i)]++;
        }
        int[] result = new int[s.length()-p.length()+1];
        Arrays.fill(result,-1);
        int left = 0,right = 0,valid = 0,rIndex = 0;

        while (right < s.length()) {

            int rc = s.charAt(right++);

            if (pMap[rc] > 0) {
                if (++sMap[rc] == pMap[rc] ) {
                    valid++;
                }
            }
            while (right - left >= p.length()) {

                if (valid == p.length()) {
                    result[rIndex++] = left;
                }

                int lc = s.charAt(left++);
                if (pMap[lc] > 0) {
                    if (pMap[lc] == sMap[lc]--) {
                        valid--;
                    }
                }
            }

        }
        return result;
    }

    /**
     * 最长无重复字串
     *
     * 给定一个字符串，请你找出不包含重复字符的最长字串的长度
     */

    int lengthOfLongestSubstring (String s) {
        int [] sMap = new int[256];

        int left=0,right=0,result = 0;

        while (right < s.length()) {

            int rc = s.charAt(right);
            if (sMap[rc] == 0) { //判断此时right指针是否重复，此时发生无重复
                right++;
                sMap[rc]++;
                continue;
            }

            while (sMap[rc] != 0) { // 右侧指针不能在扩展，会产生重复，搜索指针；
                result = Math.max(result,right-left);

                int lc = s.charAt(left++);
                sMap[lc]--;
            }

        }
        return result;
    }


    /**
     * 另外一种思路
     */
    int lengthOfLongestSubstringV2(String s) {
        int[] sMap = new int[256];

        int left = 0,right = 0;
        int result = 0;

        while (right < s.length()) {
            int rc = s.charAt(right++);
            sMap[rc]++; //窗口内数据进行更新

            while (sMap[rc] > 1) { //左侧窗口是否需要收缩
                int lc = s.charAt(left++);
                sMap[lc]--;  //窗口内数据进行更新
            }

            //这里更新结果
            result = Math.max(result,right-left);
        }
        return result;
    }


}
