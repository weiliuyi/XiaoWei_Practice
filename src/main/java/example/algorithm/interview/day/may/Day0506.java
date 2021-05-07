package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Random;

/**
 * @ClassName Day0506 跳表
 * @Description https://zhuanlan.zhihu.com/p/200815425
 * @Author weiliuyi
 * @Date 2021/5/6 10:18 上午
 **/
public class Day0506 {

    /**
     * 跳表主要是解决有序链表的快速查找
     */
    @Test
    public void testSkipList() {

        SkipList list = new SkipList();
        list.insert(50);
        list.insert(15);
        list.insert(13);
        list.insert(20);
        list.insert(100);
        list.insert(75);
        list.insert(99);
        list.insert(76);
        list.insert(83);
        list.insert(65);
        list.insert(51);
        list.printList();
        System.out.println(list.search(15));

        list.del(15);
        list.printList();
        System.out.println(list.search(15));
    }


    private class SkipList {

        private SNode head = null;
        private SNode tail = null;

        private int topNum = 0; // 层数

        public SkipList() {
            this.head = new SNode(Integer.MIN_VALUE);
            this.tail = new SNode(Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
        }

        /**
         * 插入一个元素
         */
        public void insert(int num) {
            SNode topHead = nextNode(num);

            //topHead前插入
            SNode numNode = new SNode(num);
            numNode.next = topHead;
            numNode.pre = topHead.pre;
            topHead.pre.next = numNode;
            topHead.pre = numNode;


            //建立结点索引
            Random random = new Random();
            while (random.nextDouble() < 0.5) {
                //上一层增加一个结点
                while (topHead.next != null && topHead.up == null) { //找到右侧第一个topHead.up != null的位置；
                    topHead = topHead.next;
                }
                // 三种情况
                // topHead.next == null && topHead.up == null     topHead.next == null && topHead.up != null  topHead.next != null &&  topHead.up != null
                if (topHead.next == null && topHead.up == null) { //到达末尾还没有找到上层
                    //新建一层
                    topNum++;
                    SNode minNode = new SNode(Integer.MIN_VALUE);
                    SNode maxNode = new SNode(Integer.MAX_VALUE);

                    //前后指针
                    minNode.next = maxNode;
                    maxNode.pre = minNode;

                    //上下指针
                    minNode.down = head;
                    head.up = minNode;
                    maxNode.down = topHead;
                    topHead.up = maxNode;

                    //修改指针
                    head = minNode;
                    tail = maxNode;

                }

                topHead = topHead.up;

                //将结点插入到topHead之前
                SNode indexNode = new SNode(num);
                //修改上下指针
                indexNode.down = numNode;
                numNode.up = indexNode;

                indexNode.next = topHead;
                indexNode.pre = topHead.pre;
                topHead.pre.next = indexNode;
                topHead.pre = indexNode;

                numNode = indexNode;

            }

        }


        /**
         * 删除一个元素
         */
        public void del(int num) {
            //寻找
            SNode targetNode = search(num);
            if (targetNode != null) {
                //删除结点
                targetNode.pre.next = targetNode.next;
                targetNode.next.pre = targetNode.pre;

                //删除层级索引
                delLevel(targetNode);
            }

        }


        /**
         * 查询一个元素
         */
        public SNode search(int num) {
            //寻找层数定定点
            SNode topHead = nextNode(num);

            while (topHead != null) {
                if (topHead.data == num) {
                    //System.out.println(topHead);
                    return topHead;
                }
                if (topHead.data < num) break;

                topHead = topHead.pre;
            }

            return null;
        }


        /**
         * 获得比num大的第一个node;
         */
        private SNode nextNode(int num) {
            SNode tempNode = head;

            while (true) {
                while (tempNode.data < num) {
                    tempNode = tempNode.next;
                }
                if (tempNode.pre.down == null) break;

                tempNode = tempNode.pre.down;
            }
            return tempNode;
        }

        private void delLevel (SNode node) {
            while (node.up != null) {
                node = node.up;

                SNode preNode = node.pre;
                SNode nextNode = node.next;

                //正常删除
                preNode.next = nextNode;
                nextNode.pre = preNode;

                //只剩下两边的结点，删除把这一层删除
                if (Integer.MIN_VALUE == preNode.data && Integer.MAX_VALUE == nextNode.data) {
                    //删除整层
                    if (preNode.up != null) {// 如果不是顶层
                        preNode.up.down = preNode.down;
                        nextNode.up.down = nextNode.down;
                    } else {
                        head = preNode.down;
                        tail = nextNode.down;
                    }
                    preNode.down.up = preNode.up; //如果是顶层
                    nextNode.down.up = nextNode.up;
                    topNum--;
                }
            }

        }

        /**
         * 根据层数超着最层级的结点；
         */
//        private SNode topNode() {
//            SNode topHead = head;
//            int topCount = topNum;
//            while (topCount > 0) {
//                topHead = topHead.up;
//                topCount--;
//            }
//            return topHead;
//        }
        private void printList() {
            SNode tempHead = head;
            while (tempHead != null) {
                printLevel(tempHead);
                tempHead = tempHead.down;
            }

        }

        private void printLevel(SNode head) {
            while (head != null) {
                System.out.printf(head.data + " ");
                head = head.next;
            }
            System.out.println("");
        }
    }

    /**
     * 跳表的结点信息
     */
    private static class SNode {

        // 前后 上下指针
        private SNode pre, next, down, up;

        private int data;

        private long millis = System.currentTimeMillis();

        public SNode(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "SNode{" +
                    "data=" + data +
                    ", millis=" + millis +
                    '}';
        }
    }

}


