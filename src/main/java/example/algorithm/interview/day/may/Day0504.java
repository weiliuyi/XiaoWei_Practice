package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName Day0504 二叉树的序列化
 * @Description https://mp.weixin.qq.com/s/DVX2A1ha4xSecEXLxW_UsA
 * @Author weiliuyi
 * @Date 2021/5/11 7:41 下午
 **/
public class Day0504 {


    /**
     * 单个：前序遍历，中序遍历，后续遍历 无法唯一确定一个二叉树
     * <p>
     * A B C 前序遍历     中序遍历                         后续遍历
     * A   A             A            B          C      C            C        C     C        C
     * B      B              B        A   C      B          B            B     B    B        A   B
     * C         C              C                A              A       A      A       A
     * <p>
     * <p>
     * <p>
     * 二叉树是一个二维平面内的结构，而序列化出来的字符串是一个现行的一维结构；
     * <p>
     * 所谓序列化：就是将结构化的数据打平，其实考察二叉树的遍历方式
     * <p>
     * <p>
     *    2
     * 1     3
     *   6
     * <p>
     * 前序遍历：2,1,#,6,#,#,3,#,#
     * 中序遍历：#,1,#,6,#,2,#,3,#
     * 后序遍历：#,#,#,6,1,#,#,3,2
     * 层级遍历：2,1,3,#,6,#,#,#,#
     *
     *
     * 反序列化的核心：：：： 找到根节点
     */

    @Test
    public void testSerialize() {
        String content = "2,1,#,6,#,#,3,#,#";

        CodecPre codecPre = new CodecPre();
        TNode root = codecPre.deserialize(content);
        printTree(root);


        String serial = codecPre.serialize(root);
        System.out.println(serial);
        System.out.println(codecPre.serializeV2(root));

        System.out.println("---------------------");

        CodecMid codecMid = new CodecMid();
        String midSerialize = codecMid.serialize(root);
        System.out.println(midSerialize);
        System.out.println(codecMid.serializeV2(root));

        System.out.println("----------------------");
        CodecAfter codecAfter = new CodecAfter();
        String afterSerial = codecAfter.serialize(root);
        System.out.println(afterSerial);
        TNode rootAf = codecAfter.deserialize(afterSerial);
        printTree(rootAf);


        System.out.println("---------------------");
        CodecLayer codecLayer = new CodecLayer();
        String layerSerial = codecLayer.serialize(root);
        System.out.println(layerSerial);
        TNode layerTree = codecLayer.deserialize(layerSerial);
        printTree(layerTree);

        System.out.println(codecLayer.serialize(layerTree));
    }


    /**
     * 层级进行序列化
     */
    private class CodecLayer {


        public String serialize (TNode root) {
            Queue<TNode> deque = new LinkedList<>();
            deque.add(root);
            StringBuilder res = new StringBuilder();

            while (!deque.isEmpty()) {
                TNode node = deque.poll();

                if (node == null) {
                    res.append("#,");
                    continue;
                }
                res.append(node.data).append(",");
                deque.add(node.left);
                deque.add(node.right);

            }

            return res.substring(0,res.length()-1);
        }


        public TNode deserialize (String data) {
            String[] arr = data.split(",");

            TNode root = new TNode(Integer.parseInt(arr[0]));
            Queue<TNode> queue = new LinkedList<>();
            queue.add(root);

            for (int i = 1; i < arr.length; i = i+2) {
                TNode node = queue.poll();
                if (!"#".equals(arr[i])) {
                    node.left = new TNode(Integer.parseInt(arr[i]));
                    queue.add(node.left);
                }

                if (i+1 < arr.length && !"#".equals(arr[i+1])) {
                    node.right = new TNode(Integer.parseInt(arr[i+1]));
                    queue.add(node.right);
                }
            }

            /*
             *  这样写是有bug的？arr[i++] 可能角标越界；
             * for (int i = 1;i < arr.length) {
             *      TNode node = queue.poll();
             *      String left = arr[i++];
             *      if (!"#".equals(left)) {
             *          node.left = new TNode (Integer.parseInt(left));
             *          queue.add(left);
             *      }
             *
             *      String right = arr[i++];
             *      if (!"#".equals(right)) {
             *          node.right = new TNode (Integer.parseInt(right));
             *          queue.add(right);
             *      }
             *
             * }
             *
             */
            return root;
        }
    }


