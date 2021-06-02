package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.*;

/**
 * @ClassName Day0527 排列问题
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/1 5:22 下午
 **/
public class Day0527 {

    /**
     * 排列 组合 和子集问题的解法总结，总结一下：
     * 子集问题：可以利用数学归纳法，假设已知一个规模较小的问题的结果，思考如何推导出原问题的结果；也可以利用回溯法，要用start参数排除已经选择的数字
     * 组合问题：利用的是回溯的思想，也可以表示成树结构，我们只要套用算法模版接口，关键点在于要用一个start排除已经选择过的数字
     * 排列问题：既可以利用数学归纳法，也可以利用回溯思想，表示成树结构，套用算法模版就可以了（关键用contains方法排除已经选择的数字；）；
     *
     *
     */


    /**
     * 不包含重复数字的数组nums，返回这些数字的全排列
     */
    @Test
    public void testPermutation() {
        int[] arr = {1, 2, 3};
        NumberPermutation<int[]> myRecPermutation = new MyRecursivePermutation();
        List<int[]> myRecRes = myRecPermutation.getNumberPermutation(arr);
        myRecRes.forEach(tempArr -> System.out.println(Arrays.toString(tempArr)));

        System.out.println("------------------------");
        NumberPermutation<Integer[]> myBackPermutation = new MyBackTracePermutation();
        List<Integer[]> myBackRes = myBackPermutation.getNumberPermutation(arr);
        myBackRes.forEach(tempArr -> System.out.println(Arrays.toString(tempArr)));

        System.out.println(
                "-----------------------------"
        );
        NumberPermutation<Integer[]> finalPermutation = new FinalPermutation();
        List<Integer[]> finalRes = finalPermutation.getNumberPermutation(arr);
        finalRes.forEach(tempArr -> System.out.println(Arrays.toString(tempArr)));
    }



    private static class FinalPermutation implements NumberPermutation<Integer[]> {
        @Override
        public List<Integer[]> getNumberPermutation(int[] nums) {
            LinkedList<Integer> track = new LinkedList<>();
            List<Integer[]> res = new ArrayList<>();
            backTracePermutation(nums,track,res);
            return res;
        }

        void backTracePermutation (int[] nums, LinkedList<Integer> track,List<Integer[]> res) {
            if (nums.length == track.size()) {
                res.add(track.toArray(new Integer[0]));
                return ;
            }
            for (int i = 0; i < nums.length; i++) {
                if (track.contains(nums[i])) continue;
                track.addLast(nums[i]);
                backTracePermutation(nums,track,res);
                track.removeLast();
            }
        }
    }


    private static class MyBackTracePermutation implements NumberPermutation<Integer[]> {
        @Override
        public List<Integer[]> getNumberPermutation(int[] nums) {
            List<Integer[]> res = new ArrayList<>();
            boolean[] flagArr = new boolean[nums.length];
            Stack<Integer> path = new Stack<>();
            backTraceNumberPermutation(nums, flagArr,path, res);
            return res;
        }

        private void backTraceNumberPermutation(int[] nums, boolean[] flagArr, Stack<Integer> path, List<Integer[]> res) {
            if (isNotValid(flagArr)) {
                res.add(path.toArray(new Integer[0]));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (flagArr[i]) {
                    continue;
                }
                flagArr[i] = true;
                path.add(nums[i]);
                backTraceNumberPermutation(nums, flagArr,path, res);
                path.pop();
                flagArr[i] = false;
            }
        }

        boolean isNotValid(boolean[] flagArr) {
            for (boolean flag : flagArr) {
                if (!flag) {
                    return false;
                }
            }
            return true;
        }

    }


    private static class MyRecursivePermutation implements NumberPermutation<int[]> {

        @Override
        public List<int[]> getNumberPermutation(int[] nums) {
            List<int[]> res = new ArrayList<>();
            recursiveNumberPermutation(nums, 0, res);
            return res;
        }

        private void recursiveNumberPermutation(int[] nums, int startIndex, List<int[]> res) {
            if (startIndex == nums.length) {
                res.add(Arrays.copyOf(nums, nums.length));
                //System.out.println(Arrays.toString(nums));
                return;
            }

            for (int i = startIndex; i < nums.length; i++) {
                swap(nums, startIndex, i);
                recursiveNumberPermutation(nums, startIndex + 1, res);
                swap(nums, startIndex, i);
            }
        }


        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

    }


    /**
     * 重复排列的接口
     */
    private interface NumberPermutation<T> {
        List<T> getNumberPermutation(int[] nums);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};

        int[] copyRes = Arrays.copyOf(arr, arr.length);
        System.out.println(Arrays.toString(copyRes));
    }
}
