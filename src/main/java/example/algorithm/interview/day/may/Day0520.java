package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.*;

import static java.util.Comparator.comparingInt;

/**
 * @ClassName Day0520 twoSum问题 核心思想 ------- 如何使用hash表处理问题
 * @Description https://mp.weixin.qq.com/s/3CMQaY1mO1Iqt4j30bUVcA
 * @Author weiliuyi
 * @Date 2021/5/21 10:12 上午
 **/
public class Day0520 {

    /**
     *
     * TwoSum问题，一个难点就是 数组无序，对于无序数组，我们似乎什么技巧也没有，只能暴力穷举所有可能；
     *
     * 一般情况下，我们会首先把数组排序在考虑双指针技巧；
     * 1。TwoSum启发我们，HashMap或者HashSet也可以帮助我们处理无序数组的相关的简单问题；
     * 2。设计的核心在权衡，利用不同的数据结构，可以得到针对性的加强；
     */

    @Test
    public void testTwoSum() {
        int[] nums = {3, 1, 3, 6};
        NumberSum<int[]> twoNumberSum = new TwoNumberSum();
        System.out.println(Arrays.toString(twoNumberSum.numberSum(nums, 6)));
        System.out.println("---------------------------------");
        NumberSumV2 twoNumberSumV2V1 = new TwoNumberSumV2V1();
        twoNumberSumV2V1.add(3);
        twoNumberSumV2V1.add(1);
        twoNumberSumV2V1.add(3);
        twoNumberSumV2V1.add(6);
        System.out.println(twoNumberSumV2V1.find(6));

        System.out.println("-----------------------");
        NumberSumV2 twoNumberSumV2V2 = new TwoNumberSumV2V2();
        twoNumberSumV2V2.add(3);
        twoNumberSumV2V2.add(1);
        twoNumberSumV2V2.add(3);
        twoNumberSumV2V2.add(6);
        System.out.println(twoNumberSumV2V2.find(6));
        System.out.println("-----------------------");
        NumberSum<int[]> doublePointer = new DoublePointerNumberSum();
        int[] res = doublePointer.numberSum(nums, 6);
        System.out.println(Arrays.toString(res));
    }


    /**
     * Two Sum II 要求：设计一个类，拥有两个API
     */
    private static class TwoNumberSumV2V1 implements NumberSumV2 {
        Map<Integer, Integer> numFreMap = new HashMap<>();

        @Override
        public void add(int num) {
            numFreMap.put(num, numFreMap.getOrDefault(num, 0) + 1);
        }

        @Override
        public boolean find(int target) {
            for (int num : numFreMap.keySet()) {
                int left = target - num;
                //target的另外一半 不等于 num，
                if (num != left && numFreMap.containsKey(left)) {
                    return true;
                }
                if (num == left && numFreMap.get(left) > 1) {
                    return true;
                }
            }
            return false;
        }

    }

    private static class TwoNumberSumV2V2 implements NumberSumV2 {
        /*
         * 时间复杂度的分析：add方法是O(1)，find方法是O(N),空间复杂度为O(N),
         *
         * 对于API的设计，需要考虑现实情况，例如：我们设计的这个类，使用find方法非常频繁，
         * 那么每次都要O(n)的时间，岂不是很浪费时间？如何做优化呢？
         * 1.可以在add方法中添加更多的操作；
         */
        //所有的可能的sum
        private final Set<Integer> sumSet = new HashSet<>();
        //数组列表
        private final List<Integer> nums = new ArrayList<>();

        @Override
        public void add(int num) {
            for (Integer number : nums) {
                sumSet.add(number + num);
            }
            nums.add(num);
        }

        @Override
        public boolean find(int target) {
            return sumSet.contains(target);
        }
    }


    /**
     * 最基本的形式：给出一个数组和一个整数target,可以保证数组中存在两个数的和为target，请你返回这两个数的索引；
     */
    private static class TwoNumberSum implements NumberSum<int[]> {
        @Override
        public int[] numberSum(int[] nums, int target) {
            //return twoNumberSumV1(nums, target);
            //return twoNumberSumV2(nums, target);
            return twoNumberSumV3(nums, target);
        }


        /**
         * target = c (c = a + b)
         * 数组[...a.....b.....]
         * <p>
         * 时间复杂度为O（n^2）
         * c - a
         */
        private int[] twoNumberSumV1(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                int left = target - nums[i];
                for (int j = i + 1; j < nums.length; j++) { // 为什么可以从i+1开始寻找；
                    if (left == nums[j]) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{-1, -1};
        }

        private int[] twoNumberSumV2(int[] nums, int target) {
            Map<Integer, Integer> numIndexMap = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int left = target - nums[i];
                if (numIndexMap.containsKey(left)) {
                    return new int[]{numIndexMap.get(left), i};
                }
                numIndexMap.put(nums[i], i);
            }
            return new int[]{-1, -1};
        }

        private int[] twoNumberSumV3(int[] nums, int target) {
            int len = nums.length;
            Map<Integer, Integer> numIndexMap = new HashMap<>();
            for (int i = 0; i < len; i++) {
                numIndexMap.put(nums[i], i);
            }
            for (int i = 0; i < len; i++) {
                int left = target - nums[i];
                if (numIndexMap.containsKey(left) && numIndexMap.get(left) != i) {
                    return new int[]{i, numIndexMap.get(left)};
                }
            }
            return new int[]{-1, -1};
        }
    }


    /**
     *
     * 《《《《《《《《《《《《前提操作的数据是有序的》》》》》》》》》》》》》》
     * 使用双指针的技巧来解决问题，是由双指针操作的数据结构是有序的；
     */
    private static class DoublePointerNumberSum implements NumberSum<int[]> {

        @Override
        public int[] numberSum(int[] nums, int target) {
            return pointerNumSumV2(nums, target);
        }

        /**
         * 如何在无序数组中要使用有序的特性，可以借助其他数据结构；
         */
        private int[] pointerNumSumV2 (int[] nums,int target) {
            PriorityQueue<Integer[]> queue = new PriorityQueue<>(comparingInt((Integer[] pair) -> pair[0]));
            for (int i = 0; i < nums.length; i++) {
                queue.add(new Integer[]{nums[i],i});
            }
            Object[] arr = queue.toArray();
            int minLeft = 0,maxRight = arr.length-1;

            while (minLeft < maxRight) {
                Integer[] leftArr = (Integer[])arr[minLeft];
                Integer[] rightArr = (Integer[])arr[maxRight];
                int sum = leftArr[0] + rightArr[0];
                if (sum ==  target) {
                    return new int[] {leftArr[1],rightArr[1]};
                }  else if (sum > target) {
                    maxRight--;
                } else {
                    minLeft++;
                }
            }
         return new int[]{-1,-1};
        }

        // <<<<错误的方式；>>>>  打乱了原数组的顺序结构
        private int[] pointerNumberSum(int[] nums, int target) {
            Arrays.sort(nums);  //排序会打乱索引下标的索引，所以是不可取的；
            int left = 0,right = nums.length-1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    return new int[] {left,right};
                } else if (sum > target) {
                    right--;
                } else {
                    left++;
                }
            }
            return null;
        }
    }

    /**
     * two问题的顶层抽象；
     */
    private interface NumberSum<T> {
        T numberSum(T nums, int target);
    }

    /**
     * Two Sum II 要求：设计一个类，拥有两个API
     */
    private interface NumberSumV2 {
        //向数据结构中添加一个数 number
        void add(int num);

        //寻找当前数据结构中是否存在两个数和为value
        boolean find(int target);


    }
}
