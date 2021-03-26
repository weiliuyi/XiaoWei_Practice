package example.algorithm.interview.day;


import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

/**
 * 单调栈相关的算法
 *
 * 此题的暴力求解比较简单--忽略
 */
public class Day0325 {

    @Test
    public void test () {
        int[] result = {2, 1, 2, 4, 3};
        System.out.println("beforeGreaterEle = " + Arrays.toString(beforeGreaterEle(result)));
        System.out.println("nextGreaterEle = " + Arrays.toString(nextGreaterEle(result)));
        System.out.println("nextGreaterEleVersion2 = " + Arrays.toString(nextGreaterEleVersion2(result)));
    }


    @Test
    public void testTemperatures () {
        //比如说给你输入T = [73,74,75,71,69,76]，你返回[1,1,3,2,1,0]。
        int[] temp = new int[] {73,74,75,71,69,76};
        System.out.println("dailyTemperatures = " + Arrays.toString(dailyTemperatures(temp)));
    }


    /**
     * 前一个比它大的元素
     * @param arr 数组
     * @return
     */
    private int[] beforeGreaterEle(int[] arr) {
        int[] result = new int[arr.length];
        Stack<Integer> stack = new Stack(); //从大到小的栈
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty()) { //如果栈不为null
                if (stack.peek() > arr[i]) { //如果栈顶元素大于目标元素，栈顶元素放入到result数组中，并且将目标元素押入栈
                    result[i] = stack.peek();
                    stack.push(arr[i]);
                    break;
                }
                stack.pop();
            }
            if (stack.isEmpty()) {  //如果栈为空，直接押入栈，并且设置标志为-1
                stack.push(arr[i]);
                result[i] = -1;
            }
        }
        return result;
    }

    /**
     * 下一个比它大的元素数组 从前向后进行遍历
     */
    private int[] nextGreaterEle (int[] arr) {
        int[] result = new int[arr.length];

        Stack<Integer> indexStack = new Stack<>();//存放数据的下标，角标对数的数组从大到小的顺序排列
        for (int i = 0;i < arr.length;i++) {
            while (!indexStack.isEmpty()) {//如果栈不为null
                if (arr[indexStack.peek()] >= arr[i]) { //栈顶元素大于此arr[i]，押入栈并且跳出循环
                    indexStack.push(i);
                    break;
                }
                //栈顶的元素小于arr[i]，也就是栈顶元素的下一个比它大的元素为 arr[i]
                result[indexStack.peek()] = arr[i];
                indexStack.pop();
            }
            if (indexStack.isEmpty()) {
                indexStack.push(i);
            }
        }
        while (!indexStack.isEmpty()){
            result[indexStack.peek()] = -1;
            indexStack.pop();
        }
        return result;
    }


    /**
     * 下一个比当前元素的大的数 从后向前进行循环
     */
    private int[] nextGreaterEleVersion2(int[] arr) {
        int[] result = new int[arr.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = arr.length-1;i >= 0;i--) {
            while (!stack.isEmpty()) {
                if (stack.peek() > arr[i]) {
                    result[i] = stack.peek();
                    stack.push(arr[i]);
                    break;
                }
                stack.pop();
            }
            if (stack.isEmpty()) {
                stack.push(arr[i]);
                result[i] = -1;
            }
        }
        return result;
    }

/**
 * 给你一个数组T，这个数组存放的是近几天的天气气温，你返回一个等长的数组
 * ，计算：对于每一天，你还要至少等多少天才能等到一个更暖和的气温；如果等不到那一天，填 0
 */

private int[] dailyTemperatures (int[] temperatures) {
    int[] result = new int[temperatures.length];
    Stack<Integer> indexStack = new Stack<>();

    for (int i = temperatures.length-1; i >=0; i--) {

        while (!indexStack.isEmpty()) {
            if (temperatures[indexStack.peek()] > temperatures[i]) {
                result[i] = indexStack.peek() - i;
                indexStack.push(i);
                break;
            }
            indexStack.pop();
        }

        if (indexStack.isEmpty()) {
            indexStack.push(i);
        }
    }
    return result;
}




}
