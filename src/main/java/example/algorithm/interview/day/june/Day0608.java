package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0608 字符串排列
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485141&idx=1&sn=0e4583ad935e76e9a3f6793792e60734&chksm=9bd7f8ddaca071cbb7570b2433290e5e2628d20473022a5517271de6d6e50783961bebc3dd3b&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/11 1:08 下午
 **/
public class Day0608 {

    /**
     * 字符串的排列问题
     * 给定两个字符串s1和s2，写出一个s2是否包含s1排列
     */

    @Test
    public void testStringArrange () {
        StringInclusion forceInclusion = new ForceStringInclusion();
        System.out.println(forceInclusion.checkInclusion("eidbaooo","ab"));
        System.out.println("----------------");
        StringInclusion myInclusion = new MyStringInclusion();
        System.out.println(myInclusion.checkInclusion("eidbaooo","ab"));
        System.out.println("----------------");
        StringInclusion finalInclusion = new FinalStringInclusion();
        System.out.println(myInclusion.checkInclusion("eidbaooo","ab"));
    }


    private static class FinalStringInclusion implements StringInclusion{

        @Override
        public boolean checkInclusion(String content, String target) {
            int[] accountBook = new int[256];
            int[] window = new int[256];
            for (int i = 0; i < target.length(); i++) {
                accountBook[target.charAt(i)]++;
            }
            int left = 0,right = 0,valid = 0;
            while (right < content.length()) {

//                if (right - left == target.length()) {  方式1
//                    System.out.println(content.substring(left,right));
//                    return true;
//                }


                char rightChar = content.charAt(right++);
                if (accountBook[rightChar] != 0) {
                    window[rightChar]++;
                    if (window[rightChar] == accountBook[rightChar]) {
                        valid++;
                    }
                }
//                while (right - left > target.length()) {    //方式2
                while (right - left >= target.length()) {
                    if (valid == target.length()) {
                        System.out.println(content.substring(left,right));
                        return true ;
                    }
                    char leftChar = content.charAt(left);
                    if (accountBook[leftChar] != 0) {
                        window[leftChar]--;
                        if (window[leftChar] < accountBook[leftChar]) {
                            valid--;
                        }
                    }
                }


            }



            return false;
        }
    }

    /**
     * 存在一部分冗余代码
     *
     * 窗口的缩小的时机是什么？ 窗口的大小大于t.size时；
     *
     */
    private static class MyStringInclusion implements StringInclusion {

        @Override
        public boolean checkInclusion(String content, String target) {
            int[] accountBook = new int[256];
            int[] window = new int[256];
            for (int i = 0; i < target.length(); i++) {
                accountBook[target.charAt(i)]++;
            }
            int left = 0,right = 0,valid = 0;
            while (right < target.length()) {
                char rightChar = content.charAt(right++);
                if (accountBook[rightChar] != 0) {
                    window[rightChar]++;
                    if (window[rightChar] == accountBook[rightChar]) {
                        valid++;
                    }
                }
            }
            if (valid == target.length()){
                System.out.println(content.substring(left,right));
                return true;
            }


            while (right < content.length()) {
                char rightChar = content.charAt(right++);
                char leftChar = content.charAt(left++);
                if (accountBook[rightChar] != 0) {
                    window[rightChar]++;
                    if (window[rightChar] == accountBook[rightChar]) valid++;
                }

                if (accountBook[leftChar] != 0) {
                    window[leftChar]--;
                    if (window[leftChar] < accountBook[leftChar]) {
                        valid--;
                    }
                }
                if (valid == target.length()) {
                    System.out.println(content.substring(left,right));
                    return true;
                }
            }




            return false;
        }
    }


    /**
     * 1.首先对target进行排序
     * 2.在content中获取长度为target.length()的字串，
     * 3。对字串进行排序，然后比较
     */
    private static class ForceStringInclusion implements StringInclusion {

        @Override
        public boolean checkInclusion(String content, String target) {
            int len = target.length();
            target = sortString(target);
            int left = 0,right = len; // 左闭右开 [left,right)
            while (right <= content.length()) {
                String sub = content.substring(left,right);
                if (isValid(sub,target)) {
                    System.out.println(sub);
                    return true;
                }
                left++;
                right++;
            }
            return false;
        }

        private boolean isValid (String contentSub,String target) {
            contentSub = sortString(contentSub);
            return contentSub.equals(target);
        }


        private String sortString (String str) {
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            return String.valueOf(arr);
        }


    }


    private interface StringInclusion {
        boolean checkInclusion (String content,String target);
    }

}
