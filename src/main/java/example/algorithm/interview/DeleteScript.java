package example.algorithm.interview;

import java.util.Arrays;
import java.util.Stack;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--03 14:44
 **/
public class DeleteScript {


    public static void main(String[] args) {
        System.out.println(Arrays.toString(getLeftLatestBigNear(new int[]{3,2,1,4,5,6,3,2})));
        System.out.println(Arrays.toString(getLeftLatestSmallNear(new int[]{3,2,1,4,5,6,3,2})));
    }


    private static int [] getLeftLatestSmallNear(int[] targetArr) {
        int len = targetArr.length;
        Stack<Integer> stack = new Stack<>(len);
        int[] result= new int[len];
        for (int i = 0;i < len;i++) {
            while (!stack.isEmpty() && targetArr[i]<= stack.peek()) {
                stack.poll();
            }
            if (stack.isEmpty()) {
                result[i] = -1;
            } else {
                result[i] = stack.peek();
            }
            stack.push(targetArr[i]);
        }
        return result;
    }


    /**
     * 数组中元素左侧第一个比此元素大的
     * @param targetArr
     * @return
     */
    private static int[] getLeftLatestBigNear(int[] targetArr) {
        int len = targetArr.length;
        Stack<Integer> stack = new Stack<>(len);
        int [] result= new int[len];
        for (int i = 0;i < len;i++) {
            while (!stack.isEmpty() && targetArr[i] >= stack.peek()) {
                stack.poll();
            }
            if (stack.isEmpty()) {
                result[i] = -1;
            } else {
                result[i] = stack.peek();
            }
            stack.push(targetArr[i]);
        }
        return result;
    }






    @SuppressWarnings("unchecked")
    static class Stack<T> {
        private Object[] array;
        private int top = -1;

         Stack(int len) {
            array = new Object[len];
        }

         T poll() {
            return (T)array[top--];
        }

         void push(T t) {
            array[++top] = t;
        }

         T peek() {
            return (T) array[top];
        }


         boolean isEmpty () {
            return top == -1;
        }
    }
}
