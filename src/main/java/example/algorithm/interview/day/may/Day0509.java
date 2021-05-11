package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.*;

/**
 * @ClassName Day0509 寻找重复的子树
 * @Description https://mp.weixin.qq.com/s/LJbpo49qppIeRs-FbgjsSQ
 * @Author weiliuyi
 * @Date 2021/5/11 6:24 下午
 **/
public class Day0509 {


    /**
     * 给定一颗二叉树，返回所有重复的子树，
     * <p>
     * 两颗树重复是指 它们具有相同的结构以及相同的结点值
     * <p>
     * 对于一个结点应该做什么？
     * 如果你想知道以自己为根的子树是不是重复的，是否应该加入到结果列表中，你需要知道什么信息？
     * <p>
     * 需要知道两点：
     * 1。以我为根的这棵树长成什么样子？ 后续遍历，知道以自己为根的子树长啥样，是不是先知道我的左右子树长啥样，再加上自己，就构成了整颗子树的样子？
     * 怎么描述二叉树的模样呢？
     * 二叉树的前序/中序/后序遍历结果 可以描述二叉树的结构；
     * <p>
     * 2。其他结点为根的子树长成什么样子？
     */

    @Test
    public void testFindSubTree() {

    }


    /**
     * 结果的缺点：
     * 如果相同的子树存在多棵，那么结果集合中就会出现重复
     */
    private class DupSubTree {

        Set<String> memo = new HashSet<>();
        List<TNode> res = new ArrayList<>();

        public DupSubTree(TNode root) {
            findDupSubTree(root);
        }

        private String findDupSubTree(TNode root) {
            if (root == null) return "#";

            String left = findDupSubTree(root.left);
            String right = findDupSubTree(root.right);

            String subTree = left + "," + right + "," + root.data;

            if (memo.contains(subTree)) {
                res.add(root);
            } else {
                memo.add(subTree);
            }
            return subTree;
        }

        private List<TNode> getDupSubTree() {
            return res;
        }
    }


    /**
     * 对版本一的问题，进行优化；
     */
    private class DupSubTreeV2 {

        List<TNode> res = new ArrayList<>();
        Map<String, Integer> memo = new HashMap<>();

        public DupSubTreeV2(TNode root) {
            findDupSubTree(root);
        }

        private String findDupSubTree(TNode root) {
            if (root == null) return "#";

            String left = findDupSubTree(root.left);
            String right = findDupSubTree(root.right);

            String subTree = left + "," + right + "," + root.data;

            Integer fre = memo.getOrDefault(subTree, 0);
            if (fre == 1) {
                res.add(root);
            }
            memo.put(subTree, fre + 1);
            return subTree;
        }

        private List<TNode> getDupSubTree() {
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
