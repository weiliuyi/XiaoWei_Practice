package example.algorithm.interview.construct.queueandstack;

import org.junit.Test;

import java.util.Objects;
import java.util.Stack;

/**
 * @description: 栈相关的算法题
 * @author: weiliuyi
 * @create: 2020--29 19:25
 **/

public class GetMinStack {
    /**
     *  设计一个getMin功能的栈
     * 实现各特殊功能的栈，在满足栈的基本功能的基础上，返回最小值
     * 要求： pop poll,getMin时间复杂度都是O(1)
     * 可以使用现有的栈结构
     */
    @Test
    public void test1() {
        MinStack minstack = new MinStack();
        minstack.push(2);
        System.out.println(minstack.getMin());
        System.out.println("pop "  + minstack.pop());
        minstack.push(3);
        System.out.println(minstack.getMin());
        minstack.push(20);
        System.out.println(minstack.getMin());
        minstack.push(1);
        System.out.println(minstack.getMin());
    }

}

/**
 * 获得最小值的栈(特殊的栈)
 * 还有第二种方式：
 * 入栈的时候：如果发现元素大于或者等于minStack时，此时需要把minStack的栈顶重新压栈
 *
 */

class MinStack {
    private Stack<Integer> dataStack;//数据栈
    private Stack<Integer> minStack;//栈顶为当前数据栈的最小值

    MinStack() {
        this.dataStack = new Stack<>();//数据栈
        this.minStack = new Stack<>(); //最小值栈
    }

    /**
     * 压栈的时候，如果当前元素小于等于minStack的栈顶元素，那么此时需要把此元素也要压入minStack
     * @param t 压入的元素
     *
     *          问题：为什么等于的也要放入到小栈中去？？
     *          这和出栈的逻辑有关系，出栈的时候，数据栈中的元素 等与 最小栈的时候，最小栈中的元素也要出栈；
     *
     */
    void push(Integer t) {
        dataStack.push(t);
        if (minStack.isEmpty() || t <= minStack.peek()) { //如果minStack为null,或者t 小于等于minStack的栈顶元素
            minStack.push(t);
        }
    }

    /**
     * 弹出元素时，需要注意，如果此时dataStack的栈顶元素等于minStack相等的话，那么同时弹栈；
     * @return 返回栈顶的元素
     */
    Integer pop() {
        Integer pop = dataStack.pop();
        if (Objects.equals(pop, minStack.peek())) {
            minStack.pop();
        }
        return pop;
    }

    /**
     * 获取此时占中元素的最小值
     */
    Integer getMin() {
        return minStack.peek();
    }
}
