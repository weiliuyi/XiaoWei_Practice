package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Day0606 解决子数组的问题
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484488&idx=1&sn=848f76e86fce722e70e265d0c6f84dc3&chksm=9bd7fa40aca07356a6f16db72f5a56529044b1bdb2dcce2de4efe59e0338f0c313de682aef29&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/7 8:15 下午
 **/
public class Day0606 {

    /**
     * 给定一个整数数组和一个整数k，算出一共有几个和为k的子数组
     */

    @Test
    public void testPrefixSum() {
        int[] arr = {1,1,1};
        SubArraySum subArray = new SubArraySumImpl();
        System.out.println(subArray.subArraySum(arr,2));

        SubArraySum subArraySum = new OptimizeSubArraySumImpl();
        System.out.println(subArraySum.subArraySum(arr,2));
    }


    private static class OptimizeSubArraySumImpl implements SubArraySum {
        @Override
        public int subArraySum(int[] arr, int target) {
            int res = 0;
            int[] prefix = new int[arr.length + 1];
            prefix[0] = 0;
            Map<Integer, Integer> indexCountMap = new HashMap<>();
            indexCountMap.put(0,1);
            for (int i = 1; i < prefix.length; i++) {
                prefix[i] = prefix[i-1] + arr[i-1];
                if (indexCountMap.containsKey(prefix[i] - target))
                    res+= indexCountMap.get(prefix[i] - target);
                indexCountMap.put(prefix[i],indexCountMap.getOrDefault(prefix[i],0) + 1);
            }
            return res;
        }
    }



    private static class SubArraySumImpl implements SubArraySum {

        @Override
        public int subArraySum(int[] arr,int target) {
            int count = 0;
            int[] prefixArr = getPrefixArrSum(arr);
            for (int i = 1; i < prefixArr.length; i++) {
                for (int j = 0; j < i; j++) { //这次循环的目的是什么？ 统计以[0,i]有多少个子数组和为target
                    if (prefixArr[i] - prefixArr[j] == target) count++;
                }
            }
            return count;
        }

        private int[] getPrefixArrSum (int[] arr) {
            int[] prefixSum = new int[arr.length + 1];
            prefixSum[0] = 0;
            for (int i = 1; i <= arr.length; i++) {
                prefixSum[i] = prefixSum[i-1] + arr[i-1];
            }
            return prefixSum;
        }
    }


    /**
     * 子数组接口类
     */
    private interface SubArraySum {
        int subArraySum(int[] arr,int target);
    }

}
