package example.algorithm.interview.construct.queueandstack;

import java.util.Stack;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--22 11:12
 **/
public class StackReverse {
    /**
     * 仅能使用栈和递归实现栈的反转
     */

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        reverse(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }


    /**
     * 使用递归的方法获取最后一个元素
     * @param statck 栈
     * @return 此时栈中的最后一个元素
     */
    private static int getAndRemoveLast(Stack<Integer> statck) {
        Integer result = statck.pop();
        if (statck.isEmpty()) {
            return result;
        }
        int last = getAndRemoveLast(statck);
        statck.push(result);
        return last;
    }

    /**
     * 对栈的元素进行反转
     * @param statck 目标栈
     */
    private static void reverse (Stack<Integer> statck) {
        if (statck.isEmpty()) {
            return;
        }
        int last = getAndRemoveLast(statck);
        reverse(statck);
        statck.push(last);
    }
}
