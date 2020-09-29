package example.algorithm.interview.construct.queueandstack;

import org.junit.Test;

import java.util.Stack;

/**
 * @description: 使用栈实现队列的功能
 * @author: weiliuyi
 * @create: 2020--29 20:03
 **/
public class Stack2Queue {
        @Test
        public void test1 () {
            StackQueue stackQueue = new StackQueue();
            stackQueue.add(2);
            stackQueue.add(3);
            stackQueue.add(4);
            stackQueue.add(1);
            System.out.println(stackQueue.poll());
            System.out.println(stackQueue.poll());
            System.out.println(stackQueue.poll());
            System.out.println(stackQueue.poll());
        }
}

/**
 * 队列的功能 add poll peek
 */
class StackQueue {
    // 基本思想，add时，实现压入firstStack,  注意：必须将firstStack一次性导入到secondStack;
    private Stack<Integer> firstStack; //入栈
    //出栈时，如果secondStack为null，那么必须把firstStack全部放入到第二个栈，
    private Stack<Integer> secondStack; //出栈

    public StackQueue() {
        this.firstStack = new Stack<>();
        this.secondStack = new Stack<>();
    }

    void add(Integer e) {
        firstStack.push(e);
    }

    Integer poll() {
        if (secondStack.isEmpty() && !firstStack.isEmpty()) {
            //secondStack为empty,且firstStack不为Null,需要将secondStack都压入firstStack中去；
            do {
                secondStack.push(firstStack.pop());
            } while (!firstStack.isEmpty());
        }
        //第一个为null 第二个也为null的情况
        return secondStack.isEmpty() ? null : secondStack.pop();
    }

    Integer peek() {
        if (secondStack.isEmpty() && !firstStack.isEmpty()) {
            do {
                secondStack.push(firstStack.pop());
            } while (!firstStack.isEmpty());
        }
        return secondStack.isEmpty() ? null : secondStack.peek();
    }

}