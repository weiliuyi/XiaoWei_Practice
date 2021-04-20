package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0420 双指针的技巧汇总
 * @Description https://mp.weixin.qq.com/s/yLc7-CZdti8gEMGWhd0JTg
 * @Author weiliuyi
 * @Date 2021/4/20 10:28 上午
 **/
public class Day0420 {


    /*
     * 双指针可以分为两类，
     * 第一类：快慢指针 前者解决链表中的问题 典型问题是链表中是否含有环
     * 第二类：左右指针 后者解决数组中的问题 比如二分查找
     */
    @Test
    public void testSpeedPointer() {
        LNode<String> head = new LNode<>();
        LNode<String> first = new LNode<>();
        LNode<String> second = new LNode<>();
        LNode<String> third = new LNode<>();
        LNode<String> four = new LNode<>();

        head.next = first;
        first.next = second;
        second.next = third;
        third.next = four;

        //是否有环
        System.out.println(hasCycle(head));

        //中间结点
        System.out.println(midNode(head));

        //倒数第K个结点
        System.out.println(reverseOrderK(head, 2));

        four.next = second;
        System.out.println(hasCycle(head));
        System.out.println(second);
        //环开始的地方
        System.out.println(detectCycle(head));
    }


    /**
     * 左右指针，应用场景有：
     * 1。二分查找
     * 2。两数之和
     * 3。反转数组
     * 4。滑动窗口算法
     */
    @Test
    public void testLeftRightPointer() {
        int[] arr = new int[]{2, 7, 11, 15};
        System.out.println(binarySearch(arr, 11));

        System.out.println(Arrays.toString(twoSum(arr, 18)));

        System.out.println(Arrays.toString(reverseArr(arr)));
    }


    /**
     * 判断单链表中是否含有环
     * 思路：使用快慢指针进行，慢指针，一次一步 快指针一次两步
     * 如果存在环的话：快指针一定可以追上慢指针
     * 如果不存在环的话，快指针指向空而结束；
     */

    private <T> boolean hasCycle(LNode<T> lNode) {
        LNode<T> fast, slow;
        fast = slow = lNode.next;
        while (fast != null && fast.next != null) {
            slow = slow.next; //一次一步
            fast = fast.next.next; //一次两部

            if (slow == fast) return true;
        }
        return false;
    }


    /**
     * 已知这个链表中含有环，那么返回这个环的起始位置
     */

    private <T> LNode<T> detectCycle(LNode<T> head) {
        LNode<T> fast, slow;
        fast = slow = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        if (fast == null) return null;

        slow = head.next;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 寻找链表中的中间节点
     * 当链表的长度是奇数时，slow恰巧停在中间位置，
     * 如果链表长度是偶数时，slow恰巧停在中间偏右；
     */
    private <T> LNode<T> midNode(LNode<T> head) {
        LNode<T> fast, slow;
        fast = slow = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     * 寻找链表倒数第K个元素
     * 思路：先让快指针先走K步，然后快慢指针同速前进，
     * 这样当快指针走到链表末尾的时候，慢指针所在的位置就是倒数第K个链表节点
     * 假设k不会超过链表的长度；
     */
    private <T> LNode<T> reverseOrderK(LNode<T> head, int k) {
        LNode<T> fast, slow;
        fast = slow = head.next;

        //--k > 0  2 ----- k 共有k-1个步骤
        //k-- > 0  1 .....k 共有个k步骤
        while (k-- > 0) {
            fast = fast.next;
        }
        //fast领先slow k个结点；
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 二分查找
     */
    private int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            }
        }
        return 0;
    }

    /**
     * 给定已按照 升序排列 的有序数组，找到两个数使得他们相加之和等于目标数；
     * 有序数组的性质：首尾的元素之和
     */
    private int[] twoSum(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }
        return new int[]{-1, -1};
    }


    /**
     * 反转数组
     */
    private int[] reverseArr(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
        return arr;
    }

    private static class LNode<T> {
        LNode<T> next;
        T data;
    }


}
