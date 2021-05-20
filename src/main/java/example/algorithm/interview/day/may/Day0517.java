package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0517 二叉树的神级遍历方法
 * @Description 《程序员代码面试指南》
 * @Author weiliuyi
 * @Date 2021/5/19 2:15 下午
 **/
public class Day0517 {

    /*
      给定二叉树的头结点，完成二叉树的 前序遍历，中序遍历，后序遍历 要求时间复杂度O（N），空间复杂度为O（1）
      <p>
      题目分析：
      1。本题的难度在于空间复杂度O（1），之前的递归调用和非递归调用都无法满足O（1）时间复杂度；
      递归调用：实际上使用的是函数栈，
      非递归：自己申请的栈空间
      两者空间复杂度都与栈的高度有关系，所以而外的时间复杂度为O（H），H为树的高度；
      <p>
      如果完全不使用栈结构能够完成三种遍历？答案是能够，利用二叉树结点中存在大量的指向null的指针；
      <p>
      首先来看普通的递归解法和非递归解法，都使用了栈的结构，处理完某个结点之后，回到上层结点；
      为什么二叉树回到上层结点那么难？
      这是由于二叉树的结构决定的，每个结点都有指向孩子的指针，所以从上层到下层很容易，但是没有指向父节点的指针，所以必须通过栈结构辅助完成；
      <p>
      Morris遍历的实质：就是避免使用栈结构，而是让下层到上层有指针，具体是通过让底层结点指向null的空闲指针指回上层的某个结点，从而完成下层到上层的移动；
     */

    /**
     * *      4
     * *   2    7
     * * 1  3  6  9
     * * <p>
     * * 前序遍历：4，2，1，3，7，6，9
     * * 中序遍历：1，2，3，4，6，7，9
     * * 后序遍历：1，3，2，6，9，7，4
     */


    @Test
    public void testTreeIte() {
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

        MorrisAfterOrder morrisAfterOrder = new MorrisAfterOrder();
        morrisAfterOrder.morrisOrder(root);
        System.out.println("--------------------------");
        MorrisPreOrder morrisPreOrder = new MorrisPreOrder();
        morrisPreOrder.morrisOrder(root);
        System.out.println("-------------------");
        MorrisInOrder morrisInOrder = new MorrisInOrder();
        morrisInOrder.morrisOrder(root);
    }


    /**
     * Morris前序遍历是中序遍历的简单改写
     */
    private static class MorrisPreOrder implements MorrisOrder {

        @Override
        public void morrisOrder(TNode root) {
            if (root == null) return;

            TNode cur1 = root,cur2;
            while (cur1 != null) {
                cur2 =  cur1.left;
                //System.out.print(cur1.data +  " "); 为什么不能在这个地方进行打印？ 循环到这个地方有两种可能？
                // 1。通过左子树的指针到达这个地方，2。底层结点的right指针到达这个地方；此时就会出现打印重复的问题；
                if (cur2 != null) {
                    while (cur2.right != null && cur2.right != cur1 ) { //cur2.right != null 有两种含义： 第一种指向右子树，第二种执行：上层结点（下一个结点）
                        cur2 = cur2.right;
                    }
                    if (cur2.right == null) { //cur2 一定时左子树的最右结点，且没有初始化过
                        cur2.right = cur1;
                        System.out.print(cur1.data +  " ");
                        cur1 = cur1.left;
                        continue;   // 这个地方很是巧妙， 从本行开始以上的代码构成了一个循环；
                    }
                    cur2.right = null;
                } else { //没有左孩子的结点时
                    System.out.print(cur1.data + " ");
                }

                cur1 = cur1.right; //返回上层结点 或者 右子树
            }
            System.out.println();
        }
    }

    /**
     * Morris使用后续遍历二叉树 也是Morris中序遍历的改写，但是包含更加复杂的调整过程，
     * 总的来说，逻辑很简单，依次逆序打印所有结点左子树的右边界；
     *
     *
     *
     * 后续遍历的特点：
     * 1。遍历的第一个元素；左子树的最左结点；
     * 2。某一元素的下一个结点，这个元素的父节点的右子树的最左结点；
     */
    private static class MorrisAfterOrder implements MorrisOrder{

        @Override
        public void morrisOrder(TNode root) {
            if (root == null) return;

            TNode cur1 = root,cur2;

            while (cur1 != null) {
                cur2 = cur1.left;
                if (cur2 != null) { //左子树不为空
                    while (cur2.right != null && cur2.right != cur1) {
                        cur2 = cur2.right;
                    }
                    if (cur2.right == null) {
                        cur2.right = cur1;
                        cur1 = cur1.left; // 此时有没有可能导致 cur1可能等于 null， 其实是不可能，这是因为cur2不等于null，而cur2又是当前cur1的左子树头结点；
                        continue;
                    } else {
                        cur2.right = null;
                        printEdge(cur1.left);
                    }
                }
                cur1 = cur1.right;
            }
            printEdge(root);
            System.out.println();
        }

