package example.algorithm.interview.day.may;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @ClassName Day0530 差分数组
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487011&idx=1&sn=5e2b00c1c736fd7afbf3ed35edc4aeec&chksm=9bd7f02baca0793d569a9633cc14117e708ccc9eb41b7f0add430ea78f22e4f2443f421c6841&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/2 4:06 下午
 **/
public class Day0530 {

    /**
     * 前缀和算法：主要适用于原数组不会被改变的情况下，频繁查询某个区间的累加和。
     * <p>
     * perSum[i] = number：表达的含义是arr[0...i-1]的累加和为number;
     * <p>
     * 如何计算arr[i...j]区间的累加和 perSum[j+1] - preSum[i]
     * <p>
     * 「查分数组」和前缀和算法的技巧十分类似，
     * 「查分数组」主要适用的场景 频繁对原数组的某个区间的元素进行增减；
     * 这个增减是什么样子的？例如 一个数组nums，要求给nums[2...6]全部加一，再给nums[3..9]全部减3，再给nums[0..4]全部减上2
     * <p>
     * 查分数组diff[]如何构造的呢？
     * diff[i] = nums[i] - nums[i-1] (其中 i > 0)
     * <p>
     * 构造查分数组的逻辑如下：
     * diff[0] = nums[0];
     * for (int i = 1;i < nums.length;i++) {
     * diff[0] = nums[i] - nums[i-1];
     * }
     * <p>
     * 根据查分数组反推源数组的nums,代码逻辑如下：
     * int[] res = new int[diff.length];
     * res[0] = diff[0];
     * for (int i =1;i < diff.length;i++) {
     * res[i] = res[i-1] + diff[i];
     * }
     * 这样构造的查分数组diff,可以快速的进行区间的增减操作，
     * 如果相对区间nums[i...j]的元素全部加3，那么只需要diff[i] += 3 然后让diff[j+1] -= 3;
     * <p>
     * 远离很简单：diff[i] += 3 意味着给nums[i....]的所有元素都加了3，然后diff[j+1] -= 3 又意味着对于nums[j+1....]所有的元素减去3，相当于 nums[j+1...]的元素没有变化
     */


    @Test
    public void testDiffArr () {
        int[] arr = {0,1,2,3,4,5,6};
        Difference diff = new Difference(arr);
        System.out.println(JSON.toJSONString(diff.getDiff()));
        diff.increment(1,4,3);
        System.out.println(JSON.toJSONString(diff.getDiff()));
        System.out.println(JSON.toJSONString(diff.getSourceArr()));
    }

    private static class Difference {
        private int[] diff;

        public Difference(int[] arr) {
            this.diff = getDiff(arr);
        }

        /**
         * 初始化查分数组
         *
         * @param arr 原数组
         */
        private int[] getDiff(int[] arr) {
            int[] diff = new int[arr.length];
            diff[0] = arr[0];
            for (int i = 1; i < arr.length; i++) {
                diff[i] = arr[i] - arr[i - 1];
            }
            return diff;
        }

        public int[] getDiff () {
            return diff;
        }

        public int[] getSourceArr() {
            int[] res = new int[diff.length];
            res[0] = diff[0];
            for (int i = 1; i < res.length; i++) {
                res[i] = res[i - 1] + diff[i];
            }
            return res;
        }

        //给闭区间[i...j]上增加一个val(可以为负数)
        public void increment(int i, int j, int val) {
            diff[i] += val;
            if (j < diff.length-1) {
                diff[j+1] -= val;
            }
        }

    }

}
