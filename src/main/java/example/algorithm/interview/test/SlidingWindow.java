package example.algorithm.interview.test;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: 滑动窗口技术
 * @author: weiliuyi
 * @create: 2020--11 11:25
 **/
public class SlidingWindow {


    /*
     *
     * 类似于StringContain
     *
     * 滑动窗口是一种想象出来的数据结构：
     *
     * 滑动窗口有左边界L和有边界R
     * 在数组或者字符串或者一个序列上，记为S，窗口就是S[L..R]这一部分
     * L往右滑意味着一个样本出了窗口，R往右滑意味着一个样本进了窗口
     * L和R都只能往右滑
     *
     * 滑动窗口解决什么问题？？？
     * 滑动窗口、首尾指针等技巧，说白了是一种求解问题的流程设计
     *
     * 滑动内最大值和最小值的更新结构
     * 窗口不管L还是R滑动之后，都会让窗口呈现新状况，
     * 如何能够更快的得到窗口当前状况下的最大值和最小值？
     * 最好平均下来复杂度能做到O(1)
     * 利用单调双端队列！
     */


    /**
     * 给定一个整数数组，计算长度为 'k' 的连续子数组的最大总和。
     */

    @Test
    public void test1() {
        int[] arr = {1000, 100, 50, 900, 300, 400};
        System.out.println(getKLengthMax(arr, 2));
        System.out.println(getKLengthMax(arr, 3));
    }


    @Test
    public void test2() {
        int[] arr = {1000, 100, 50, 900, 300, 400};
        System.out.println(getKLengthMaxVersion2(arr, 2));
        System.out.println(getKLengthMaxVersion2(arr, 3));
    }

    /**
     * 获得固定长度数组元素的最大值；
     *
     * @param array 目标数组
     * @param k     固定长度
     * @return 返回最大值
     */
    private int getKLengthMax(int[] array, int k) {
        int result = -1;
        if (array == null || array.length < k) {
            return result;
        }
        for (int i = 0; i < array.length - k + 1; i++) {
            int sum = 0;
            for (int j = i; j < i + k; j++) {  //这个地方性能太低了，每次都要遍历子数组  可以进行优化；
                sum += array[j];
            }
            result = sum > result ? sum : result;
        }
        return result;
    }


    private int getKLengthMaxVersion2(int[] array, int k) {
        int result = 0;
        if (array == null || array.length < k) {
            return result;
        }
        for (int i = 0; i < k; i++) { //前k个数的最大值
            result += array[i];
        }
        int temp = result;
        for (int i = k; i < array.length; i++) {
            temp += array[i] - array[i - k];
            result = temp > result ? temp : result;
        }
        return result;
    }

    /**
     * 给定一个字符串 S 和一个字符串 T，请在 S 中找出包含 T 所有字母的最小子串。(minimum-window-substring)
     * 输入: S = "ADOBECODEBANC", T = "ABC"
     * 输出: "BANC"
     */

    @Test
    public void test3() {
        System.out.println(getMinContainSubstring("ADOBECODEBANC", "ABC"));
        System.out.println(getMinContainSubstring("ADOBECODEBANCF", "ABCE"));
        System.out.println("--------------------");
        System.out.println(getMinContainSubstringVersion2("ADOBECODEBANC", "ABC"));
        //System.out.println(getMinContainSubstringVersion2("ADOBECODEBANCF", "ABCE"));

        System.out.println("--------------------------------------------");
        System.out.println(getMinStr("ADOBECODEBANC", "ABC"));
        //System.out.println(getMinStr("ADOBECODEBANCF", "ABCE"));

    }

    private String getMinContainSubstring(String content, String target) {
        if (content == null || content.length() < target.length()) {
            return null;
        }
        String result = "                                          ";
        int left = 0, right = target.length() - 1;
        while (right < content.length()) {
            String sub = content.substring(left, right + 1);
            if (contains(sub, target)) { //这个地方可以进行优化，根据记账算法进行优化；
                result = sub.length() < result.length() ? sub : result;
                left++;
            } else {
                right++;
            }
        }
        return result;
    }

    /**
     * 判断target的所有字符是否在content存在
     *
     * @param content 原值字符串
     * @param target  目标字符串
     * @return 返回是否包含
     */
    private boolean contains(String content, String target) {
        for (int i = 0; i < target.length(); i++) {
            if (!content.contains(target.substring(i, i + 1))) {
                return false;
            }
        }
        return true;
    }