        private void printEdge (TNode head) {
            TNode tail = reverse(head);
            TNode cur = tail;
            while (cur != null) {
                System.out.print( cur.data+ " ");
                cur =  cur.right;

            }
            reverse(tail);
        }


        /**
         * 逆序一个链表
         * 基本操作思想：使用双指针，第一个结点指向null
         * pre:指向前一个node
         * next:临时指针，主要用来暂存head的right指针；
         */
        private TNode reverse (TNode head) {
            TNode pre = null,next =null;
            while (head != null) {
                next = head.right;
                head.right = pre;
                pre = head;
                head = next;
            }
            return pre;
        }


    }


    /**
     * Morris使用中序进行遍历二叉树；
     * 中序遍历的特点：
     * 1。根结点的前一个结点在，根结点左子树的最右结点上（根结点的左子树的最右结点的下一个元素是根结点）
     * 2.遍历的第一个结点 根结点左子树的最左结点
     *
     */
    private static class MorrisInOrder implements MorrisOrder {

        @Override
        public void morrisOrder(TNode root) {
            morrisInOrderV3(root);
            System.out.println("----------");
            morrisInOrderV2(root);
            System.out.println("----------------");
            morrisInOrder(root);
        }

        /**
         * 这个地方没有下层到上层的索引；
         * 自己的思路；
         */
        private void morrisInOrder(TNode root) {
            TNode head = root;

            while (head != null) {
                //一旦发现左子树为null，说明已经到达某个二叉树的最左结点；
                while (head.left != null) {
                    //root为根左子树的最右结点；
                    TNode maxRight = getMaxRight(head);
                    //左子树的最右结点指向上层结点；
                    maxRight.right = head;
                    head = head.left;
                }
                //head.left == null  开始打印数据
                while (head != null) {
                    TNode rNode = getMaxRight(head);
                    if (rNode == head || rNode.right == head) {
                        System.out.print(" " + head.data);
                        head = head.right;
                        continue;
                    }
                    break;
                }
            }
        }

        /**
         * 清除线索
         */
        private void morrisInOrderV2(TNode root) {
            TNode head = root;
            while (head != null) {
                while (head.left != null) {
                    TNode maxRight = getMaxRightV2(head);
                    maxRight.right = head;
                    head = head.left;
                }

                System.out.print(" " + head.data);
                //打印数据
                while (true) {
                    head = head.right;
                    if (head == null) break;
                    TNode rNode = getMaxRightV2(head);
                    if (rNode == null || head == rNode.right) {
                        //if (head == rNode.right || head == rNode)
                        System.out.print(" " + head.data);
                        if (rNode != null) rNode.right = null;
                        continue;
                    }
                    break;
                }
            }

            /*

             *      4
             *   2    7
             * 1  3  6  9
             * <p>
             * 前序遍历：4，2，1，3，7，6，9
             * 中序遍历：1，2，3，4，6，7，9
             * 后序遍历：1，3，2，6，9，7，4
             */

            System.out.println();
        }

        // 每一行代码都没有多余；
        private void morrisInOrderV3 (TNode head) {
            if (head == null) return;

            TNode cur1 = head,cur2; //cur1 代表当前的头结点，cur2代表当前头结点cur1的左子树最右结点；

            while (cur1 != null) {
                // 将左子树的头结点复制给cur2，然后求的然后求解以cur2为根的左子树的最右结点；
                cur2 = cur1.left;
                //如果cur == null 只能说明一个问题，那就是以cur2为根的树没有左子树；直接可以打印此结点的值；
                if (cur2 != null) {
                    //这个判断条件为什么要加上 cur2.right != cur1，这是因为线索的问题,
                    // 特殊情况：cur2是cur1的左子树的最右结点，此时就会cur2.right == cur1,如果不增加这个判断的话，就会找到cur1的右子树上的最右结点；
                    while (cur2.right != null && cur2.right != cur1) {
                        cur2 = cur2.right;
                    }
                    // 确定清除线索还是设置线索；
                    if (cur2.right == null) { //没有设置过指针，说明是第一次
                        cur2.right = cur1;  //将以cur1为根的左子树的最右结点指向cur1，设置线索
                        cur1 = cur1.left;   //以左子树为根继续设置线索；
                        continue;     // 这个地方也是一个比较核心的地方
                    } else { //此指针已经使用过，没有作用，需要进行清除；
                        cur2.right = null;
                    }
                }
                System.out.print(cur1.data +  " ");
                cur1 = cur1.right; // 这个地方：返回上层结点，或者 右子树
            }
            System.out.println();
        }

        /**
         * 获取以root为根的二叉树的左子树最右结点；
         */
        private TNode getMaxRight(TNode root) {
            TNode res = root;
            if (res.left != null) {
                res = res.left;
                while (res.right != null && res.right != root) {
                    res = res.right;
                }
            }
            return res;
        }

        private TNode getMaxRightV2(TNode root) {
            if (root.left == null) return null;

            TNode res = root.left;

            while (res.right != null && res.right != root) {
                res = res.right;
            }
            return res;
        }


    }

    private interface MorrisOrder {

        void morrisOrder (TNode root);
    }

    private static class TNode {
        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }


}
