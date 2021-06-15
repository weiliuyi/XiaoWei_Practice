package example.algorithm.interview.day.june;

import org.junit.Test;

/**
 * @ClassName Day0612 二分查找
 * @Description   https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484507&idx=1&sn=36b8808fb8fac0e1906493347d3c96e6&source=41#wechat_redirect
 * @Author weiliuyi
 * @Date 2021/6/15 5:14 下午
 **/
public class Day0612 {

    /**
     * 二分查找，思路很简单，细节是魔鬼；
     *
     *
     * 二分查找框架；
     * int binarySearch (int[] nums,int target) {
     *     int left = 0,right = ...;
     *
     *     while (...) {
     *         int mid = (left + right) / 2;
     *         if (nums[mid] == target) {
     *             ...
     *         } else if (nums[mid] < target) {
     *             ...
     *         } else if (nums[mid] > target) {
     *             ...
     *         }
     *     }
     *
     *    return ....;
     * }
     *
     *
     */

    @Test
    public void testBinarySearch () {
        int[] nums = {1,2,2,2,3};
        BinarySearch targetIndexBs = new GetTargetIndexBS();
        System.out.println(targetIndexBs.binarySearch(nums,2));

        BinarySearch leftIndexBS = new GetLeftIndexBS();
        System.out.println(leftIndexBS.binarySearch(nums,2));

        BinarySearch rightIndexBS = new GetRightIndexBs();
        System.out.println(rightIndexBS.binarySearch(nums,2));
    }


    /**
     * 寻找一个数的索引
     */

    private static class GetTargetIndexBS implements BinarySearch {
        @Override
        public int binarySearch(int[] nums, int target) {
            int left = 0,right = nums.length - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (nums[mid] == target) {
                    return mid;
                } else if (nums[mid] > target) {
                    right = mid -1;
                } else if (nums[mid] < target) {
                    left  = mid + 1;
                }
            }
            return -1;
        }
    }

    /**
     * 寻找左侧边界
     */

    private static class GetLeftIndexBS implements BinarySearch {
        @Override
        public int binarySearch(int[] nums, int target) {
            int left = 0,right = nums.length;
            while (left < right) {
                int mid = (left + right) / 2;
                if (nums[mid] == target) {
                    right = mid;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid;
                }
            }
            return right < nums.length && nums[right] == target ?  right : -1;
        }
    }

    /**
     * 寻找右侧边界
     */
    private static class GetRightIndexBs implements BinarySearch {
        @Override
        public int binarySearch(int[] nums, int target) {
            int left = 0,right = nums.length;
            while (left < right) {
                int mid = (left + right) / 2;
                if (nums[mid] == target) {
                    left = mid + 1;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid;
                }
            }
            return left != 0 && nums[left-1] == target ? left -1 : -1;
        }
    }


    private interface BinarySearch {
        int binarySearch (int[] nums,int target);
    }
}
