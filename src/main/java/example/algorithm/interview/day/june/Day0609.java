package example.algorithm.interview.day.june;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Day0609 找到所有字母的异位词
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485141&idx=1&sn=0e4583ad935e76e9a3f6793792e60734&chksm=9bd7f8ddaca071cbb7570b2433290e5e2628d20473022a5517271de6d6e50783961bebc3dd3b&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/11 6:26 下午
 **/
public class Day0609 {


    /**
     * 给定一个字符串s和一个非空字符串p，找到s中s中所有的p的字母位词的字串，返回这些字符串的起始位置
     */

    @Test
    public void testAnagrams () {
        StringAnagrams<List<String>> myAnagrams  = new MyStringAnagrams();
        System.out.println(JSON.toJSONString(myAnagrams.findAnagrams("cbaebabacd","abc")));
    }


    private static class MyStringAnagrams implements StringAnagrams<List<String>> {


        @Override
        public List<String> findAnagrams(String content, String target) {
            int[] accountBook = new int[256];
            int[] window = new int[256];
            for (int i = 0; i < target.length(); i++) {
                accountBook[target.charAt(i)]++;
            }
            int left = 0,right = 0,valid = 0;
            List<String> res = new ArrayList<>();
            while (right < content.length()) {
                char rightChar = content.charAt(right++);
                if (accountBook[rightChar] != 0) {
                    window[rightChar]++;
                    if (accountBook[rightChar] == window[rightChar]) {
                        valid++;
                    }
                }

                while (right - left >= target.length()) {
                    if (right - left == target.length() && valid == target.length()) {
                        res.add(content.substring(left,right));
                    }
                    char leftChar = content.charAt(left++);
                    if (accountBook[leftChar] != 0) {
                        window[leftChar]--;
                        if (accountBook[leftChar] > window[leftChar]) {
                            valid--;
                        }
                    }
                }
            }


            return res;
        }
    }

    /**
     * 字符串异位词接口
     */
    private interface StringAnagrams<T> {
        T findAnagrams (String content,String target);
    }
}
