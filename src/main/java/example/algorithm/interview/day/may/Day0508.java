package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Day0508 最近的公共祖先
 * @Description https://mp.weixin.qq.com/s/9RKzBcr3I592spAsuMH45g
 * @Author weiliuyi
 * @Date 2021/5/10 2:27 下午
 **/
public class Day0508 {


    /**
     *  最近的公共祖先（Lowest Common Ancestor LCA）
     *
     *
     *              3
     *       5             1
     *   6      2      0        8
     *       7     4
     *
     *  TODO 进阶的版本：《程序员代码面试指南》
     *
     *  2。如果查询两个结点的公共祖先的结点非常频繁，试图减少单词查询的时间
     *  3。给定二叉树的头结点head，同时给定所有想要进行的查询，二叉树的结点数为N，进行的查询为M
     *  要求在时间复杂度为O(N + M)
     */

    @Test
    public void testLCA () {
        Integer[] num = {3,5,1,6,2,0,8,null,null,7,4};
        TNode root = buildTree(num);
        printTree(root);
    }


    private void printTree (TNode root) {
        Queue<TNode> queue = new LinkedList<>();
        queue.add(root);
        TNode flag = root;
        while (!queue.isEmpty()) {
            TNode temp = queue.poll();
            TNode left = temp.left;
            TNode right = temp.right;
            System.out.print("  " + temp.data);
            if (left != null) {
                queue.add(left);
            }
            if (right != null ) {
                queue.add(right);
            }
            if (flag == temp ) {
                System.out.println("");

                // 这个地方太蠢了，本质就是队列的最后一个元素
                if ((left != null || right != null)) {
                    flag = (right != null) ?  right : left;
                } else {
                    //flag = 队列末尾的元素
                }
            }
        }
    }


    /**
     * 构造二叉树
     */
    private TNode buildTree (Integer[] num) {
        TNode root = new TNode(num[0]);
        Queue<TNode> queue = new LinkedList<>();
        queue.add(root);

        for (int i = 1; i < num.length; i= i + 2) {
            TNode temp = queue.poll();
            if (num[i] != null) {
                TNode left = new TNode(num[i]);
                temp.left = left;
                queue.add(left);
            }

            if (i+1 < num.length && num[i+1] != null) {
                TNode right = new TNode(num[i+1]);
                temp.right = right;
                queue.add(right);
            }
        }
        return root;
    }



    /**
     * 最近的公共祖先结点
     * 这个方法中的局限性：（p，q一定存在于以root为根的树中）
     * 1。结点已经存在，而且唯一；
     * 2。最近的公共祖先一定有解；
     *
     * 递归解法的灵魂三问：
     * 1。这个函数是干什么的？
     * 2。函数的参数中的 变量 是什么？
     * 3。得到函数的递归结果，你应该干什么？
     *
     *
     * 这个函数是干嘛呢？
     * 描述：给该函数输入三个参数root,q,p,返回一个结点
     *
     * 情况一：如果p和q存在以root为根的树中，函数返回的即是p和q的最近的公共祖先
     * 情况二：如果p和q都不存在以root为根的树中怎么办？函数理所因当的返回null
     * 情况三：如果p和q只有一个存在于root为根的树中怎么办？函数就会返回那个结点；
     *
     * 函数的参数中，变量是什么
     * 函数的中的参数列表中的变量是root，递归的调用root.left,root.right,
     * 至于p，q，我们要求他们两个的祖先，他俩肯定不会变化的。
     *
     *
     * 得到递归函数的结果该干什么？选择
     *
     * base case:
     * 如果root为空，肯定返回null，
     * 如果root本身就是p或者q，
     * 比如root就是p结点，q存在于以root为根的树中，显然root就是最近的公共祖先，即是q不存在于以root为根的树中，按照情况三的定义也应该返回root结点；
     *
     *
     * 情况一：如果p，q都在以root为根的树中，那么left和right一定分别是p和q（从base case）
     * 情况二：如果p，q都不在以root为根的树中，直接返回null
     * 情况三：如果p，q只有一个存在以root为根的树中，函数返回该结点；
     *
     */
    private TNode lowestCommonAncestor(TNode node, int p, int q) {
        if (node == null) return null;

        if (node.data == p || node.data == q) return node;

        TNode left = lowestCommonAncestor(node.left, p, q);
        TNode right = lowestCommonAncestor(node.right, p, q);

        if (left != null && right != null) return node;

        if (left == null && right == null) return null;

        return left == null ? right : left;

    }


    private class TNode {
        TNode left,right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }

}
