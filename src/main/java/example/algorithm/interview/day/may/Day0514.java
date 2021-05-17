package example.algorithm.interview.day.may;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName Day0514 打印二叉树的边界
 * @Description 《程序员代码面试指南》
 * @Author weiliuyi
 * @Date 2021/5/14 7:34 下午
 **/
public class Day0514 {


    /**
     * 给定二叉树的结点root,按照两种标准分别实现二叉树边界结点的逆时针打印；
     * <p>
     * <p>
     * 标准一：
     * 1。头结点是边界结点
     * 2。叶子结点是边界结点
     * 3。如果结点在其所在的层中，最左，最右那么也是边界结点
     * <p>
     * <p>
     * <p>
     * 问题分析：
     * <p>
     * 标准二：
     * 1。头结点是边界结点
     * 2。叶子结点是边界结点
     * 3。树的左边界的延伸下去的结点也是叶子结点
     * 4。树的右边界延伸下去的结点也是叶子结点
     */
    @Test
    public void testTreeBound() {

        TNode root = new TNode(1);
        TNode l2 = new TNode(2);
        TNode r2 = new TNode(3);

        root.left = l2;
        root.right = r2;

        TNode  lr3= new TNode(4);
        l2.right = lr3;

        TNode rl3 = new TNode(5);
        TNode rr3 = new TNode(6);
        r2.left = rl3;
        r2.right = rr3;

        TNode lrl4 = new TNode(7);
        TNode lrr4 = new TNode(8);
        lr3.left = lrl4;
        lr3.right = lrr4;

        TNode rll4 = new TNode(9);
        TNode rlr4 = new TNode(10);
        rl3.left = rll4;
        rl3.right = rlr4;

        TNode lrrr5 = new TNode(11);
        lrr4.right = lrrr5;

        TNode rlll5 = new TNode(12);
        rll4.left = rlll5;

        TNode lrrrl6 = new TNode(13);
        TNode lrrrr6 = new TNode(14);
        lrrr5.left = lrrrl6;
        lrrr5.right = lrrrr6;

        TNode rllll6 = new TNode(15);
        TNode rlllr6 = new TNode(16);
        rlll5.left = rllll6;
        rlll5.right = rlllr6;



        List<Entry> entryList = getLevelLREntry(root);
        for (Entry entry: entryList) {
            System.out.print(" "+ entry.l.data);
            System.out.print(" " + entry.r.data);
            System.out.println(" ");
        }

        List<Integer> result = printEdgeV1(root);
        System.out.println(JSON.toJSON(result));


    }


    /**
     * 问题分析：
     * <p>
     * 1。叶子结点？
     * 左右结点都为空的时候
     * 2。如何实现逆时针打印呢？
     * 使用递归都无法满足题目的需求，如果是非递归呢？采用不同的方式进行遍历 思路跑偏
     * 前序遍历
     * 中序遍历
     * 后续遍历
     * 基本操作，计算出每一层的最左，最右结点，然后先打印最左结点，然后打印叶子结点，然后打印最右结点；
     * <p>
     * <p>
     * 3。如何判断层中最左和最右结点呢？ 层级遍历可以确定？？ ---- 想到这一步已经很接近答案了
     * 使用层次遍历可以找到曾中最左或者最右节点；但是没有办法实现逆时针打印；
     * <p>
     * <p>
     * 算法步骤：
     * 1。得到二叉树每一层的最左，最右结点
     * 2。然后打印每一层的最左结点
     * 3。打印叶子结点
     * 4。从下向上打印最右侧结点；
     * 完成逆时针的打印；
     */


    private List<Integer> printEdgeV1(TNode root) {

        //打印左侧结点
        List<Entry> entryList = getLevelLREntry(root);

        // 所有层的最左和最右集合；
        List<Integer> bounds = getTreeBound(entryList);
        List<Integer> result = new ArrayList<>(getLeftBound(entryList));
        fillResultLeafNode(bounds,root,result);
        result.addAll(getRightBound(entryList));
        return result;
    }


    /**
     * 叶子结点不包括最左和最右结点
     */
    private void fillResultLeafNode(List<Integer> col, TNode root, List<Integer> res) {

        if (root == null) return;

        if (root.left == null && root.right == null && !col.contains(root.data)) {
            res.add(root.data);
        }
        fillResultLeafNode(col,root.left,res);
        fillResultLeafNode(col,root.right,res);
    }


    private List<Integer> getLeftBound(List<Entry> entries) {
        List<Integer> result = entries.stream().map(entry -> entry.l.data).collect(Collectors.toList());
        return result;
    }

    private List<Integer> getRightBound(List<Entry> entries) {
        List<Integer> result = new ArrayList<>();
        for (int i = entries.size()-1; i >= 1 ; i--) {
            result.add(entries.get(i).r.data);
        }
        return result;
    }


    /**
     * 获得所有所有的边界结点
     */
    private List<Integer> getTreeBound (List<Entry> entries) {
        return entries.stream().
                map(entry -> new Integer[]{entry.l.data, entry.r.data}).
                flatMap(Arrays::stream).collect(Collectors.toList());
    }

    /**
     * 如何解决最左侧的元素和最右侧元素？？？
     * 最右侧元素：
     * 上层元素遍历到最后一个时，可以确定下层最右侧元素
     * 最左侧元素：
     * 也就是：上层元素结束之后遍历到的第一个元素；
     */
    private List<Entry> getLevelLREntry(TNode root) {
        List<Entry> result = new ArrayList<>();
        Deque<TNode> queue = new LinkedList<>();
        queue.push(root);
        TNode flag = root;
        result.add(new Entry(root,root));
        while (!queue.isEmpty()) {
            TNode temp = queue.poll();
            TNode right = temp.right;
            TNode left = temp.left;
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }

            if (flag == temp) {
                if (queue.isEmpty()) break;
                flag = queue.peekLast();//最右侧的元素解决了
                result.add(new Entry(queue.peekFirst(),flag));
            }
        }
        return result;
    }


    private class Entry {
        TNode l, r;

        public Entry(TNode l, TNode r) {
            this.l = l;
            this.r = r;
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
