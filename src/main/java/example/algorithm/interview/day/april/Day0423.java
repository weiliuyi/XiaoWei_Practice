package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName Day0423 手写LRU缓存淘汰策略
 * @Description https://mp.weixin.qq.com/s/RQSmeQYnVrX4ILfNh1PVkg
 * @Author weiliuyi
 * @Date 2021/4/22 7:29 下午
 **/
public class Day0423 {

    /**
     * 手写LRU（Least Recently Used）算法 最近最久未使用
     * FIFO（First In First Out）  先进先出置换算法 类似与队列
     * OPT(Optimal ) 最佳页面置换算法
     * NRU Clock置换算法（时钟置换算法）
     * LFU 最少使用置换算法
     * PBA 页面缓冲算法
     * <p>
     *  计算机的局部性原理
     *  空间局部性： 被访问的存储单元附近的位置，在之后很有可能被访问
     *  时间局部性：被访问的存储单元，在之后很有可能被再次访问
     */

    @Test
    public void testLRU() {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);

        System.out.println("get(1) " + cache.get(1));

        cache.put(3, 3);
        System.out.println("get(2) " + cache.get(2));

        cache.put(1, 4);

        System.out.println("get(1) = " + cache.get(1));

        cache.put(5, 7);

        System.out.println("get(3) = " + cache.get(3));
    }


    /**
     * 通过复写LinkedHashMap实现LRU算法
     */
    @Test
    public void testLRULinkedHashMap () {
        MyLRUCache<Integer,Integer> cache = new MyLRUCache<>(2);

        cache.put(1, 1);
        cache.put(2, 2); // (1,1),(2,2)

        //(2,2) (1,1)
        System.out.println("get(1) " + cache.get(1));

        cache.put(3, 3); //(1,1),(3,3)
        System.out.println("get(2) " + cache.get(2));

        cache.put(1, 4);//(3,3),(1,4)

        System.out.println("get(1) = " + cache.get(1));

        cache.put(5, 7);//(1,4),(5,7)

        System.out.println("get(3) = " + cache.get(3));
    }


    /**
     * LRU算法的特点（有顺序之分(时序)）：
     * 1。查找快（hash表 查询快 但是数据没有固定顺序）
     * 2。插入快 (LinkedList 链表有顺序之分，插入删除快，但是查询慢)
     * <p>
     * 综合一下：形成一个新的哈希链表 ---双向链表和哈希表的结合体
     * 思想：就是通过哈希表赋予链表快速查找的能力；
     */

    private static class LRUCache {

        /**
         * 刚刚访问过的结点放在头部，
         * 越靠近尾结点，越代表越长时间没有被访问；
         */
        private final DLinkedList dLinkedList;

        private final Map<Integer, Node> map;

        /**
         * 缓存的阈值
         */
        private final int threshold;

        public LRUCache(int threshold) {
            this.threshold = threshold;
            dLinkedList = new DLinkedList();
            map = new java.util.HashMap<>();
        }


        /**
         * 设置缓存
         * 1。如果关键字已经存在，则变更其数据值；
         * 2。如果关键字不存在，则插入该组【关键字-值】；
         * 3。如果缓存容量达到上限，应该写入新的数据删除最久未使用的数据值；为新的数据值流出空间；
         */
        public void put(int key, int value) {
            //如果此时已经存在，进行更新
            Node temp = map.get(key);
            if (temp != null) {
                temp.val = value;
                dLinkedList.remove(temp);
                dLinkedList.addFirst(temp);
                return;
            }

            //如果此时一定达到阈值，进行淘汰缓存
            if (dLinkedList.getSize() >= threshold) {
                Node delNode = dLinkedList.removeLast();
                if (delNode != null) map.remove(delNode.key);
            }

            Node node = new Node(key, value);

            map.put(key, node);
            dLinkedList.addFirst(node);

        }


        public int get(int key) {
            Node node = map.get(key);
            if (node == null) return -1;

            dLinkedList.remove(node);
            dLinkedList.addFirst(node);

            return node.val;

        }

    }


    /**
     * 双向链表结点
     */
    private static class Node {
        int key, val;
        Node pre, next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }


    /**
     * 双向链表
     */
    private static class DLinkedList {

        private final Node head;
        private int size;

        public DLinkedList() {
            head = new Node(0, 0);
            head.next = head;
            head.pre = head;
        }

        /**
         * 双向链表头增加一个结点
         */
        public void addFirst(Node node) {
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            size++;
        }

        /**
         * 删除一个结点
         *
         * @param node 待删除的结点；
         */
        public void remove(Node node) {
            Node preNode = node.pre;
            Node nextNode = node.next;

            preNode.next = nextNode;
            nextNode.pre = preNode;

            size--;
        }


        /**
         * 删除最后一个结点
         */
        public Node removeLast() {
            Node lastNode = head.pre;
            if (lastNode == head) return null;

            Node newEnd = lastNode.pre;

            newEnd.next = head;
            head.pre = newEnd;

            lastNode.next = null;//gc释放资源
            lastNode.pre = null;

            size--;

            return lastNode;

        }

        public int getSize() {
            return size;
        }
    }

    /**
     *
     * 双向链表的约定：越靠近双向链表头部，元素越老，越靠近尾部元素越年轻；
     *
     * LinkedHashMap是HashMap的子类，但是内部还有一个双向链表维护者键值对的顺序；
     * 每一个键值对既位于哈希表中，也位于双线链表中，
     * LinkedHashMap支持两种顺序，第一种是插入顺序，第二种是访问顺序
     * 插入顺序：先添加的元素在前面，后添加的在后面，修改和访问元素不改变元素在链表中的数据；
     * 访问顺序：访问===》指的是put/get操作，对一个key执行put/get操作之后，需要把这个结点转移到链表末尾。
     */

    private static class MyLRUCache<K,V> extends LinkedHashMap<K,V>  {

        private final int capacity;


        public MyLRUCache (int capacity) {
            super(16,0.75f,true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }
}
