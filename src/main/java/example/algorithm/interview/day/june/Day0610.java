package example.algorithm.interview.day.june;

import org.junit.Test;

/**
 * @ClassName Day0910 最长无重复字串
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485141&idx=1&sn=0e4583ad935e76e9a3f6793792e60734&chksm=9bd7f8ddaca071cbb7570b2433290e5e2628d20473022a5517271de6d6e50783961bebc3dd3b&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/12 8:25 上午
 **/
public class Day0610 {


    /**
     * 给定一个字符串，找出其中不包含重复字符的最长字串的长度；
     */
    @Test
    public void testLongestNoDuplicateStr() {
        LongestSubstring myLongest = new MyLongestSubstring();
        System.out.println(myLongest.longestNoDuplicateSub("bbbbbbb"));
        System.out.println(myLongest.longestNoDuplicateSub("pwwkew"));
        System.out.println("--------------------------");
        LongestSubstring finalLongestSubstring = new FinalLongestSubstring();
        System.out.println(finalLongestSubstring.longestNoDuplicateSub("bbbbbbb"));
        System.out.println(finalLongestSubstring.longestNoDuplicateSub("pwwkew"));
    }

    private static class FinalLongestSubstring implements LongestSubstring {
        @Override
        public String longestNoDuplicateSub(String content) {
            int[] accountMap = new int[256];
            int left = 0, right = 0;
            String result = null;
            while (right < content.length()) {
                char rightChar = content.charAt(right++);
                accountMap[rightChar]++;

                while (accountMap[rightChar] > 1) {
                    char leftChar = content.charAt(left++);
                    accountMap[leftChar]--;
                }
                result = (result == null || result.length() < right - left) ? content.substring(left, right) : result;
            }
            return result;
        }
    }


    //收缩左侧指针的条件：右侧执政小于content.length 且 accountBook[content.charAt(right)] != 0
    private class MyLongestSubstring implements LongestSubstring {
        @Override
        public String longestNoDuplicateSub(String content) {
            int[] accountBook = new int[256];
            int left = 0, right = 0;
            String res = null;
            while (right < content.length()) {
                char rightChar = content.charAt(right++);
                accountBook[rightChar]++;
                //判断是否要收缩左侧窗口的时候，判断条件过于冗余 accountBook[rightChar] > 1
                while (right < content.length() && accountBook[content.charAt(right)] != 0) {
                    res = (res == null || res.length() < right - left) ? content.substring(left, right) : res;

                    char leftChar = content.charAt(left++);
                    accountBook[leftChar]--;
                }
            }

            return res;
        }
    }


    /**
     * 最长无重复字符的字串接口类
     */
    private interface LongestSubstring {
        String longestNoDuplicateSub(String content);
    }
}
