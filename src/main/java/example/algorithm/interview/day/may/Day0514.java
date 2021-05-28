package example.algorithm.interview.day.may;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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

        TNode lr3 = new TNode(4);
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


        AbstractEdgeTree<List<Entry>> abTreeV1 = new PrintEdgeTreeV1();
        List<Integer> res = abTreeV1.getTreeEdgeRes(root);
        System.out.println(JSON.toJSONString(res));

        System.out.println("---------------------");

        AbstractEdgeTree<TNode[][]> abTreeV2 = new PrintEdgeTreeV12();
        List<Integer> res2 = abTreeV2.getTreeEdgeRes(root);
        System.out.println(JSON.toJSONString(res2));
        //System.out.println(getHeight(root, 0));
        //System.out.println(getHeightV2(root));

        System.out.println("---------------------------");

        printEdgeTreeV2 abTree = new printEdgeTreeV2();
        System.out.println(JSON.toJSONString(abTree.getTreeEdgeRes(root)));

    }


    /**
     * 按照标准二的方式进行打印的树的边界；
     */
    private class printEdgeTreeV2  {


        private List<Integer> getTreeEdgeRes(TNode root) {
            List<Integer> res = new ArrayList<>();
            List<Integer> left = getLeftNode(root);
            List<Integer> right = getRightBoundNodes(root);
            res.addAll(left);
            res.addAll(getBottomNodes(root,left,right));
            res.addAll(right);
            return res;
        }

        private List<Integer> getBottomNodes (TNode root,List<Integer> left,List<Integer> right) {
            List<Integer> res = new ArrayList<>();
            fillBottomBoundNode2Res(root,left,right,res);
            return res;
        }

        private void fillBottomBoundNode2Res (TNode root,List<Integer> left,List<Integer> right,List<Integer> res) {
            if (root == null) return ;

            if (root.left == null && root.right == null && !left.contains(root.data) && !right.contains(root.data)) {
                res.add(root.data);
            }
            fillBottomBoundNode2Res(root.left,left,right,res);
            fillBottomBoundNode2Res(root.right,left,right,res);
        }

        private List<Integer> getLeftNode (TNode root) {
            List<Integer> res = new ArrayList<>();
            fillLeftBoundNodes2Res(root,res);
            return res;
        }

        private void fillLeftBoundNodes2Res (TNode root,List<Integer> res) {
            if (root == null) return;

            res.add(root.data);
            if (root.left != null) {
                fillLeftBoundNodes2Res(root.left,res);
            } else {
                fillLeftBoundNodes2Res(root.right,res);
            }
        }

        private List<Integer> getRightBoundNodes (TNode root) {
            List<Integer> res= new ArrayList<>();
            fillRightBoundNode2Res(root,res);
            return res;
        }

        private void fillRightBoundNode2Res (TNode root,List<Integer> res) {
            if (root == null) return;

            if (root.right != null) {
                fillRightBoundNode2Res(root.right,res);
            } else {
                fillLeftBoundNodes2Res(root.left,res);
            }
            res.add(root.data);
        }



    }


    private class Entry {
        TNode l, r;

        public Entry(TNode l, TNode r) {
            this.l = l;
            this.r = r;
        }
    }

    /**
     * 使用数组的方式
     */
    private class PrintEdgeTreeV12 extends AbstractEdgeTree<TNode[][]> {

        @Override
        List<Integer> getLeftBoundNodes(TNode[][] level) {
            List<Integer> result = new ArrayList<>();
            for (TNode[] tNodes : level) {
                result.add(tNodes[0].data);
            }
            return result;
        }

        @Override
        List<Integer> getLeafBoundNodes(TNode root, TNode[][] levelR) {
            List<Integer> res = new ArrayList<>();
            Set<Integer>  lrNodeSet = Arrays.stream(levelR).flatMap(Arrays::stream).map(en -> en.data).collect(toSet());
            fillBottom2Res(root,res,lrNodeSet);
            return res;
        }

        private void fillBottom2Res (TNode root,List<Integer> res,Set<Integer> lrNodeSet) {
            if (root == null) return;

            if (root.left == null && root.right == null && !lrNodeSet.contains(root.data)) {
                res.add(root.data);
            }
            fillBottom2Res(root.left,res,lrNodeSet);
            fillBottom2Res(root.right,res,lrNodeSet);
        }

        @Override
        List<Integer> getRightBoundNodes(TNode[][] level) {
            List<Integer> res = new ArrayList<>();
            for (int i = level.length-1; i >= 1 ; i--) {
                res.add(level[i][1].data);
            }
            return res;
        }

        @Override
        TNode[][] getLevelLR(TNode root) {
            int height = getHeight(root,0);
            TNode[][] res = new TNode[height][2];
            fillLevelLr2Res(root,res,0);
            return res;
        }

        private void fillLevelLr2Res (TNode root,TNode[][] res,int level) {
            if (root == null) return;

            if (res[level][0] == null ) {
                res[level][0] = root;
            }
            res[level][1] = root;

            fillLevelLr2Res(root.left,res,level + 1);
            fillLevelLr2Res(root.right,res,level + 1);
        }



        /**
         * 获得二叉树层数
         */
        private int getHeight(TNode root, int l) {
            if (root == null) return l;

            return Math.max(getHeight(root.left, l + 1), getHeight(root.right, l + 1));
        }


        /**
         * 这个逻辑很好理解：
         * 左右子树的中最高层数加一
         */
        private int getHeightV2(TNode root) {
            if (root == null) return 0;

            return Math.max(getHeightV2(root.left), getHeightV2(root.right)) + 1;
        }

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

    private class PrintEdgeTreeV1 extends AbstractEdgeTree<List<Entry>> {


        /**
         * 左边界结点
         */
        @Override
        List<Integer> getLeftBoundNodes(List<Entry> level) {
            return level.stream().map(entry -> entry.l.data).collect(toList());
        }

        /**
         * 叶子结点不包括最左和最右结点
         */
        @Override
        List<Integer> getLeafBoundNodes(TNode root, List<Entry> levelR) {
            List<Integer> res = new ArrayList<>();
            Set<Integer> lRNodeList = levelR.stream().map(en -> new Integer[]{en.l.data, en.r.data}).flatMap(Arrays::stream).collect(toSet());
            fillResBottomNode(root, res, lRNodeList);
            return res;
        }


        /**
         * 叶子结点不包括最左和最右结点
         */
        private void fillResBottomNode(TNode root, List<Integer> res, Set<Integer> lRNodeSet) {
            if (root == null) return;

            if (root.left == null && root.right == null && !lRNodeSet.contains(root.data)) {
                res.add(root.data);
            }

            fillResBottomNode(root.left, res, lRNodeSet);
            fillResBottomNode(root.right, res, lRNodeSet);
        }


        /**
         * 右边界结点
         */
        @Override
        List<Integer> getRightBoundNodes(List<Entry> level) {
            List<Integer> result = new ArrayList<>();
            for (int i = level.size() - 1; i >= 1; i--) {
                result.add(level.get(i).r.data);
            }
            return result;
        }


        /**
         * 如何解决最左侧的元素和最右侧元素？？？
         * 最右侧元素：
         * 上层元素遍历到最后一个时，可以确定下层最右侧元素
         * 最左侧元素：
         * 也就是：上层元素结束之后遍历到的第一个元素；
         */
        @Override
        List<Entry> getLevelLR(TNode root) {
            List<Entry> result = new ArrayList<>();
            Deque<TNode> deque = new LinkedList<>();
            deque.add(root);
            result.add(new Entry(root,root));
            TNode flag = root;
            while (!deque.isEmpty()) {
                TNode node = deque.poll();
                TNode left = node.left;
                TNode right = node.right;
                if (left != null) deque.add(left);

                if (right != null) deque.add(right);

                if (flag == node) {
                    if (deque.isEmpty()) break;
                    flag = deque.peekLast();
                    result.add(new Entry(deque.peekFirst(), flag));
                }
            }
            return result;
        }


    }

    private abstract class AbstractEdgeTree<T> {

        /**
         * 左边界
         */
        abstract List<Integer> getLeftBoundNodes(T level);

        /**
         * 底部边界
         */
        abstract List<Integer> getLeafBoundNodes(TNode root, T levelR);

        /**
         * 右部边界
         */
        abstract List<Integer> getRightBoundNodes(T level);

        abstract T getLevelLR(TNode root);


        /**
         * 打拼树的边界结点的骨架
         */
        public List<Integer> getTreeEdgeRes(TNode root) {
            ArrayList<Integer> result = new ArrayList<>();
            T levelR = getLevelLR(root);
            result.addAll(getLeftBoundNodes(levelR));
            result.addAll(getLeafBoundNodes(root, levelR));
            result.addAll(getRightBoundNodes(levelR));
            return result;
        }

    }


    @Test
    public void testTemp() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.stream().forEach(System.out::println);
    }

    private class TNode {
        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }

}
