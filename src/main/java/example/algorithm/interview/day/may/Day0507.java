package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.Random;

/**
 * @ClassName Day0507 跳表
 * @Description
 * @Author weiliuyi
 * @Date 2021/5/7 10:21 上午
 **/
public class Day0507 {

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


    private static class SkipList {
        private SNode head, tail;

        private int levelCount;

        //初始化一个空的SkipList;
        public SkipList() {
            this.head = new SNode(Integer.MIN_VALUE);
            this.tail = new SNode(Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
            this.levelCount = 0;
        }

        // 插入指定元素
        public void insert(int num) {
            SNode tempNode = findNode(num);
            SNode node = new SNode(num);
            //插入结点
            appendNode(tempNode,node);

            int currentLevel = 0;
            //建立层级索引
            Random random  = new Random();
            while (random.nextBoolean()) {

                //判断增加层级
                if (currentLevel == levelCount) {
                    addLevel();
                }

                while (tempNode.up == null) tempNode = tempNode.pre;

                tempNode = tempNode.up;

                SNode indexNode = new SNode(num);
                //前后指针
                appendNode(tempNode,indexNode);
                //上下指针
                indexNode.down = node;
                node.up = indexNode;

                node = indexNode;

                currentLevel++;
            }


        }

        private void addLevel() {
            levelCount++;
            SNode minNode = new SNode(Integer.MIN_VALUE);
            SNode maxNode = new SNode(Integer.MAX_VALUE);

            minNode.next = maxNode;
            maxNode.pre = minNode;

            minNode.down = head;
            head.up = minNode;

            maxNode.down = tail;
            tail.up = maxNode;

            head = minNode;
            tail = maxNode;

        }

        // 删除指定元素
        public void del(int num) {
            SNode tempNode = findNode(num);

            if (tempNode.data != num)  return;

            //从链表删除此结点
            delSNode(tempNode);
            //删除索引层
            while (tempNode.up != null) {
                tempNode = tempNode.up;

                delSNode(tempNode);
                SNode preNode = tempNode.pre;
                SNode nextNode = tempNode.next;
                if (preNode.data != Integer.MIN_VALUE || nextNode.data != Integer.MAX_VALUE)  continue;

                // preNode.data == min && nextNode.data == max
                if (preNode.up == null) { // 说明是顶层
                    head = preNode.down;
                    tail = nextNode.down;

                    preNode.down.up = null;
                    nextNode.down.up = null;
                } else {
                    preNode.up.down = preNode.down;
                    nextNode.up.down = nextNode.down;

                    preNode.down.up = preNode.up;
                    nextNode.down.up = preNode.up;
                }
                levelCount--;

            }

        }

        // 查找指定元素
        public SNode search(int num) {
            SNode node = findNode(num);
            if (node.data == num) return node;
            return null;

        }

        //在cur结点后面插入ins结点
        private void appendNode(SNode cur, SNode ins) {
            ins.next = cur.next;
            ins.pre = cur;
            cur.next.pre = ins;
            cur.next = ins;
        }


        //查找指定结点
        private SNode findNode(int num) {

            SNode tempNode = head;
            while (true) {
                while (tempNode.next.data <= num) {
                    tempNode = tempNode.next;
                }
                if (tempNode.down == null) break;

                tempNode = tempNode.next;
            }
            return tempNode;
        }


        // 从链表中删除tempNode
        private void delSNode (SNode tempNode) {
            //在链表中删除这个数据元素
            tempNode.pre.next = tempNode.next;
            tempNode.next.pre = tempNode.pre;
        }


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


    private static class SNode {

        SNode pre, next, up, down;
        int data;
        long millis = System.currentTimeMillis();

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
