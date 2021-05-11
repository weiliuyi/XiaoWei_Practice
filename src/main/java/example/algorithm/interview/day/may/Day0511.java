package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @ClassName Day0511 构造最大二叉树
 * @Description https://mp.weixin.qq.com/s/OlpaDhPDTJlQ5MJ8tsARlA
 * @Author weiliuyi
 * @Date 2021/5/11 10:47 上午
 **/
public class Day0511 {


    /**
     * 最大二叉树
     * 给定一个不包括重复元素的整数数组，以此数组构建最大二叉树定义如下：
     * 1。二叉树的根是数组中最大的元素
     * 2。左子树是通过数组中最大值左边部分构造出的最大二叉树
     * 3。右子树是通过数组中最大值的右边部分构造出的最大二叉树；
     *
     * 通过给定数组构建最大二叉树，并且输出这个树的根结点；
     */

    @Test
    public void testBuildMaxTree() {
        int[] arr = {3,2,1,6,0,5};
        TNode root = buildMaxTree(arr, 0, arr.length - 1);

        printTree(root);
    }


    /**
     * 通过前序和中序遍历结果构造二叉树
     *
     */
    @Test
    public void testPreMidOrderBuildTree () {
        int[] preOrder = {3,9,20,15,7};
        int[] inOrder = {9,3,15,20,7};
        //System.out.println(preOrder.length - 1);
        TNode root = buildPreMidOrderTree(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);

        printTree(root);

    }


    /**
     *
     */

    @Test
    public void testMidAfterBuildTree () {
        int[] inOrder = {9,3,15,20,7};
        int[] afterOrder = {9,15,7,20,3};

        TNode root = buildMidAfterOrderTree(afterOrder, 0, afterOrder.length - 1, inOrder, 0, inOrder.length - 1);

        printTree(root);
    }

    private TNode buildMidAfterOrderTree (int[] afterArr,int afL,int afR,int[] midArr,int midL,int midR) {

        if (afL > afR) return null;

        int rootNum = afterArr[afR];

        TNode root =  new TNode(rootNum);

        int index = findIndexByNum(midArr, midL, midR, rootNum);

        int rightSize = midR - index;
        // 中序遍历左子树 左子树[midL,index-1]    右子树[index+1,midR]
        // 后续遍历遍历  左子树  [afL,afR-rightSize -1]    右子树[afR-rightSize, afR-1]

        root.left = buildMidAfterOrderTree(afterArr,afL,afR-rightSize -1,midArr,midL,index-1);
        root.right = buildMidAfterOrderTree(afterArr,afR-rightSize,afR-1,midArr,index+1,midR);

        return root;
    }



    /**
     * 根据前序数组，和后续数 构造二叉树
     *
     * 前序遍历的特点： 首先遍历到的都是根结点
     * 中序遍历的特点： 结点的两侧，为此结点左右子树结点；
     *
     * 算法步骤：
     * 1。首先取得前序遍历的root
     * 2. 在中序遍历的查找root对应角标mid
     * 中序遍历： 左子树[midL,mid-1]  右子树[mid+1,midR] 左子树的个数 mid-midL    前序遍历 左子树[preL+1,pre+mid-midL] 右子树[pre+mid-midL + 1,preR]
     */
    private TNode buildPreMidOrderTree (int[] preOrder,int preL,int preR,int[] midOrder,int midL,int midR) {
        if (preL > preR) return null;

        int rootNum = preOrder[preL];
        TNode root = new TNode(rootNum);

        int midIndex = findIndexByNum(midOrder, midL, midR,rootNum);//   midindex - midL

        root.left = buildPreMidOrderTree(preOrder,preL + 1,preL + midIndex - midL ,midOrder,midL,midIndex-1);
        root.right = buildPreMidOrderTree(preOrder,preL + midIndex - midL + 1,preR,midOrder,midIndex+1,midR);
        return root;
    }


    private int findIndexByNum (int[] arr,int begin,int end,int target ) {

        for (int i = begin; i <= end ; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 1.找到数组中的最大值的maxIndex
     * 2。根据最大值创建root结点
     * 3。然后分别在[begin,maxIndex-1] 和 [maxIndex + 1,end] 构造二叉树
     */
    private TNode buildMaxTree(int[] arr, int begin, int end) {
        if (begin > end) return null;


        int maxIndex = findMaxIndex(arr, begin, end);
        TNode root = new TNode(arr[maxIndex]);

        root.left = buildMaxTree(arr,begin,maxIndex-1);

        root.right = buildMaxTree(arr,maxIndex+1,end);
        return root;
    }


    /**
     * 获取最大的值的角标
     */
    private int findMaxIndex(int[] arr, int begin, int end) {

        if (begin > end) return -1;

        int maxIndex = begin;
        for (int i = begin + 1; i <= end; i++) {
            if (arr[maxIndex] < arr[i]) maxIndex = i;
        }
        return maxIndex;
    }


    private void printTree (TNode root) {
        Deque<TNode> deque = new LinkedList<>();
        deque.add(root);
        TNode flag = root;

        while (!deque.isEmpty()) {
            TNode node = deque.poll();
            TNode left = node.left;
            TNode right = node.right;
            System.out.print("  " + node.data);
            if (left != null) deque.add(left);

            if (right != null) deque.add(right);

            if (flag == node) {
                System.out.println("");

                if (deque.isEmpty()) break;

                flag = deque.getLast();
            }
        }
    }


    private class TNode {

        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<>();
        deque.add(1);
        deque.add(2);
        System.out.println(deque.getLast());
        System.out.println(deque.getFirst());
    }
}
