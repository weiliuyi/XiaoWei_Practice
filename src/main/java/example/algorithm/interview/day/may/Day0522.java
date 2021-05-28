package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

/**
 * @ClassName Day0522 回溯算法模版
 * @Description https://mp.weixin.qq.com/s/nMUHqvwzG2LmWA9jMIHwQQ
 * @Author weiliuyi
 * @Date 2021/5/27 7:41 下午
 **/
public class Day0522 {

    /**
     * 回溯算法的框架，解决一个回溯问题，其实就是一个决策树的遍历过程；
     * <p>
     * 路径：也就是已经做出的选择
     * 选择列表：也就是你当前可以做的选择
     * 结束条件：也就是到达决策树的底层，无法在做出选择；
     * <p>
     * 回溯算法的框架：'
     * result = [];
     * def backtrack(路径，选择列表) {
     * if (满足结束条件) {
     * result.add(路径)；
     * return；
     * }
     * for 选择 in 选择列表：
     * 做选择
     * backtrack(路径，选择列表);
     * 取消选择
     * }
     * 其核心就是for循环里面递归调用，在递归之前「做选择」，在递归之后「撤销选择」，特别简单；
     *
     *
     *
     *
     * 多叉树的遍历框架：
     * void traverse (TreeNode root) {
     *     for(TreeNode child :root.child) {
     *         //前序遍历
     *         traverse(child);
     *         //后续遍历
     *     }
     * }
     * //两个重要的时间点
     * 前序遍历的代码在进入某一个结点之前的那个时间点执行，后续遍历代码在离开某个结点之后的那个时间点执行；
     *
     * 回溯算法的核心框架：
     *
     * for 选择 in 选择列表 ：
     *      //做选择
     *      将选择从选择列表中清除
     *      路径。add（选择）
     *      backTrack(路径，选择列表);
     *      路径。remove（选择）；
     *      将该选择加入选择列表
     *
     * 最核心的思想：在递归之前做选择，在递归之后撤销选择；
     *
     */

    @Test
    public void testBackTrace() {
        int[] arr = {1, 2, 3};
        BackTraceFullArrange backTraceFullArrange = new BackTraceFullArrange();
        backTraceFullArrange.printFullArray(arr);
        System.out.println("---------------------------------------");
        RecursiveFullArrange recursiveFullArrange = new RecursiveFullArrange();
        recursiveFullArrange.printFullArray(arr);
    }


    /**
     * 使用递归的思路来解决这个问题
     */
    private static class RecursiveFullArrange implements FullArrange {

        @Override
        public void printFullArray(int[] arr) {
            recursiveFullArray(arr,0);
        }

        private void recursiveFullArray(int[] arr, int start) {
            if (start == arr.length) {
                System.out.println(Arrays.toString(arr));
                return;
            }

            for (int i = start; i < arr.length; i++) {
                swapArrIndex(arr, start, i);
                recursiveFullArray(arr, start + 1);
                swapArrIndex(arr, start, i);
            }
        }

        void swapArrIndex(int[] arr, int i, int j) {
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }

    }


    /**
     * 使用回溯算法进行全排列
     */

    private static class BackTraceFullArrange implements FullArrange {

        @Override
        public void printFullArray(int[] arr) {
            backTraceFullArray(new Stack<>(), arr, new int[arr.length]);
        }

        void backTraceFullArray(Stack<Integer> res, int[] arr, int[] flag) {
            if (isFlagValid(flag)) {
                System.out.println(Arrays.toString(res.toArray()));
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                if (flag[i] == 1) continue;

                flag[i] = 1;
                res.push(arr[i]);
                backTraceFullArray(res, arr, flag);
                res.pop();
                flag[i] = 0;
            }
        }

        private boolean isFlagValid(int[] flag) {
            for (int j : flag) {
                if (j == 0) return false;
            }
            return true;
        }
    }


    /**
     * 全排列的接口
     */
    private static interface FullArrange {
        void printFullArray(int[] arr);
    }

}
