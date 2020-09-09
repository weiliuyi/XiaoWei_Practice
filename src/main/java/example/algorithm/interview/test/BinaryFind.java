package example.algorithm.interview.test;

import org.junit.Test;

/**
 * @description: 二分查找
 * @author: weiliuyi
 * @create: 2020--07 18:33
 **/
public class BinaryFind {
    /**
     * 在一个有序数组中，查找某个数是否存在
     * 在一个有序数组中，查询>=某个数的最左侧的位置
     * 在一个有序数组中，查找<=某个数的最右侧的位置
     * 局部最小值问题
     */

    @Test
    public void test1() {
        int[] array = new int[]{1, 3, 5, 6, 7, 8, 10, 20, 23};
        System.out.println(binarySearch(array, 20));
        System.out.println(binarySearch(array, 1));
        System.out.println(binarySearch(array, 23));
    }

    /**
     * 在一个有序数组中，查找某个数是否存在
     *
     * @return target的下标
     */
    private int binarySearch(int[] array, int target) {
        int result = -1;
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mind = (left + right) / 2;
            if (array[mind] > target) {
                right = mind - 1;
            } else if (array[mind] < target) {
                left = mind + 1;
            } else {
                result = mind;
                break;
            }
        }
        return result;
    }

    @Test
    public void test2() {
        int[] array = new int[]{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};
        System.out.println(binarySearchV2(array, 2));
        System.out.println(binarySearchV2(array, 3));
        System.out.println(binarySearchV2(array, 1));
    }

    /**
     * 在一个有序数组中，查询>=某个数的最左侧的位置
     * 示例：1122333  查询大于等于2的最左侧的位置
     */
    private int binarySearchV2(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            int mind = (left + right) / 2;
            if (array[mind] < target) {
                left = mind + 1;
            } else if (array[mind] >= target) {
                right = mind - 1;
            }
        }
        return left;
    }

    @Test
    public void test3() {
        int[] array = new int[]{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};
        System.out.println(binarySearchV3(array, 2));
        System.out.println(binarySearchV3(array, 3));
        System.out.println(binarySearchV3(array, 1));
    }


    /**
     * 在一个有序数组中，查找<=某个数的最右侧的位置
     * <p>
     * 示例：1122333  查询大于等于2的最右侧的位置
     */
    private int binarySearchV3(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            int mind = (left + right) / 2;
            if (array[mind] <= target) {
                left = mind + 1;
            } else if (array[mind] > target) {
                right = mind - 1;
            }
        }
        return left;
    }


    /**
     * 局部最小值问题 (数组中的值各不相等)
     * 也可以使用二分法，
     */
    @Test
    public void test4() {
        int[] array = new int[]{12, 7, 9, 5, 4, 1, 11, 8, 20};
        System.out.println(getPartMin(array));
    }


    /**
     * 获得局部最小值
     *
     * @param array 测试数组
     * @return 返回局部最小的下标
     */
    private int getPartMin(int[] array) {
        if (array == null || array.length <= 1) {
            return -1;
        }
        if (array[0] < array[1]) {
            return 0;
        }
        if (array[array.length - 2] > array[array.length - 1]) {
            return array.length - 1;
        }
        int left = 0, right = array.length - 1;
        while (left < right) {
            int mind = (left + right) / 2;
            if (array[mind] > array[mind - 1]) {
                right = mind;
            } else if (array[mind] > array[mind + 1]) {
                left = mind;
            } else {
                return mind;
            }
        }
        return -1;
    }
}
