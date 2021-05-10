package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0510 将二叉树转展成链表
 * @Description https://mp.weixin.qq.com/s/izZ5uiWzTagagJec6Y7RvQ
 * @Author weiliuyi
 * @Date 2021/5/10 12:30 下午
 **/
public class Day0510 {

    /**
     * 将二叉树展成链表
     * <p>
     * 1
     * 2      5
     * 3    4        6
     * <p>
     * 1
     * 2
     * 3
     * 4
     * 5
     * 6
     */
    @Test
    public void testFlatten() {
        TNode root = new TNode(1);
        TNode l1 = new TNode(2);
        TNode r1 = new TNode(5);
        root.left = l1;
        root.right = r1;

        TNode ll2 = new TNode(3);
        TNode lr2 = new TNode(4);

        l1.left = ll2;
        l1.right = lr2;

        TNode rr2 = new TNode(6);
        TNode rl2 = new TNode(7);

        r1.right = rr2;
        r1.left = rl2;


        //flatten(root);
        //flattenV2(root);
        flattenV3(root);
        printTree(root);

    }

    /**
     * 给flatten函数输入一个root节点，那么以root为根的二叉树会被拉平为一个链表；
     * 后续遍历
     * 1。 将root的左右子树拉平
     * 2。 将右子树接到左子树的下方，然后左子树作为右子树（将左子树插入到左子树中），并且清空左子树
     */
    private void flatten(TNode root) {

        if (root == null) return;
        flatten(root.left);
        flatten(root.right);


        //把left合并到right中去
        TNode lTemp = root.left;
        if (lTemp == null) return;


        TNode rTemp = root.right;

        root.right = lTemp; // 将左侧分支合并到右侧分支

        //找到最右侧节点
        while (lTemp.right != null) lTemp = lTemp.right;

        lTemp.right = rTemp;// 将右侧分支合并到左侧分支的末尾

        root.left = null;//清空左子树
    }


    private void flattenV2(TNode root) {
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);

        TNode lTemp = root.left;
        if (lTemp == null) return;
        //左子树的最右侧
        while (lTemp.right != null) lTemp = lTemp.right;

        lTemp.right = root.right;

        root.right = root.left;
        root.left = null; //清空左子树

    }

    /**
     * 代码精简话
     */

    private void flattenV3(TNode root) {
        if (root == null) return;

        flattenV3(root.left);
        flattenV3(root.right);
        //  后续遍历位置，左右子树已经拉成一个链表
        TNode left = root.left;
        TNode right = root.right;
        //将左子树作为右子树并且，清楚左子树
        root.left = null;
        root.right = left;
        //将原先的右子树接到当前右子树的末端
        TNode temp = root;
        while (temp.right != null) temp = temp.right;

        temp.right = right;

    }


    /**
     * 打印树
     */
    private void printTree(TNode root) {
        while (root != null) {
            System.out.println(root);
            root = root.right;
        }
    }

    /**
     * 树节点
     */
    private static class TNode {
        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }


        @Override
        public String toString() {
            return "TNode{" +
                    "data=" + data +
                    '}';
        }
    }
}
