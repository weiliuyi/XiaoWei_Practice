package example.algorithm.interview.order;

import java.util.Arrays;

/**
 * @description: 快速排序算法
 * @author: weiliuyi
 * @create: 2020--18 14:54
 **/
public class QuickOrder {

    public static void main(String[] args) {
        int[] array = new int[]{3, 1, 5, 9, 7, 6};
        quickOrder(array, 0, 5);
        System.out.println(Arrays.toString(array));
    }


    /**
     * 每次讲数组一分为二，然后在各自的子数组中进行排序； 递归的思想；
     * <p>
     * 快速排序的思想：每次随机选定一个数，将小于这个数的放在左边，将大于这个数放在右边；每次操作之后，都会有一个数归为；
     * 选定一个Pivot，之后，从数组的末尾到开始扫描，寻找比pivot小的数据，
     * 2.然后从左向右寻找比Pivot大的数据；
     *
     * @param array     数组
     * @param fromIndex 起始index
     * @param toIndex   末尾index
     */
    private static void quickOrder(int[] array, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            int index = partition(array, fromIndex, toIndex);
            quickOrder(array, fromIndex, index - 1);
            quickOrder(array, index + 1, toIndex);
        }
    }

    /**
     * 每次讲数组一分为二，左边的小于基准值pivot，右边的大于基准值pivot
     */
    private static int partition(int[] array, int low, int high) {
        int pivot = array[low];
        while (low < high) {
            while (array[high] > pivot && low < high) {
                high--;
            }
            //这个地方可以进行优化  要么是low == high  要么是 array[high] <= pivot
            /*if (low < high && array[high] <= pivot) {
                array[low] = array[high];
            }*/
            array[low] = array[high];
            while (array[low] < pivot && low < high) {
                low++;
            }
            /*if (low < high && array[low] >= pivot) {
                array[high] = array[low];
            }*/
            array[high] = array[low];
        }
        array[low] = pivot;
        System.out.println(Arrays.toString(array));
        System.out.println(low + "   " + high);
        return low;
    }


    public static void swap(int[] array, int fromIndex, int toIndex) {
        int temp = array[fromIndex];
        array[fromIndex] = array[toIndex];
        array[toIndex] = temp;
    }
}