    /**
     * 后序遍历
     */
    private class CodecAfter {

        int index = 0;

        /**
         * 序列化
         */
        private String serialize (TNode root) {
            StringBuilder res = new StringBuilder();
            serialize(root,res);
            return res.substring(0, res.length()-1);
        }


        private void serialize (TNode root,StringBuilder res) {
            if (root == null) {
                res.append("#,");
                return;
            }

            serialize(root.left,res);
            serialize(root.right,res);

            res.append(root.data).append(",");
        }

        /**
         * 反序列化
         */
        private TNode deserialize (String data) {
            String[] arr = data.split(",");
            this.index = arr.length-1;
            return buildTree(arr);
        }

        private TNode buildTree  (String[] arr) {
            if (index < 0 || "#".equals(arr[index])) return null;

            TNode root = new TNode(Integer.parseInt(arr[index]));
            index--;
            root.right = buildTree(arr);
            index--;
            root.left = buildTree(arr);

            return root;
        }
    }



    /**
     * 中序遍历序列化和反序列化
     *
     * 《《《《《中序遍历无法进行 反序列化》》》》》》》
     */
    private class CodecMid {


        int index;

        /**
         * 序列化
         */
        private String serialize (TNode root) {
            StringBuilder result = new StringBuilder();
            serialize(root,result);
            return result.substring(0,result.length()-1);
        }


        private String serializeV2 (TNode root) {
            if (root == null) return "#,";

            String left = serializeV2(root.left);
            String result =  root.data  + ",";
            String right = serializeV2(root.right);
            return left + result + right;
        }


        private StringBuilder serialize (TNode root,StringBuilder res) {
            if (root == null) return res.append("#").append(",");

             serialize(root.left,res);
             res.append(root.data).append(",");
             serialize(root.right,res);
             return res;
        }


        /**
         * 反序列化 中序遍历如何反序列化呢？？？
         *
         * 中序遍历无法实现反序列化
         * 要实现反序列化方法，首先要构造root节点，前序遍历得到的nodes列表中，第一个元素是root节点值，后续遍历得到的nodes列表中，最后一个元素是root的节点值
         * 而中序遍历，root的值被夹在两颗树的中间，也就是nodes列表的中间，我们不知道确切的索引位置，所以无法找到root节点，也就无法进行反序列化；
         *
         */
        private TNode deserialize (String data) {

            return null;
        }

    }

    /**
     * 前序遍历序列化
     */
    private class CodecPre {


        private int index;

        //递归调用获取结果方式：
        // 成员变量，
        // 方法签名添加参数，
        // 利用返回值

        private StringBuilder serialize(TNode root, StringBuilder res) {

            if (root == null) return res.append("#").append(",");

            res.append(root.data).append(",");
            serialize(root.left, res);
            serialize(root.right, res);
            return res;
        }


        private String serializeV2 (TNode root) {
            if (root == null) return "#,";

            String result =  root.data + ",";
            String  left = serializeV2(root.left);
            String right = serializeV2(root.right);
            return result + left + right;

        }


        /**
         * 序列化成字符串
         */
        private String serialize(TNode root) {
            StringBuilder res = new StringBuilder();
            serialize(root, res);
            return res.substring(0, res.length() - 1);
        }

        /**
         * 序列化成二叉树
         */
        private TNode deserialize(String data) {
            String[] eleArr = data.split(",");
            index = 0;
            return buildTree(eleArr);
        }


        private TNode buildTree(String[] arr) {
            if (arr[index].equals("#")) {
                return null;
            }

            TNode root = new TNode(Integer.parseInt(arr[index]));
            index++;
            root.left = buildTree(arr);
            index++;
            root.right = buildTree(arr);

            return root;
        }

    }


    /**
     * 打印二叉树
     */
    private void printTree (TNode root) {
        Deque<TNode> deque = new LinkedList<>();
        deque.add(root);
        TNode flag = root;
        while (!deque.isEmpty()) {
            TNode node = deque.poll();
            TNode left = node.left;
            TNode right = node.right;
            if (left != null) deque.add(left);

            if (right != null) deque.add(right);

            System.out.print("  " + node.data);
            if (flag == node) {
                System.out.println("");
                flag = deque.peekLast();
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

}
