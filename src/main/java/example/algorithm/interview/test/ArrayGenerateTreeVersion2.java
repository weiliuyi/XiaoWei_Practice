package example.algorithm.interview.test;

/**
 * @description: 根据前序遍历和后续遍历数组，生二叉树；
 * @author: weiliuyi
 * @create: 2020--25 17:37
 **/
public class ArrayGenerateTreeVersion2 {

    /**
     * 二叉树的节点
     */
    private static class TreeNode {
        TreeNode left;
        TreeNode right;
        char data;

        public TreeNode(char data) {
            this.data = data;
        }
    }

    /**
     * A
     * B      C
     * D   E  F   G
     * 前序遍历：A B D E C F G
     * 中序遍历：D B E A F C G
     * 后序便利 ：D E B F G C A
     */

    public static void main(String[] args) {
        char[] preOrder = new char[]{'A', 'B', 'D', 'E', 'C', 'F', 'G'}; //前序
        char[] inOrder = new char[]{'D', 'B', 'E', 'A', 'F', 'C', 'G'}; // 中序
        TreeNode tree = generateBtree(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
        afterOrder(tree);//后序遍历输出结果
    }

    /**
     * 根据前序遍历 后序遍历生成二叉树
     * @param preArray 前序数组下标
     * @param preLIndex 前序开始下标
     * @param preRIndex 前序结束下标
     * @param inArray 中序遍历下标
     * @param inLIndex 中序开始下标
     * @param inRIndex 中序结束下标
     * @return 生成二叉树
     */
    private static TreeNode generateBtree(char[] preArray, int preLIndex, int preRIndex, char[] inArray, int inLIndex, int inRIndex) {
        if (preLIndex > preRIndex || inLIndex > inRIndex) {
            return null;
        }
        TreeNode rootNode = new TreeNode(preArray[preLIndex]);
        int mid = 0;
        for (int i = inLIndex; i <= inRIndex; i++) {
            if (inArray[i] == preArray[preLIndex]) {
                mid = i;
                break;
            }
        }
        int size = mid - inLIndex;
        rootNode.left = generateBtree(preArray, preLIndex + 1, preLIndex + size, inArray, inLIndex, mid - 1);
        rootNode.right = generateBtree(preArray, preLIndex + size + 1, preRIndex, inArray, mid + 1, inRIndex);
        return rootNode;
    }


    /**
     * 后续遍历打印二叉树
     *
     * @param bn 二叉树
     */
    private static void afterOrder(TreeNode bn) {
        if (bn != null) {
            afterOrder(bn.left);
            afterOrder(bn.right);
            System.out.println(bn.data);
        }
    }
}


