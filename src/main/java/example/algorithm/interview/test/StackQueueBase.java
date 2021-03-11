package example.algorithm.interview.test;

import java.util.Arrays;

/**
 * @description: 使用数组和链表实现栈和队列
 * @author: weiliuyi
 * @create: 2020--11 11:31
 *
 **/
public class StackQueueBase {


    public static void main(String[] args) {
        System.out.println(Arrays.toString(getLeftNearestBigNum(new int[]{3,2,1,4,5,6,3,2})));
        System.out.println(Arrays.toString(getLeftNearestLowNum(new int[]{3,2,1,4,5,6,3,2})));
    }


    /**
     * 统计数组中左侧比它大的数值
     * @param numArray
     * @return
     */
    public static int[] getLeftNearestBigNum (int[] numArray) {
        int len = numArray.length;
        int[] bigArray = new int[len];
        Stack stack = new Stack(len);
        for (int i = 0 ;i < len;i++) {
            while (!stack.isEmpty()&& stack.peek() <= numArray[i] ) {
                stack.poll();
            }
            if (stack.isEmpty()) {
                bigArray[i] = -1;
            } else {
                bigArray[i] = stack.peek();
            }
            stack.push (numArray[i]);
        }
        return bigArray;
    }

    /**
     * 统计数组中左侧比它小的数值
     * @param numArray 目标数组
     */
    public static int[] getLeftNearestLowNum (int[] numArray) {
        int len = numArray.length;
        int[] lowArray = new int[len];
        Stack stack = new Stack(len);
        for (int i = 0;i < len;i++) {
            while (!stack.isEmpty() && stack.peek() >= numArray[i]) {
                stack.poll();
            }
            if (stack.isEmpty()) {
                lowArray[i] = -1;
            } else {
                lowArray[i] = stack.peek();
            }
            stack.push(numArray[i]);
        }
        return lowArray;
    }

}


/**
 * 双向链表的节点
 *
 */
class LinkNode {
    private int num;
    LinkNode last;
    LinkNode next;
    public LinkNode (int num) {
        this.num = num;
    }
}

class MyLinkQueue {
    private LinkNode head;
    private LinkNode tail;


}



class Stack {
    private int[] stack;
    private int top = -1;//指向栈顶元素
     Stack(int len) {
        stack = new int[len];
    }

    int poll () {
        return stack[top--];
    }

    void push (int num) {
         stack[++top] = num;
    }

    int peek () {
        return stack[top];
    }

    boolean isEmpty () {
        return top == -1;
    }

}