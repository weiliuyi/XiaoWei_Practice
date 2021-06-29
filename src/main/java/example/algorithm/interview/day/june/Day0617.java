package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Day0617 BFS
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485134&idx=1&sn=fd345f8a93dc4444bcc65c57bb46fc35&chksm=9bd7f8c6aca071d04c4d383f96f2b567ad44dc3e67d1c3926ec92d6a3bcc3273de138b36a0d9&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/22 5:39 下午
 **/
public class Day0617 {

    /*
     * BFS的核心思想并不难理解，就是把一些问题抽象成图，从一个点开始，向四周开始扩散，
     * 一般来说，我们写BFS这种数据结构，都是用到队列的数据结构，一次将一个节点周围的所有节点加入队列；
     * <p>
     * BFS和DFS最主要的区别是，BFS找到的路径一定是最短的，代价就是BFS的空间复杂度闭DFS大很多；
     * <p>
     * 「二叉树的最小高度」 「打开密码锁的最小步数」
     * BFS的常用场景：问题的本质就是让你在一幅图中从起点start到终点target的最近距离；
     * 广义的描述可以有各种变体，
     * 比如走迷宫，有的格子是围墙不能走，从起点到重点的最短距离是多少？如果这个迷宫带「传送门」可以瞬间传送呢？
     * 比如说，两个单词，邀请你通过某些替换，把其中的一个变成另外一个，每次只是替换一个字符，最少要替换几次？
     * 再比如说，连连看游戏，
     * <p>
     * BFS框架
     * int BFS(Node start,Node target) {
     * Queue<Node> q; //核心数据结构
     * Set<Node> visited; //防止走回头路
     * q.offer(start) //将起点加入队列
     * visited.add(start);
     * int step = 0; //记录扩散的步数
     * while ( q not empty) {
     * int sz = q.size();//将当前队列的所有结点向四周扩散
     * for (int i = 0;i < sz;i++) {
     * Node cur = q.poll();
     * if (cur is target) {// 判断这里是否到达终点
     * return step;
     * }
     * //将cur相邻的结点加入队列；
     * for (Node x :cur.adj()) {
     * if (x not in visited) {
     * q.offer(x);
     * visited.add(x);
     * }
     * }
     * }
     * //更新步数
     * step++;
     * }
     * }
     */


    /**
     * 起点是根结点，终点是叶子结点（叶子结点 左右结点的都是null）
     */
    @Test
    public void testMinTreeHeight() {
        TreeNode root = new TreeNode(3);
        TreeNode l = new TreeNode(9);
        TreeNode r = new TreeNode(20);
        root.left = l;
        root.right = r;

        TreeNode rl = new TreeNode(15);
        TreeNode rr = new TreeNode(7);
        r.left = rl;
        r.right = rr;

        System.out.println("---------------------");

        MinTreeHeight myBFS = new MyBFSMinTreeHeight();
        System.out.println("myBFS = " + myBFS.getMinTreeHeight(root));

        MinTreeHeight myRec = new MyRecMinTreeHeight();
        System.out.println("MyRec =" + myRec.getMinTreeHeight(root));

        MinTreeHeight myBFSV2 = new MyBFSMinTreeHeightV2();
        System.out.println("myBFSV2 = " + myBFSV2.getMinTreeHeight(root));


    }

    private static class MyBFSMinTreeHeightV2 implements MinTreeHeight {
        @Override
        public int getMinTreeHeight(TreeNode root) {
            Deque<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int depth = 1;
            TreeNode flag = root;

            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                TreeNode left = cur.left,right = cur.right;
                if (left == null && right == null) return depth;
                if (left != null) queue.add(cur.left);
                if (right != null)  queue.add(cur.right);

                if (flag == cur) {
                    depth++;
                    flag = queue.peekLast();
                }
            }
            return depth;
        }
    }


    /**
     * 使用BFS进行统计树的最小深度
     */
    @SuppressWarnings("ConstantConditions")
    private static class MyBFSMinTreeHeight implements MinTreeHeight {
        @Override
        public int getMinTreeHeight(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int depth = 1;
            while (!queue.isEmpty()) {
                int qs = queue.size();
                for (int i = 0; i < qs; i++) {
                    TreeNode cur = queue.poll();
                    TreeNode left = cur.left, right = cur.right;

                    if (left == null && right == null) {
                        return depth;
                    }
                    //cur的所有相邻结点加入
                    if (left != null) queue.offer(cur.left);
                    if (right != null) queue.offer(cur.right);
                }
                depth++;
            }
            return -1;
        }
    }

    /**
     * 递归的方式实现 最小深度
     */
    private static class MyRecMinTreeHeight implements MinTreeHeight {
        @Override
        public int getMinTreeHeight(TreeNode root) {
            if (root == null) return 0;

            int leftHeight = getMinTreeHeight(root.left);
            int rightHeight = getMinTreeHeight(root.right);

            return Math.min(leftHeight, rightHeight) + 1;
        }
    }


    /**
     * 获取树的最小高度
     */
    private interface MinTreeHeight {
        int getMinTreeHeight(TreeNode root);
    }

    private static class TreeNode {
        int data;
        TreeNode left, right;

        public TreeNode(int data) {
            this.data = data;
        }
    }




}
