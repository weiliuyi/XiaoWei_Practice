package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName Day0526 组合问题
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/1 4:15 下午
 **/
public class Day0526 {

    /**
     * 输入两个数字，n，k，算法输出[1....n]中k个数字的所有组合
     * 示例： n = 4,k = 2,输出如下结果，顺序无所谓，但是不能包含重复([1,2],[2,1] 算重复)
     * [1,2] [1,3],[1,4]
     * [2,3] [2,4]
     * [3,4]
     * 这是经典的回溯算法，k限制了树高，n限制了树的宽度，
     */
    @Test
    public void testCombine() {
        NumberCombine<Integer[]> myNumberCombine = new MyNumberCombine();
        List<Integer[]> myRes = myNumberCombine.getNumberCombine(4, 3);
        myRes.forEach(array -> System.out.println(Arrays.toString(array)));

        System.out.println("--------------------------");

        NumberCombine<Integer[]> finalNumberCombine = new FinalNumberCombine();
        List<Integer[]> finalRes = finalNumberCombine.getNumberCombine(4, 3);
        finalRes.forEach(arr -> System.out.println(Arrays.toString(arr)));

    }


    private static class FinalNumberCombine implements NumberCombine<Integer[]> {

        @Override
        public List<Integer[]> getNumberCombine(int n, int k) {
            Stack<Integer> path = new Stack<>();
            List<Integer[]> res = new ArrayList<>();
            numberCombine(n, k, path, 1, res);
            return res;
        }

        private void numberCombine(int n, int k, Stack<Integer> path, int curNum, List<Integer[]> res) {
            if (path.size() == k) {
                res.add(path.toArray(new Integer[0]));
                return;
            }

            for (int i = curNum; i <= n; i++) {
                path.add(i);
                numberCombine(n, k, path, i + 1, res);
                path.pop();
            }
        }
    }


    private static class MyNumberCombine implements NumberCombine<Integer[]> {

        @Override
        public List<Integer[]> getNumberCombine(int n, int k) {
            Stack<Integer> path = new Stack<>();
            List<Integer[]> res = new ArrayList<>();
            numberCombine(n, k, 1, 1, path, res);
            return res;
        }

        /**
         * @param n         代表数组
         * @param k         需要k个数
         * @param curNumber 当前数字
         * @param curK      第K个数字
         * @param res
         */
        private void numberCombine(int n, int k, int curNumber, int curK, Stack<Integer> path, List<Integer[]> res) {
            if (curK > k) {
                return;
            }

            for (int i = curNumber; i <= n; i++) {
                path.add(i);
                if (path.size() == k) {
                    res.add(path.toArray(new Integer[0]));
                    path.pop();
                    continue;
                }
                numberCombine(n, k, i + 1, curK + 1, path, res);
                path.pop();
            }

        }
    }

    /**
     * 数字组合的接口类
     */
    private interface NumberCombine<T> {
        List<T> getNumberCombine(int n, int k);
    }
}
