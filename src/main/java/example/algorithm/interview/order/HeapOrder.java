package example.algorithm.interview.order;

import java.util.Arrays;

/**
 * @description: 堆排序
 * @author: weiliuyi
 * @create: 2020--19 17:22
 **/
public class HeapOrder {

    /*
    *      5
    *    3   1
    *  9  7  6
    * 从0开始
    * 左： 2 * i + 1
    * 右： 2 * i + 2
    *
    *父节点为：(i - 1) / 2
    *
    * 小根堆
    * */
    public static void main(String[] args) {
        int[] array = {5, 3, 1, 9, 7, 6, 4};
        initHeap(array);
        System.out.println(Arrays.toString(array));
    }


    private static void initHeap(int[] array) {
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            heapify(array, i,array.length - 1);
        }
    }

    /**
     * 将数组初始化小根堆
     * 思想：将index 和左右子树的根节点进行比较，和其中比较小的节点进行交换；
     * 然后对交换的子树进行heapify(进行递归)
     *
     */
    private static void heapify(int[] array, int index,int endIndex) {
        if (2 * index + 1 > endIndex) { //如果发现左子树的index 大于 数组的最后一个角标；那么退出
            return;
        }
        //将根节点和左右子树中比较小的进行交换
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int min = array[index];
        int target = index;
        if (min > array[left]) {
            min = array[left];
            target = left;
        }
        if (right <= array.length - 1 && min > array[right]) {
            target = right;
        }
        if (target != index) {
            //根节点和子树进行交换
            swap(array, index, target);
            //对交换的子树进行heapify;破坏了堆的结构，重新进行初始化
            heapify(array, target,endIndex);
        }
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

}
