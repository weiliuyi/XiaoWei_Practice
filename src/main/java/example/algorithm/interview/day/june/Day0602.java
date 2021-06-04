package example.algorithm.interview.day.june;

import org.junit.Test;

/**
 * @ClassName Day0602  二分查找 左右指针常用的算法
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247488584&idx=1&sn=90f7956fd9d8320fcb81aaf33c3fe7f1&chksm=9bd7ea40aca06356cdb87ba86518c50646b48b8534d42625ba454c084187400b979c8d736a61&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/4 7:53 下午
 **/
public class Day0602 {

    /**
     * 左右指针常用的算法：1。二分查找 2。两数之和
     */
    @Test
    public void testLeftRightPointer() {

    }

    /**
     * 给定按照升序排序的有序数组，找到两个数使得他们的和瞪目目标数
     */

    private int[] getTargetIndex(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int tempSum = arr[left] + arr[right];
            if (tempSum < target) {
                left++;
            } else if (tempSum > target) {
                right--;
            } else {
                return new int[]{left, right};
            }
        }
        return new int[0];
    }

    private int[] reverseArr(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            swap(arr,left++,right--);
        }
        return arr;
    }

    private void swap (int[] arr,int i,int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
