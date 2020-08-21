package example.algorithm.interview.order;

import java.util.Arrays;

/**
 * @description: 插入排序
 * @author: weiliuyi
 * @create: 2020--20 11:37
 **/
public class InsertOrder {

    public static void main(String[] args) {
        int[] array = {5, 3, 1, 9, 7, 6, 4, 2, 0};
        insertOrder(array);
        System.out.printf(Arrays.toString(array));
    }

    private static void insertOrder(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int value = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > value) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = value;
        }
    }

    /**
     * 交换数组角标的两个值
     */
    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

}
