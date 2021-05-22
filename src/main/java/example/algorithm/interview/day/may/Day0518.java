package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0518  在二叉树中找到累加和为指定值的最长路径长度
 * @Description 《程序员代码面试指南》
 * @Author weiliuyi
 * @Date 2021/5/20 3:33 下午
 **/
public class Day0518 {

    /**
     * 首先要解决的问题是：在未排序的数组中累加和为指定值的最长子数组
     *
     */
    @Test
    public void testMaxPath () {

    }


    /**
     * 数组中累加和为指定值的最长子数组；
     */
    private static class MaxLengthArray implements MaxLengthStruct<int[]> {
        @Override
        public int maxLength(int[] struct, int target) {
            return 0;
        }

        private int maxLengthSubArray (int[] struct,int target,int index) {
            return 0;
        };
    }


    /**
     * 二叉树中累加和为指定值的最长路径；
     */
    private static class MaxLengthTree implements MaxLengthStruct<TNode> {
        @Override
        public int maxLength(TNode struct, int target) {
            return 0;
        }

        private int treeMaxPath (TNode root,int target,int num) {
            if (root == null) return 0;
            return 0;
        }

    }

    /**
     * 最长的数据结构：
     * 数组：累加和为指定值的最长子数组
     * 二叉树：累加和为指定值的最长路径；
     */
    private static interface MaxLengthStruct<T> {

        int maxLength (T struct,int target);
    }


    private static class TNode {
        TNode left,right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }

}
