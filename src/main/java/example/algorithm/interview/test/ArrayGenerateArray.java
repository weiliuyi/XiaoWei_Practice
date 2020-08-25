package example.algorithm.interview.test;



/**
 * @description: 根据后序遍历的排序二叉树数组，生成二叉树；
 * @author: weiliuyi
 * @create: 2020--25 15:58
 **/
public class ArrayGenerateArray {


    /**
     * 输的节点对象
     */
    private static class TreeNode {
        TreeNode left;
        TreeNode right;
        int data;

        TreeNode(int data) {
            this.data = data;
        }
    }

    /**
     *       5
     *    3     8
     *  1   4  7   9
     *
     * 1 4 3 7 9 8 5  后续便利的数组；
     * */

    public static void main(String[] args) {
        int [] array = new int[]{1,4,3,7,9,8,5};
        TreeNode bTree = generateBTree(array, 0, array.length - 1);
        afterOrder(bTree);
    }


    /**
     * 更具后续遍历的有序数组 生成二叉树
     * @param array  后续便利的有序数组
     * @param lIndex 起始index
     * @param rIndex 结束index
     * @return 返回创建的二叉树
     */
    private static TreeNode generateBTree(int[] array, int lIndex, int rIndex) {
        if (lIndex > rIndex) {
            return null;
        }
        TreeNode rootNode = new TreeNode(array[rIndex]);
        int m = lIndex - 1;  //这个地方是技巧  m = lIndex -1;可以使得代码更加简洁
        //当只有左子树的时候 循环结束的话，m = rIndex -1;  右子树 (array,rindex,rIndex-1) 返回null
        //只有右子树的时候，循环结束的话 m = lIndex -1  左子树：(array,lIndex,lIndex-1;
        //最笨拙的方法是：if (m == lIndex -1){ //只有右子树 左子树为null}
        // else if (m == rIndex -1) {//只有左子树，右子树为null}
        // else {//既有左子树又有右子树}
        for (int i = lIndex;i < rIndex;i++) { //找到左右子树的分界点，特殊情况，只有左子树，只有右子树
            if (array[i] < array[rIndex]) {
                m = i;
            }
        }
        rootNode.left = generateBTree(array,lIndex,m);
        rootNode.right = generateBTree(array,m+1,rIndex-1);
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
