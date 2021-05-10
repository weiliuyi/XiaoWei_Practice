package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Day0505 二叉树
 * @Description https://mp.weixin.qq.com/s/izZ5uiWzTagagJec6Y7RvQ
 * @Author weiliuyi
 * @Date 2021/5/7 2:23 下午
 **/
public class Day0505 {


    /**
     * 树的递归遍历框架
     * void traverse (TreeNode root) {
     *      //前序遍历
     *      traverse(root.left);
     *      //中序遍历
     *      traverse(root.right)
     *      //后续遍历
     * }
     *
     * 写递归算法的关键是：明确函数「定义」是什么，然后相信这个定义，利用这个定义推导
     */

    /**
     * 翻转二叉树
     * <p>
     * 4
     * 2    7
     * 1  3  6  9
     */

    @Test
    public void testReverseTree() {
        TNode root = new TNode(4);

        TNode left1 = new TNode(2);
        TNode right1 = new TNode(7);

        root.left = left1;
        root.right = right1;

        TNode lleft2 = new TNode(1);
        TNode lright2 = new TNode(3);
        left1.left = lleft2;
        left1.right = lright2;

        TNode rleft2 = new TNode(6);
        TNode rright2 = new TNode(9);

        right1.left = rleft2;
        right1.right = rright2;

        printTree(root);
        traverseTree(root);
        printTree(root);

    }

    /**
     * 填充每一个结点的下一个右侧节点指针
     */

    @Test
    public void testConnect () {

    }

    /**
     * 将二叉树展成列表
     */
    @Test
    public void testFlatten () {

    }

    void flatten (TNode root) {

    }

    /**
     * 填充每个结点的下一个右侧结点指针
     */

    private void connect (TNode root,TNode levelNode) {
        if (root == null) return;

        root.next = levelNode;
        connect(root.left,root.right);
        if (levelNode != null) {
            connect(root.right,levelNode.left);
            connect(levelNode.left,levelNode.right);
        }

    }

    private TNode connect (TNode root) {
        if (root == null) return null;

        return root;
    }

    private void connectTwo (TNode node1,TNode node2) {
        if (node1 == null || node2 == null) return ;

        node1.next = node2;

        // 连接相同父节点的两个子节点
        connectTwo(node1.left,node1.right);
        connectTwo(node2.left,node2.right);
        //连接跨越父节点的两个子节点
        connectTwo(node1.right,node2.left);


    }



    /**
     * 翻转二叉树
     */
    private void traverseTree(TNode root) {

        if (root == null) return;
        TNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        traverseTree(root.left);
        traverseTree(root.right);

    }

    //按照层级打印树的结构
    private void printTree(TNode root) {
        Queue<TNode> queue = new LinkedList<>();
        queue.add(root);
        TNode flag = root;
        while (!queue.isEmpty()) {
            TNode ele = queue.poll();
            TNode left = ele.left;
            TNode right = ele.right;
            if (left != null) queue.add(left);

            if (right != null) queue.add(right);

            System.out.print(" " + ele.data);
            if (flag == ele) {
                System.out.println("");
                if (left != null || right != null) {
                    flag = (right != null ? right : left);
                }
            }
        }
    }


    private static class TNode {

        private TNode left, right,next;
        private int data;

        public TNode(int data) {
            this.data = data;
        }
    }

}
