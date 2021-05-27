package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.*;

/**
 * @ClassName Day0521 twoSum 问题扩展 threeSum ..... NumSum的问题
 * @Description https://mp.weixin.qq.com/s/fSyJVvggxHq28a0SdmZm6Q
 * @Author weiliuyi
 * @Date 2021/5/22 6:45 下午
 **/
public class Day0521 {

    /**
     *
     */
    @Test
    public void testNumberSumExtent() {

        int[] nums = {5, 3, 1, 6};
        NumberSum<int[]> twoNumberSumV1 = new TwoNumberSumV1();
        int[] resV1 = twoNumberSumV1.numSumTarget(nums, 9);
        System.out.println(Arrays.toString(resV1));

        System.out.println("-----------------");
        int[] nums2 = {1, 3, 1, 2, 2, 3};
        TwoNumberSumV2 twoNumberSumV2 = new TwoNumberSumV2();
        Set<ArrayWrapper> resV2 = twoNumberSumV2.numSumTarget(nums2, 4);
        resV2.forEach(System.out::println);
        System.out.println("-----------------");
        TwoNumberSumV3 twoNumberSumV3 = new TwoNumberSumV3();
        int[] nums3 = {1, 3, 1, 2, 2, 3};
        List<int[]> res3 = twoNumberSumV3.numSumTarget(nums3, 4);
        res3.forEach(arr -> System.out.println(Arrays.toString(arr)));

        System.out.println("----------------------");
        TwoNumberSumV4 twoNumberSumV4 = new TwoNumberSumV4();
        int[] nums4 = {1, 3, 1, 2, 2, 3};
        List<int[]> resV4 = twoNumberSumV4.numSumTarget(nums4, 4);
        resV4.forEach(arr -> System.out.println(Arrays.toString(arr)));

        System.out.println("---------------------");
    }


    /**
     * 原题目要求返回索引，现在改成返回元素的值；
     */
    public static class TwoNumberSumV1 implements NumberSum<int[]> {

