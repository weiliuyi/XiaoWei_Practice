package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0512 二叉搜索树
 * @Description https://mp.weixin.qq.com/s/ioyqagZLYrvdlZyOMDjrPw
 * @Author weiliuyi
 * @Date 2021/5/12 4:25 下午
 **/
public class Day0512 {


    /**
     * 二叉搜索树（Binary Search Tree）
     * <p>
     * 二叉搜索树的定义：
     * 1。对于BST的每一个结点node，左子树的结点值都比node小，右子树的结点值都比node大
     * 2。对于BST的每一个结点node,它的左子树和右子树都是BST
     * <p>
     * 重要性质：BST的中序遍历结果是有序的（升序）
     * <p>
     * 基于BST的数据结构
     * 1。有AVL树，红黑树等拥有自平衡性质，可以提供log(N)级别的增删改查，
     * 2。B+树，线段树等结构都是基于BST的思想来设计的；
     *
     * BST的操作为什么这么高效？
     * 1。BST能够在对数时间找到该元素的根本原因
     * 在BST的定义中，左子树小，右子树大，所以每个结点都可以通过对比自身的值判断左子树还是右子树搜索目标值，从而避免全树遍历，达到对数级的复杂度；
     *
     *
     * 想找到第k小的元素或者说找到排名为k的元素，如果想达到对数级的复杂度，关键就是知道每个结点知道它自己排在第几名；
     *
     * 如果查找排名为k的元素，当前结点知道自己的排名第m，那么我们可以比较k和m的大小？
     * 1。如果m==k，显然找到了第K个元素，返回当前结点就可以
     * 2。如果m<k, 那么说明排名第k的元素在右子树上，所以可以去右子树搜索第k-m-1个元素；
     * 3。如果m>k, 那么说明第k的元素在左子树，可以去左子树搜索第k个元素；
     *
     * 需要在二叉树结点中维护额外的信息，每个结点需要记录以自己为根的这棵二叉树多少个结点；
     *
     */
    @Test
    public void testBST() {

    }


    private class MinKNum {

        TNode res = null;
        int count = 0;

        public MinKNum(TNode root, int k) {
            minKNum(root, k);
        }

        /**
         * 二叉搜索树中第K小的元素；
         * <p>
         * 这是一种错误的方式： 和统计二叉树结点的个数逻辑是不一样；
         * 1。左右子树的结点数 === K，此时就会出现 右子树的结点是大于根结点的，所以采用此方式是不正确的；
         */
        private int minKNum(TNode root, int k) {
            if (root == null) return 0;

            int leftNum = minKNum(root.left, k);
            int rightNum = minKNum(root.right, k);

            int nodeCount = leftNum + rightNum + 1;
            if (nodeCount == k) {
                res = root;
            }
            return nodeCount;
        }


        /**
         * 二叉搜索树中第K小的元素
         *
         * 一个直接的思路：升序排序，然后找第K个元素
         * BST的中序遍历其实就是升序排序的结果
         *
         */
        private void minKNumNormal(TNode root, int k) {
            if (root == null) return;

            minKNumNormal(root.left, k);

            count++;
            if (k == count) {
                res = root;
                return;
            }
            minKNumNormal(root.right, k);

        }

        private TNode getRes() {
            return res;
        }

    }


    private class TNode {
        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }

}
