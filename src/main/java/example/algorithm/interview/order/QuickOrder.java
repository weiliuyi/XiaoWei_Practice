package example.algorithm.interview.order;

import org.junit.Test;

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
            int index = partition(array, fromIndex, toIndex);  //分区其实就是荷兰国旗问题
            quickOrder(array, fromIndex, index - 1);
            quickOrder(array, index + 1, toIndex);
        }
    }

    /**
     * 每次讲数组一分为二，左边的小于基准值pivot，右边的大于基准值pivot
     * <p>
     * 快速排序，如果选取左侧第一个作为pivot,那么必须从右侧开始(扫描比pivot小的数)，然后在从左侧开始（扫描比pivot大的数），
     * 右侧先开始时：1.左指针追上右指针的时候(low == high)，array[low]一定是小于pivot的值
     * 2.右侧指针追上左指针的时候（low== high），array[low] 一定大于pivot的值
     * <p>
     * 如果左侧先开始呢？直接是不满足要求
     * 左侧先扫描，找到一个大于pivot的值是
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


    @Test
    public void test1() {
        int[] array = new int[]{10, 7, 8, 9, 20, 3, 7, 2};
        sortHeLanFlags(array, 8);
        System.out.println(Arrays.toString(array));
        sortHeLanFlags(array, 4);
        System.out.println(Arrays.toString(array));

    }

    /**
     * 荷兰国旗问题
     *
     * @param array  待排序的数组
     * @param target 分解数组
     */
    private void sortHeLanFlags(int[] array, int target) {
        int bound = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] <= target) {
                int temp = array[++bound];
                array[bound] = array[i];
                array[i] = temp;
                //System.out.println(Arrays.toString(array)  +  " bound " +bound );
            }
        }
        System.out.println("bound " + bound);
    }


    @Test
    public void test2() {
        int[] array = new int[]{10, 7, 8, 9, 20, 3, 7, 2};
        int[] indexArr = sortHeLanFlagsVersion2(array, 7);
        System.out.println(Arrays.toString(indexArr));

    }

    /**
     *
     * @param array 目标数组  更具相同的角标分割成三部分，小于target的一部分， 等于target的一部分  大于target的一部分
     * @param target 分界线
     * @return 返回指针范围
     */
    private int[] sortHeLanFlagsVersion2(int[] array, int target) {
        int l_bound = -1, r_bound = array.length;
        int i = 0;
        while (i < r_bound) {
            if (array[i] < target) {
                swap(array, i, ++l_bound);
                i++;
            } else if (array[i] > target) {
                swap(array, i, --r_bound);
            } else {
                i++;
            }

        }
        System.out.println(Arrays.toString(array));
        return new int[]{++l_bound, --r_bound};
    }


}