        @Override
        public int[] numSumTarget(int[] nums, int target) {
            Arrays.sort(nums);
            int left = 0, right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    return new int[]{nums[left], nums[right]};
                } else if (sum > target) {
                    right--;
                } else {
                    left++;
                }
            }
            return new int[]{-1, -1};
        }
    }

    /**
     * 继续魔改，返回所有累加和为指定值的元素所有集合（不包含重复）
     * <p>
     * 这又两个关键点，1。所有的集合 2。不包含重复；
     * 1. 不可重复的解决方式，1。使用set集合，2。使用包装类 ArrayWrapper,
     * 2. 如何找到所有的结果集
     * 2.1：排序，使用双指针技巧 其实也可使用这一种
     * 当 nums[left] + nums[right] == target，此时可以执行left++,right--;
     * 为什么可以这么执行呢？
     * 这是因为 left++,right和left,right-- 这两种情况是可以忽略，为什么可以忽略呢？
     * 假如我们执行left,right--这操作，在以后的结果中，又出现了能够凑成目标和，并且left的角标没有变化；
     * 此时凑成的目标和一定重复，这是因为，目标和一定，其中的一个元素确定了，那么另外一个元素的就已经确定了，
     * 所以可以得出；
     * <p>
     * 2.2：使用hashMap来记录这个貌似可行
     */

    private static class TwoNumberSumV2 implements NumberSum<Set<ArrayWrapper>> {
        @Override
        public Set<ArrayWrapper> numSumTarget(int[] nums, int target) {
            return numSumTargetHashSet(nums, target);
        }

        private Set<ArrayWrapper> numSumTargetHashSet(int[] nums, int target) {
            Set<ArrayWrapper> res = new HashSet<>();
            Set<Integer> set = new HashSet<>();

            for (int num : nums) {
                int left = target - num;
                if (set.contains(left)) {
                    int one = Math.min(left, num);
                    int two = Math.max(left, num);
                    res.add(new ArrayWrapper(one, two));
                }
                set.add(num);
            }
            return res;
        }
    }


    /**
     * 1。排序
     * 2。当累加和等于目标值的时候，只需要判断当前数据对 是否和上一个相等；
     */
    private static class TwoNumberSumV3 implements NumberSum<List<int[]>> {
        @Override
        public List<int[]> numSumTarget(int[] nums, int target) {
            List<int[]> res = new ArrayList<>();
            Arrays.sort(nums);
            int left = 0, right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    if (res.isEmpty()) {
                        res.add(new int[]{nums[left], nums[right]});
                    } else {
                        int[] last = res.get(res.size() - 1);
                        if (last[0] != nums[left] && last[1] != nums[right]) {
                            res.add(new int[]{nums[left], nums[right]});
                        }
                    }
                    left++;
                    right--;
                } else if (sum > target) {
                    right--;
                } else {
                    left++;
                }

            }
            return res;
        }
    }

    private static class TwoNumberSumV4 implements NumberSum<List<int[]>> {
        @Override
        public List<int[]> numSumTarget(int[] nums, int target) {
            List<int[]> res = new ArrayList<>();
            Arrays.sort(nums);
            int left = 0, right = nums.length - 1;
            while (left < right) {
                int leftNum = nums[left], rightNum = nums[right];
                int sum = leftNum + rightNum;
                if (target == sum) {
                    res.add(new int[]{leftNum, rightNum});
                    while (left < right && leftNum == nums[left]) left++;
                    while (left < right && rightNum == nums[right]) right--;

                } else if (sum > target) {
                    while (left < right && rightNum == nums[right]) right--;
                } else {
                    while (left < right && leftNum == nums[left]) left++;
                }

                // 代码优化，这样写代码 太low了 把公用的变量抽取出来
//                int sum = nums[left] + nums[right];
//                if (sum == target) {
//                    res.add(new int[] {nums[left],nums[right]});
//
//                    int tempRight = nums[right--];
//                    while (left < right && tempRight == nums[right]) right--;
//                    int tempLeft = nums[left++];
//                    while (left < right && tempLeft == nums[left]) left++;
//
//                } else if (sum > target) {
//                    int tempRight = nums[right--];
//                    while (left < right && tempRight == nums[right]) right--;
//                } else {
//                    int tempLeft = nums[left++];
//                    while (left < right && tempLeft == nums[left]) left++;
//                }

            }
            return res;
        }
    }

    /**
     * ThreeNumber问题
     */
    public class ThreeNumberSum implements NumberSum<List<int[]>> {
        @Override
        public List<int[]> numSumTarget(int[] nums, int target) {
            return threeNumberSum(nums, 0, target);
        }

        /**
         * 可以将threeNum问题转化为twosum问题
         */
        private List<int[]> threeNumberSum(int[] nums, int start, int target) {
            Arrays.sort(nums);
            List<int[]> res = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int temp = nums[i];
                List<int[]> twoNumList = twoNumberSum(nums, start + 1, target - nums[i]);
                if (twoNumList.size() == 0) continue;

                for (int[] arr : twoNumList) {
                    res.add(new int[]{nums[i], arr[0], arr[1]});
                }
                //while (i < nums.length && temp == nums[i]) i++; 这样写有什么样子的问题？ 如果temp ！= nums[i]的话 这样也就是i不会++
                while (i < nums.length - 1 && nums[i] == nums[i + 1])
                    i++;  //跳出循环时 nums[i] != nums[i+1]  再经过for循环的i++来完成
            }
            return res;
        }


        private List<int[]> twoNumberSum(int[] nums, int start, int target) {
            List<int[]> res = new ArrayList<>();
            int left = start, right = nums.length - 1;
            while (left < right) {
                int leftNum = nums[left], rightNum = nums[right];
                int sum = leftNum + rightNum;
                if (sum == target) {
                    res.add(new int[]{leftNum, rightNum});

                    while (left < right && leftNum == nums[left]) left++;
                    while (left < right && rightNum == nums[right]) right--;

                } else if (sum > target) {
                    while (left < right && rightNum == nums[right]) right--;
                } else {
                    while (left < right && leftNum == nums[left]) left++;
                }
            }
            return res;
        }

    }


    /**
     * nsum问题
     */
    private class NNumberSum implements NumberSum<List<int[]>> {

        @Override
        public List<int[]> numSumTarget(int[] nums, int target) {
            Arrays.sort(nums);
            return null;
        }

        private List<int[]> nNumberSum(int[] nums, int n, int target, int start) {
            List<int[]> res = new ArrayList<>();
            if (n == 2) {
                int left = start, right = nums.length;
                while (left < right) {
                    int leftSum = nums[left], rightSum = nums[right];
                    int sum = leftSum + rightSum;
                    if (sum == target) {
                        res.add(new int[]{leftSum, rightSum});
                        while (left < right && leftSum == nums[left]) left++;
                        while (left < right && rightSum == nums[right]) right--;
                    } else if (sum > target) {
                        while (left < right && rightSum == nums[right]) right--;
                    } else {
                        while (left < right && leftSum == nums[left]) left++;
                    }
                }
                return res;
            }

            for (int i = start; i < nums.length; i++) {
                int temp = nums[i];
                List<int[]> subList = nNumberSum(nums, n - 1, target - temp, start + 1);
                if (subList.size() == 0) continue;
                for (int[] arr : subList) {
                    int[] resArr = new int[arr.length + 1];
                    copyArray(arr,resArr);
                    resArr[arr.length] = temp;
                    res.add(resArr);
                }
            }
            return res;
        }

        private void copyArray(int[] src, int[] des) {
            for (int i = 0; i < src.length; i++) {
                des[i] = src[i];
            }
        }
    }




    /**
     * 为什么创建这个类？ 主要是元素的元素的重复问题；
     * 如何实现的？ 冲泻 hashCode equals方法；
     */
    private static class ArrayWrapper {
        int[] arr;

        public ArrayWrapper(int one, int two) {
            this.arr = new int[]{one, two};
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ArrayWrapper that = (ArrayWrapper) o;
            return Arrays.equals(arr, that.arr);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(arr);
        }

        @Override
        public String toString() {
            return Arrays.toString(arr);
        }
    }


    private interface NumberSum<T> {
        T numSumTarget(int[] nums, int target);
    }

}
