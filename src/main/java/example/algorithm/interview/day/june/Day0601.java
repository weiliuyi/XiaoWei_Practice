package example.algorithm.interview.day.june;

import org.junit.Test;

/**
 * @ClassName Day0601 双指针的技巧
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247488584&idx=1&sn=90f7956fd9d8320fcb81aaf33c3fe7f1&chksm=9bd7ea40aca06356cdb87ba86518c50646b48b8534d42625ba454c084187400b979c8d736a61&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/2 8:04 下午
 **/
public class Day0601 {

    /**
     * 链表字串数组题，用双指针别犹豫；
     * 双指针家三兄弟，各个都是万人迷；
     * <p>
     * 快慢指针最神奇，链表操作无压力；
     * 归并排序找中点，链表成环搞判定；
     * <p>
     * 左右指针最常见，左右两端相向行；
     * 反转数组要靠它，二分搜索是弟弟；
     * <p>
     * 滑动窗口老猛男，字串问题全靠它；
     * 左右指针滑窗口，一前一后齐头金；
     * <p>
     * 双指针类型：
     * 快慢指针：
     * 左右指针：
     * 滑动窗口：
     */

    @Test
    public void testDoublePointer() {

    }


    /**
     * 检测链表中是否存在环
     */
    private boolean detectCycle(LinkedNode head) {
        LinkedNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }


    /**
     * 在循环链表中，找到循环的起点
     */
    private LinkedNode detectCycleNode (LinkedNode head) {
        LinkedNode slow = head,fast = head;
        while (fast != null && fast.next !=null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 寻找链表的中点位置
     *
     *
     * 不计算头结点：
     *  k+1
     *  2*k+1
     *
     *  两种情况：第一种 fast != null fast.next == null 此时元素的个数是奇数
     *          第二种情况： fast == null              此时元素的个数是偶数个；
     */

    LinkedNode middleNode (LinkedNode head) {
        LinkedNode slow = head,fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }


    /**
     * 删除链表中倒数第k个元素
     */
    boolean delLinkedNode (LinkedNode head,int k) {
        LinkedNode slow = head,fast = head;
        for (int i = 0; i < k; i++) { //快指针先前进k步
            if (fast.next == null)  return false;
            fast = fast.next;
        }
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;
        return true;
    }


    private static class LinkedNode {
        int data;
        LinkedNode next;
    }

}
