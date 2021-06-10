package example.algorithm.interview.day.june;

import org.junit.Test;

/**
 * @ClassName Day0607  滑动窗口问题
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485141&idx=1&sn=0e4583ad935e76e9a3f6793792e60734&chksm=9bd7f8ddaca071cbb7570b2433290e5e2628d20473022a5517271de6d6e50783961bebc3dd3b&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/9 7:45 下午
 **/
public class Day0607 {

    /**
     * 滑动窗口算法的模版代码
     * int left = 0,right = 0;
     * while (right < s.size()) {
     * //扩大窗口
     * window.add(s[right]);
     * right++;
     * while (window need shrink) {
     * //缩小窗口
     * window(s[left]);
     * left++;
     * }
     * }
     */

    @Test
    public void testSlidingWindow() {
        ForceMinWindowSubstring forceWindowSub = new ForceMinWindowSubstring();
        System.out.println(forceWindowSub.slidingWindow("ADOBECODEBANC", "ABC"));
        System.out.println("----------------");
        SlidingWindow<String> myMinWindowSub = new MyMinWindowSubstring();
        System.out.println(myMinWindowSub.slidingWindow("ADOBECODEBANC", "ABC"));

        System.out.println("--------------");
        MyV2MinWindowSubstring myV2MinWindowSub = new MyV2MinWindowSubstring();
        System.out.println(myV2MinWindowSub.slidingWindow("ADOBECODEBANC", "ABC"));

        System.out.println("------------------");
        FinalMinWindowSubstring finalMinWindowSub = new FinalMinWindowSubstring();
        System.out.println(finalMinWindowSub.slidingWindow("ADOBECODEBANC","ABC"));
    }


    /**
     * 左闭又开的区间[left,right)
     */
    private static class FinalMinWindowSubstring implements SlidingWindow<String> {

        @Override
        public String slidingWindow(String content, String target) {
            int[] accountBook = new int[256];
            int account = 0;
            for (int i = 0; i < target.length(); i++) {
                accountBook[target.charAt(i)]++;
                account++;
            }
            int left = 0, right = 0;
            String result = null;
            while (right < content.length()) {

                if (accountBook[content.charAt(right++)]-- > 0) {
                    account--;
                }

                while (account == 0) {
                    result = (result == null || result.length() > right - left) ? content.substring(left, right) : result;
                    if (accountBook[content.charAt(left++)]++ >= 0) {
                        account++;
                    }
                }
            }
            return result;
        }
    }

    /**
     * 最小覆盖字串(Minimum Window Substring)
     * 给你一个字符串S,和一个字符串T,请在字符串S里面找出：包含T所有字母的最小字串
     */
    private static class MyMinWindowSubstring implements SlidingWindow<String> {

        /**
         * 记账算法
         */
        @Override
        public String slidingWindow(String content, String target) {
            int[] accountBook = new int[256];
            int account = 0;
            for (int i = 0; i < target.length(); i++) {
                accountBook[target.charAt(i)]++;
                account++;
            }
            for (int i = 0; i < target.length(); i++) {
                if (accountBook[content.charAt(i)]-- > 0) {
                    account--;
                }
            }
            if (account == 0) return content.substring(0, target.length());

            String res = null;
            int left = 0, right = target.length();
            while (right < content.length()) {
                if (accountBook[content.charAt(right++)]-- > 0) account--;
                while (account == 0) {
                    //更新结果
                    res = (res == null || res.length() > (right - left)) ? content.substring(left, right) : res;
                    if (accountBook[content.charAt(left++)]++ >= 0) {
                        account++;
                    }
                }
            }
            return res;
        }
    }

    /**
     * 按照自己的逻辑实现滑动窗口
     */
    private static class MyV2MinWindowSubstring implements SlidingWindow<String> {

        @Override
        public String slidingWindow(String content, String target) {
            int[] accountBook = new int[256];
            int account = 0;
            for (int i = 0; i < target.length(); i++) {
                accountBook[target.charAt(i)]++;
                account++;
            }
            int left = 0, right = 0;
            boolean isRight = true;
            String res = null;
            while (right < content.length() || !isRight) { //为什么要加 !isRight这个关键字，这是因为右侧的指针right== content.length时，左侧的指针也需要搜索；
                if (isRight) {
                    if (accountBook[content.charAt(right++)]-- > 0) {
                        account--;
                    }
                } else {
                    if (accountBook[content.charAt(left++)]++ >= 0) {
                        account++;
                    }
                }
                if (account == 0) {
                    res = (res == null || res.length() > (right - left)) ? content.substring(left, right) : res;
                    isRight = false;
                } else {
                    isRight = true;
                }
            }
            return res;
        }
    }


    /**
     * 滑动敞口暴力求解法
     */
    private static class ForceMinWindowSubstring implements SlidingWindow<String> {

        @Override
        public String slidingWindow(String content, String target) {
            String res = null;
            int left = 0, right = target.length() - 1;
            while (right < content.length()) {
                String substring = content.substring(left, right + 1);
                if (!containTarget(substring, target)) {
                    right++;
                    continue;
                }
                res = (res == null || res.length() > substring.length()) ? substring : res;
                left++;
            }
            return res;
        }

        private boolean containTarget(String content, String target) {
            for (int i = 0; i < target.length(); i++) {
                if (!content.contains(target.charAt(i) + "")) return false;
            }
            return true;
        }
    }


    /**
     * 滑动窗口的接口类
     *
     * @param <T>
     */
    private interface SlidingWindow<T> {
        T slidingWindow(String content, String target);
    }

}
