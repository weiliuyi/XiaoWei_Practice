package example.algorithm.interview.day.pointer;

import example.algorithm.interview.day.april.Day0420;
import example.algorithm.interview.test.SlidingWindow;
import org.junit.Test;

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


    @Test
    public void testSlidingWindow() {
        String source = "ADOBECODEBANC";
        String target = "ABC";
        System.out.println(slidingWindow(source, target));
        System.out.println(slidingWindowFormat(source, target));

        System.out.println(slidingWindowV2(source, target));
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

        while (right < source.length()) {
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


}
