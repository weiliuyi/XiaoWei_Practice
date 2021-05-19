package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.*;

/**
 * @ClassName Day0516 二叉树的序列化 和 反序列化
 * @Description 《程序员代码面试指南》
 * @Author weiliuyi
 * @Date 2021/5/18 8:11 下午
 **/
public class Day0516 {


    /**
     * 2
     * 1     3
     * 6
     * <p>
     * 前序遍历：2,1,#,6,#,#,3,#,#
     * 中序遍历：#,1,#,6,#,2,#,3,#
     * 后序遍历：#,#,#,6,1,#,#,3,2
     * 层级遍历：2,1,3,#,6,#,#,#,#
     * <p>
     * <p>
     * 反序列化的核心：：：： 找到根节点
     */

    @Test
    public void testSerialize() {
        Codec preCodec = new PreCodec();
        String preOrder = "2,1,#,6,#,#,3,#,#";
        System.out.println(preOrder);
        TNode preRoot = preCodec.deserialize(preOrder);
        System.out.println(preCodec.serialize(preRoot));

        System.out.println("----------------");

        Codec afterCodec = new AfterCodec();
        String afterOrder = "#,#,#,6,1,#,#,3,2";
        System.out.println(afterOrder);
        TNode afterRoot = afterCodec.deserialize(afterOrder);
        System.out.println(afterCodec.serialize(afterRoot));


        System.out.println("------------------");
        String levelOrder = "2,1,3,#,6,#,#,#,#";
        System.out.println(levelOrder);
        Codec levelCodec = new LevelCodec();
        TNode levelRoot = levelCodec.deserialize(levelOrder);
        System.out.println(levelCodec.serialize(levelRoot));


    }


    /**
     * 层级序列化 和 反序列化
     */
    private static class LevelCodec implements Codec {

        @Override
        public String serialize(TNode root) {
            Deque<TNode> deque = new LinkedList<>();
            deque.add(root);
            StringBuilder res = new StringBuilder("");
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
            return res.substring(0, res.length() - 1);
        }

        @Override
        public TNode deserialize(String root) {
            String[] nodeStr = root.split(",");
            return levelDeserialize(nodeStr);
        }

        private TNode levelDeserialize(String[] nodeStr) {
            Queue<TNode> queue = new LinkedList<>();
            TNode root = new TNode(Integer.parseInt(nodeStr[0]));
            queue.add(root);
            // nodeStr.length - 1   nodeStr.length -2  nodeStr.length - 3
            for (int i = 1; i < nodeStr.length; i = i + 2) {
                TNode node = queue.poll();
                if (!"#".equals(nodeStr[i])) {
                    node.left = new TNode(Integer.parseInt(nodeStr[i]));
                    queue.add(node.left);
                }

                if (i + 1 < nodeStr.length && !"#".equals(nodeStr[i + 1])) {
                    node.right = new TNode(Integer.parseInt(nodeStr[i + 1]));
                    queue.add(node.right);
                }

            }
            return root;
        }
    }


    /**
     * 后续遍历进行序列化 和 反序列化
     */
    private static class AfterCodec implements Codec {

        @Override
        public String serialize(TNode root) {
            String res = afterSerialize(root);
            return res.substring(0, res.length() - 1);
        }


        private String afterSerialize(TNode root) {
            if (root == null) return "#,";

            String left = afterSerialize(root.left);
            String right = afterSerialize(root.right);

            return left + right + root.data + ",";

        }

        private int afterIndex;

        @Override
        public TNode deserialize(String root) {
            String[] content = root.split(",");
            afterIndex = content.length - 1;
            return afterDeserialize(content);
        }

        private TNode afterDeserialize(String[] content) {
            if (afterIndex < 0) return null;


            if ("#".equals(content[afterIndex])) return null;

            TNode root = new TNode(Integer.parseInt(content[afterIndex]));

            afterIndex--;
            root.right = afterDeserialize(content);
            afterIndex--;
            root.left = afterDeserialize(content);

            return root;
        }

    }


    /**
     * 前序遍历序列化
     */

    private static class PreCodec implements Codec {


        @Override
        public String serialize(TNode root) {
            //StringBuilder res = new StringBuilder();
            //preOrderSerialize(root, res);
            String res =  preOrderSerialize(root);
            return res.substring(0, res.length() - 1);
        }

        /**
         * 使用方法签名参数 来获取结果
         */
        private void preOrderSerialize(TNode root, StringBuilder res) {
            if (root == null) {
                res.append("#,");
                return;
            }

            res.append(root.data).append(",");

            preOrderSerialize(root.left, res);
            preOrderSerialize(root.right, res);
        }


        /**
         * 使用返回值 获取结果
         */
        private String preOrderSerialize(TNode root) {
            if (root == null) return "#,";

            String preContent = root.data + ",";

            preContent += preOrderSerialize(root.left);
            preContent += preOrderSerialize(root.right);

            return preContent;
        }


        /**
         * 两种方式反序列化
         */
        @Override
        public TNode deserialize(String root) {
            String[] content = root.split(",");
//            preIndex = 0;
//            return preOrderDeserialize(content);
            Queue<String> queue = new LinkedList<>();
            Arrays.stream(content).forEach(str -> queue.add(str));
            return preOrderDeserialize(queue);

        }

        private int preIndex = 0;

        /**
         * 方式一  使用成员变量
         */
        private TNode preOrderDeserialize(String[] content) {
            if (preIndex >= content.length) return null;

            if ("#".equals(content[preIndex])) {
                return null;
            }
            TNode node = new TNode(Integer.parseInt(content[preIndex]));
            preIndex++;
            node.left = preOrderDeserialize(content);
            preIndex++;
            node.right = preOrderDeserialize(content);
            return node;
        }

        /**
         * 方式二：使用队列的数据结构
         */
        private TNode preOrderDeserialize (Queue<String> queue) {
            String str = queue.poll();
            if ("#".equals(str)) {
                return null;
            }
            TNode node = new TNode(Integer.parseInt(str));
            node.left = preOrderDeserialize(queue);
            node.right = preOrderDeserialize(queue);
            return node;
        }
    }


    /**
     * 序列化的接口
     */
    private interface Codec {
        /**
         * 序列化
         */
        String serialize(TNode root);

        /**
         * 反序列化
         */
        TNode deserialize(String root);
    }

    private static class TNode {
        TNode left, right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }
}