    //TODO  这是有问题的 以后进行解决
    private String getMinContainSubstringVersion2(String content, String target) {
        if (content == null || content.length() < target.length()) {
            return null;
        }
        int[] accountBook = new int[256];//记账本
        int account = 0;                 //总账
        for (int i = 0; i < target.length(); i++) { //对账本和总账进行初始化
            accountBook[target.charAt(i)] += 1;
            account++;
        }
        for (int i = 0; i < target.length(); i++) { //首先判断content的前target.length个字符的情况；
            if (accountBook[content.charAt(i)]-- > 0) {
                account--;
            }
        }
        if (account == 0) { //如果content的前target.length个字符正好匹配成功，那么直接返回
            return content.substring(0, target.length());
        }

        String result = null;
        int left = 0, right = target.length();
        boolean isRight = true;
        while (right < content.length() || !isRight) {
            if (isRight) {
                if (accountBook[content.charAt(right++)]-- > 0) {
                    account--;
                }
            } else {
                if (accountBook[content.charAt(left++)]++ >= 0) {
                    account++;
                }
            }
            //System.out.println("getMinContainSubstringVersion2 sub =  " + sub + " account = " + account);
            if (account == 0) {
                String sub = content.substring(left, right);
                result = result == null || result.length() > sub.length() ? sub : result;
                isRight = false;
            } else {
                isRight = true;
            }
        }
        return result;
    }


    private String getMinStr(String content, String target) {
        if (content == null || target == null || target.length() > content.length()) {
            return "";
        }
        int[] accountBook = new int[256]; //账本
        int account = 0; //总账
        //初始化
        for (int i = 0; i < target.length(); i++) {
            accountBook[target.charAt(i)]++;
            account++;
        }
        for (int i = 0; i < target.length(); i++) { //content的前target.length字符还账的情况
            if (accountBook[content.charAt(i)]-- > 0) {
                account--;
            }
        }
        if (account == 0) {
            return content.substring(0, target.length());
        }
        String result = null;
        boolean isRight = true;
        int left = 0;
        int right = target.length();
        while (right < content.length() || !isRight) {
            if (isRight) {
                if (accountBook[content.charAt(right++)]-- > 0) {
                    account--;
                }
            } else {
                if (accountBook[content.charAt(left++)]++ >= 0) {
                    account++;
                }
            }
            String sub = content.substring(left, right );
            //System.out.println("getMinStr sub =  " + sub);
            if (account == 0) {
               result = result == null || result.length() > sub.length() ? sub : result;
                isRight = false;
            } else {
                isRight = true;
            }
        }
        return result;
    }


    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。(longest-substring-without-repeating-characters)
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */

    @Test
    public void test4() {
        System.out.println(getMaxNoDuplicateCharLength("abcabcdefghabb"));
        System.out.println(getMaxNoDuplicateCharLengthVersion2("abcabcdefghabb"));
        System.out.println(getMaxNoDuplicateCharLengthVersion3("abcabcdefghabb"));
        System.out.println(getNoDuplicateCharStr("abcabcdefghbb"));
        System.out.println(getNoDuplicateMaxStr("abcabcdefghbb"));



    }

    /**
     * 暴力求解的方法
     * 找到所有的子字符串， 然后判断是否是不含重复的字符串
     * 时间的复杂度是O(n3)
     */
    private int getMaxNoDuplicateCharLength(String content) {
        int result = 0;
        for (int i = 0; i < content.length(); i++) {
            for (int j = i + 1; j <= content.length(); j++) {
                //System.out.println("i = " + i + " j " + j);
                String subStr = content.substring(i, j);
                if (isAllUnique(subStr)) {
                    result = subStr.length() > result ? subStr.length() : result;;
                }
            }
        }
        return result;
    }

