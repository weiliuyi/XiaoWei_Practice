package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

import static java.util.Comparator.comparingInt;

/**
 * @ClassName Day0519 优势洗牌（田忌赛马）
 * @Description https://mp.weixin.qq.com/s/Rxx3BGxRLe_FZHqNS2ILsg
 * @Author weiliuyi
 * @Date 2021/5/20 4:25 下午
 **/
public class Day0519 {


    /**
     * 输入两个长度相等的数组nums1和nums2，重新组织nums1中的元素的位置，使得nums1的「优势」最大化；
     * 如果nums1[i] > nums[2]，就是说nums1在索引i上对nums2[i]有优势，优势最大树就是说让你重新组织nums1，尽可能多的nums[i] > nums[i];
     * <p>
     * 思路分析： 没有排序的思路，如果存在优势，选择差距最小的；
     * 1。什么时候nums1[i]可以对nums2[i]有优势？
     * nums1存在大于nums2[i]的值；
     * 2。如果nums1[i]没有可能对nums2[i]有优势？
     * 在nums1选择最小的的元素；
     * 3。如果让nums1[i]可以对nums2[i]可以有优势？
     * 可以在nums1选择和nums2[i]最小的值；
     *
     *
     * 思路二：
     * 1。分别对两个数组进行排序，按照排名一一对比，如果存在优势，。。。 如果不存在优势，选择一个最小的；
     *
     * 伪代码如下
     *
     * int n = nums1.length;
     * sort(nums1);
     * sort(nums12);
     *
     * 从最开的马开始比赛
     * for (int i = n -1 ;i >= 0 ;i--) {
     *     if (nums1[i] > nums2[i]) {
     *         // 比得过，就比
     *     } else {
     *         //比不过，换个垫底的来送人头；
     *     }
     * }
     *
     */

    @Test
    public void testAdvantageCount() {
        int[] nums1 = {12,24,8,32};
        int[] nums2 = {13,25,32,11};
        //int[] res = advantageCount(nums1, nums2);
        int[] res = advantageCountV2(nums1, nums2);
        System.out.println(Arrays.toString(res));
    }

    /**
     * 这个算法有没有可以优化的空间
     */
    private int[] advantageCount(int[] nums1, int[] nums2) {
        int len = nums1.length;
        for (int i = 0; i < len; i++) {
            int biggerIndex = -1;
            int minIndex = i;
            for (int j = i; j < len; j++) {
                if (nums1[j] > nums2[i] && (biggerIndex == -1 || nums1[biggerIndex] > nums1[j])) {
                    biggerIndex = j;
                }
                if (nums1[j] < nums1[minIndex]) {
                    minIndex = j;
                }
            }
            System.out.println("min = " + nums1[minIndex] + " biggerIndex =" + biggerIndex);
            if (biggerIndex == -1) { //无法优势
                swap(nums1, i, minIndex);
            } else {
                swap(nums1, i, biggerIndex);
            }
        }
        return nums1;
    }

    /**
     * 复用已经存在的数据结构
     */
    private int[] advantageCountV2 (int[] nums1,int[] nums2) {
        int len = nums1.length;

        //PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((int[] pair1,int[] pair2) -> pair2[1] - pair1[1]);
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(comparingInt((int[] pair) -> pair[1]).reversed());

        for (int i = 0; i < len; i++) {
            priorityQueue.add(new int[]{i,nums2[i]});
        }

        Arrays.sort(nums1);

        int left = 0,right = len -1;
        int[] res = new int[len];

        while (!priorityQueue.isEmpty()) {
            int[] pair = priorityQueue.poll();
            int i = pair[0],maxVal = pair[1];
            if (nums1[right] > maxVal) {
                res[i] = nums1[right];
                right--;
            } else {
                res[i] = nums1[left];
                left++;
            }
        }
        return res;

    }


    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
