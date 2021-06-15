package example.algorithm.interview.day.june;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;

/**
 * @ClassName Day0611 最长递增子序列
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484498&idx=1&sn=df58ef249c457dd50ea632f7c2e6e761&source=41#wechat_redirect
 * @Author weiliuyi
 * @Date 2021/6/12 4:22 下午
 **/
public class Day0611 {

    /**
     * Longest Increasing Subsequence(LIS) 最长递增子序列
     * <p>
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度；
     */
    @Test
    public void testMaxSubLength() {
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};
        LongestIncreSub<Integer> myDynamicLengthLIS = new MyDynamicLengthLIS();
        System.out.println(myDynamicLengthLIS.longestOfIncreSub(arr));
        System.out.println("------------" +
                "");
        LongestIncreSub<String> myDynamicArrLIS = new MyDynamicArrLIS();
        System.out.println(myDynamicArrLIS.longestOfIncreSub(arr));

        int[] arrV2 = {6, 3, 5, 10, 11, 2, 9, 14, 13, 7, 4, 8, 12};
        LongestIncreSub<Integer> finalLongestLIS = new FinalLongestLIS();
        System.out.println(finalLongestLIS.longestOfIncreSub(arrV2));
        System.out.println(finalLongestLIS.longestOfIncreSub(arr));
    }


    /**
     * 1.只能把点数小的牌压到比它大的点数上
     * 2。如果当前牌点数较大，没有可以放置的堆，则新建一个对，放进去
     * 3。如果当前堆有多个堆可供选择，选择最左侧的堆放置；
     */
    private static class FinalLongestLIS implements LongestIncreSub<Integer> {
        @Override
        public Integer longestOfIncreSub(int[] arr) {
            int[] piles = new int[arr.length]; //piles 单调递增的
            int size = 0;
            for (int temp : arr) {
                int left = 0, right = size; //pile[0,size)中左侧第一个闭temp大的元的角标
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (piles[mid] >= temp) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                    //错误的方式 这是只寻找了一个temp元素大的元素
///                    if (temp <= piles[mid]) {
//                        piles[mid] = temp;
//                    } else {
//                        left = left + 1;
//                    }
                }
                if (right == size) {
                    size++;
                }
                piles[right] = temp;
            }
            System.out.println("piles arr " + JSON.toJSONString(piles));
            return size;
        }
    }

    /**
     * 返回无序数组中，最长递增子序列
     */
    private static class MyDynamicArrLIS implements LongestIncreSub<String> {
        @Override
        public String longestOfIncreSub(int[] arr) {
            Entry[] dpEntry = new Entry[arr.length];
            for (int i = 0; i < arr.length; i++) {
                dpEntry[i] = new Entry(arr[i]);
            }
            for (int i = 1; i < arr.length; i++) {
                Entry temp = null;
                for (int j = 0; j < i; j++) {
                    if (arr[i] > dpEntry[j].number) {
                        temp = (temp == null || temp.arrList.size() < dpEntry[j].arrList.size()) ? dpEntry[j] : temp;
                    }
                }
                if (temp != null && temp.arrList.size() + 1 > dpEntry[i].arrList.size()) {
                    ArrayList<Integer> list = new ArrayList<>(temp.arrList);
                    list.add(dpEntry[i].number);
                    dpEntry[i].arrList = list;
                }
            }
            System.out.println(Arrays.toString(dpEntry));
            return Arrays.stream(dpEntry).
                    max(Comparator.comparingInt(entry -> entry.arrList.size())).orElseGet(() -> new Entry(-1)).arrList.toString();
        }

        private static class Entry {
            List<Integer> arrList = new ArrayList<>();
            int number;


            public Entry(int number) {
                this.number = number;
                arrList.add(number);
            }

            @Override
            public String toString() {
                return "Entry{" +
                        "arrList=" + arrList +
                        ", number=" + number +
                        '}';
            }
        }

    }


    /**
     * 返回无序数组中，最长递增子序列的长度；
     */
    private static class MyDynamicLengthLIS implements LongestIncreSub<Integer> {
        @Override
        public Integer longestOfIncreSub(int[] arr) {
            int[] dp = new int[arr.length];
            int res = -1;
            Arrays.fill(dp, 1);
            for (int i = 1; i < arr.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[i] > arr[j]) {
                        dp[i] = Math.max(dp[j] + 1, dp[i]);
                    }
                }
                res = Math.max(res, dp[i]);
            }
            return res;
        }

    }

    /**
     * 最长递增子序列的的接口类
     */
    private interface LongestIncreSub<T> {
        T longestOfIncreSub(int[] arr);
    }


}
