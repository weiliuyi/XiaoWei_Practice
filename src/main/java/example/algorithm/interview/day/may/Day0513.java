package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Stack;

/**
 * @ClassName Day0513 二叉树的 前序遍历 中序遍历 后序遍历  递归非递归
 * @Description 《程序员代码面试指南》
 * @Author weiliuyi
 * @Date 2021/5/13 8:03 下午
 **/
public class Day0513 {


    /**
     *     4
     *  2    7
     *1  3  6  9
     *
     * 前序遍历：4，2，1，3，7，6，9
     * 中序遍历：1，2，3，4，6，7，9
     * 后序遍历：1，3，2，6，9，7，4
     */


    @Test
    public void testTreeOrder () {
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

        RecursiveIte rec = new RecursiveIte();

        NoRecursiveIte noRec = new NoRecursiveIte();

        rec.preOrder(root);
        System.out.println("");
        noRec.preOrder(root);

        System.out.println("-------------");


        rec.inOrder(root);
        System.out.println("");
        noRec.inOrder(root);

        System.out.println("------------");
        rec.afterOrder(root);
        System.out.println("");
        noRec.afterOrder(root);



    }

    /**
     * 非递归的方式遍历二叉树
     */
    private class NoRecursiveIte {

        /*
         * 无论何种遍历方式？都是从左子树向右子树进行遍历；
         */

        /**
         * 前序遍历
         * 1。对于每个结点先访问，然后在压站
         */
        private void preOrder(TNode root) {

            TNode node = root;
            Stack<TNode> stack = new Stack<>();
            while (!stack.isEmpty() || node != null) {
                if (node == null) {
                    TNode temp = stack.pop();
                    node = temp.right;
                    continue;
                }
                System.out.print(" " + node.data);
                //node ！= null，将node元素进栈，并且将node的左结点重新赋值给node，
                // 这样做的目的为了将树的最左结点进栈；
                stack.push(node);
                node = node.left;
            }
            System.out.println("");
        }

        /**
         * 中序遍历
         * 1.结点首先入栈，出栈时访问结点
         * <p>
         * 为什么出栈访问结点就是中序遍历？
         * 这是因为进栈的的顺序决定的，首先就是左结点全部进栈，
         * 假如此结点出栈，那么说明以这棵树为根的所有左结点已经出栈完毕（所有的左结点已经被访问过）
         */
        private void inOrder(TNode root) {
            TNode node = root;
            Stack<TNode> stack = new Stack<>();

            while (node != null || !stack.isEmpty()) {
                if (node == null) { // node == null，说明一定遍历到二叉树的最左结点,此时需要将这个最左结点弹出，
                    TNode temp = stack.pop();
                    //最左结点弹出之后，需要把此结点的右结点赋值给node,
                    //这样做的目的是为了将最左结点所有右结点入栈

                    System.out.print(" " + temp.data);
                    node = temp.right;
                    continue;
                }
                //node不等于null，node进站，并且将node的左子树赋值给node，
                // 这样左的目的，可以将以node为根树的最左结点进站；
                stack.push(node);
                node = node.left;
            }
            System.out.println("");
        }


        /**
         * 后续遍历
         */
        public void afterOrder(TNode root) {
            TNode node = root,lastNode = null;
            Stack<TNode> stack = new Stack<>();

            while (node != null || !stack.isEmpty()) {

                if (node != null) {
                    //node的左结点进栈（所有左子树的左结点进栈）
                    stack.push(node);
                    node = node.left;
                    continue;
                }
                TNode temp = stack.peek();
                if (temp.right == null || temp.right == lastNode) {
                    lastNode = stack.pop();
                    System.out.print(" " + lastNode.data);
                } else {
                    node = temp.right;
                }
            }
            System.out.println("");
        }

    }


    /**
     * 递归的方式遍历二叉树
     */
    private class RecursiveIte {
        /**
         * 前序遍历
         */
        private void preOrder(TNode root) {
            if (root == null) return;

            System.out.print(" " + root.data);
            preOrder(root.left);
            preOrder(root.right);
        }

        /**
         * 中序遍历
         */
        private void inOrder(TNode root) {
            if (root == null) return;
            inOrder(root.left);
            System.out.print(" " + root.data);
            preOrder(root.right);
        }


        /**
         * 后序遍历
         */
        private void afterOrder(TNode root) {
            if (root == null) return;

            afterOrder(root.left);
            afterOrder(root.right);
            System.out.print(" " + root.data);
        }
    }


    /**
     * 二叉树的结点
     */
    private class TNode {
        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }


}