    /**
     * 判断字符串是否包含重复字符
     *
     * @param content 目标字符串
     * @return 是否重复
     */
    private boolean isAllUnique(String content) {
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < content.length(); i++) {
            if (charSet.contains(content.charAt(i))) {
                return false;
            } else {
                charSet.add(content.charAt(i));
            }
        }
        return true;
    }

    /**
     * 使用滑动窗口的思想；
     * 使用两个指针，来标注不重复子串的范围，如果是不重复字符串，就移动右指针，如果不是重复字符串，就移动左侧指针
     * 在这个移动过程中，记录不重复子串的最大值
     * 时间复杂度为O(n2)
     * <p>
     * 可以通过一次遍历就可以解决，
     * 字符串是否包含重复字符串  这个逻辑可以进行优化  字符数组表---进行计数
     */
    private int getMaxNoDuplicateCharLengthVersion2(String content) {
        if (StringUtils.isEmpty(content)) {
            return 0;
        }
        int result = 0;
        int left = 0, right = 0;
        while (right < content.length() && left <= right) {
            String subs = content.substring(left, right + 1);
            if (isAllUnique(subs)) {  //判断是否重复   这个地方可以进行优化  可以使用一个字符数组进行解决，
                result = subs.length() > result ? subs.length() : result;
                right++;
            } else {
                left++;
            }
        }
        return result;
    }


    /**
     * 滑动窗口算法来解决，并且优化判断是否重复的逻辑；
     *
     * @param content 目标字符串
     * @return 返回最大长度
     */
    private int getMaxNoDuplicateCharLengthVersion3(String content) {
        if (StringUtils.isEmpty(content)) {
            return 0;
        }
        int result = 1;
        int left = 0, right = 1;
        int[] charArray = new int[256];
        charArray[content.charAt(left)]++;
        boolean isRight = true;
        while (right < content.length() && left <= right) {
            if (isRight) {
                if (charArray[content.charAt(right)]++ == 0) { //判断最右边的是否重复，如果重复就停止
                    //如果不重复，计算长度
                    int length = right - left + 1;
                    result = length > result ? length : result;
                    right++;
                } else {  //如果重复，需要移动左指针
                    isRight = false;
                }
            } else {
                if (content.charAt(left) == content.charAt(right)) {
                    //移动左指针，直到与有指针对应的字符相等为止，在这个过程中，每移动一次左指针，都要更新charArray
                    isRight = true;
                    right++; //重复指针的下一个元素
                }
                charArray[content.charAt(left++)]--;
            }
        }
        return result;
    }


    public String getNoDuplicateCharStr (String content) {
        if (content == null) {
            return "";
        }
        int[] isHave  = new int[256]; //设置已经遍历的得到的
        int[] charIndexMapping = new int[256];
        String result = "";
        int begin = 0, end = 0;
        while (end < content.length()) {
            char c =  content.charAt(end);
//            if (isHave[c] == 0) { //bigfix分析，isHave[c] != 0时候，就会出现跳过这个字母的问题；
//                isHave[c]++;
            if (isHave[c]++ == 0) {
                String sub = content.substring(begin,end+1);
                result = result.length() < sub.length() ? sub : result;

            } else {
                int cIndex = charIndexMapping[c];
                for (int i = begin;i <= cIndex;i++) { //清除标志 此时也失去跳跃指针的跳跃的作用
                    isHave[content.charAt(i)]--;
                }
                begin = cIndex+1;
            }
            charIndexMapping[c] = end;//这是映射关系
            end++;

        }
        return result;
    }

    public String getNoDuplicateMaxStr (String content) {
        if (content == null) {
            return "";
        }
        int[] charMapping = new int[256];
        int begin = 0,end = 1;
        boolean isRight = true;
        String result = "";
        charMapping[content.charAt(begin)]++;
        while (end < content.length() && begin < end) {
            if (isRight) {
                if (charMapping[content.charAt(end)] == 0) {
                    charMapping[content.charAt(end++)]++;
                    String sub = content.substring(begin,end);
                    result = result.length() < sub.length() ? sub : result;
                } else {
                    isRight = false;
                }
            } else {
                if (content.charAt(begin) == content.charAt(end)) {
                    isRight = true;
                }
                charMapping[content.charAt(begin++)]--;
            }
        }
        return result;
    }


    /**
     * 这个是对上边的优化，使用hashMap这个数据结构来实现进行跳转左边的指针
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        //使用hashmap记录遍历过的字符的索引，当发现重复的字符时，可以将窗口的左边直接跳到该重复字符的索引处
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]  i 代表左起点，j代表右起点
        for (int j = 0, i = 0; j < n; j++) {//j负责向右边遍历，i根据重复字符的情况进行调整
            if (map.containsKey(s.charAt(j))) {//当发现重复的字符时,将字符的索引与窗口的左边进行对比，将窗口的左边直接跳到该重复字符的索引处
                i = Math.max(map.get(s.charAt(j)), i);  //什么情况下会出现，j重复字符下标小于 i ？？？？？？
            }
            //记录子字符串的最大的长度
            ans = Math.max(ans, j - i + 1);
            //map记录第一次遍历到key时的索引位置，j+1,保证i跳到不包含重复字母的位置
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }


    @Test
    public void test7() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -3);
        long createTime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime()));
        System.out.println(createTime);
    }

}
