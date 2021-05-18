package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0515 打印二叉树的造型
 * @Description 《程序员代码main是指南》
 * @Author weiliuyi
 * @Date 2021/5/17 8:29 下午
 **/
public class Day0515 {

    /**
     *
     * * 4
     * * 2    7
     * * 1  3  6  9
     * * <p>
     * * 前序遍历：4，2，1，3，7，6，9
     * * 中序遍历：1，2，3，4，6，7，9
     * * 后序遍历：1，3，2，6，9，7，4
     */

    @Test
    public void testPrintTreePic () {
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


        printTree(root);
    }


    @Test
    public void testPrintTreePicV2 () {
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

        printTree(root);
    }

    /**
     *
     */

    private void printTree (TNode root) {
        System.out.println("printTree ");
        printTree(root,0,"H");
        System.out.println("");
    }


    private void printTree (TNode root,int height,String ch) {

        if (root == null) return ;


        printTree(root.right,height+1,"^");
        String col = getSpace(height * 17,"-");
        String content = ch + root.data + ch;
        int lengthL = (17 - content.length()) / 2;
        int lengthR = 17 - lengthL - content.length();
        System.out.println(col + getSpace(lengthL," ") + content + getSpace(lengthR," "));
        //System.out.println(col + content);
        printTree(root.left,height+1,"v");
    }




    private String getSpace (int num,String space) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < num; i++) {
            res.append(space);
        }
        return res.toString();
    }



    private class TNode {
        TNode left,right;
        int data;

        public TNode(int data) {
            this.data = data;
        }
    }


}
