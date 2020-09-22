package example.algorithm.interview.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @description: 归并排序 递归和非递归
 * @author: weiliuyi
 * @create: 2020--22 12:47
 **/
public class MergeSorted {


    @Test
    public void test1() {
        int[] array = {3,2,1,5,8,6,5,4};
        mergeSorted(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 对数组进行归并排序
     * @param array 目标数组
     */
    private void mergeSorted(int[] array) {
        if (array == null || array.length == 1) {
            return;
        }
        processArray(array, 0, array.length - 1);
    }

    /**
     * 递归实现归并排序
     *
     * @param array 目标数组
     * @param l     数组的左侧指针
     * @param r     数组的右侧指针
     */
    private void processArray(int[] array, int l, int r) {
        if (l == r) { //当左右指针相等时，只有一个元素，此时是有序的
            return;
        }
        int mid = (l + r) / 2;
        processArray(array, l, mid); //对左侧进行排序
        processArray(array, mid + 1, r); //对右侧进行排序
        mergerArray(array,l,r,mid); //合并左右两侧的数组
    }


    /**
     * 将数组左侧[有序l,mid] 和 右侧有序[mid+1,r]进行合并
     * @param array 目标数组
     * @param l 数组的左侧指针
     * @param r 数组的右侧指针
     * @param mid 中间值
     */
    private void mergerArray (int[] array,int l,int r,int mid) {
        int[] help = new int[r - l + 1];
        int h_i = 0;
        int p1 = l,p2 = mid+1;
        while (p1 <= mid && p2 <=r) {
            help[h_i++] = array[p1] <= array[p2] ? array[p1++] : array[p2++];
        }
        while(p1 <= mid) {
            help[h_i++] = array[p1++];
        }
        while (p2 <= r) {
            help[h_i++] = array[p2++];
        }
       /* while (h_i > 0) {  //这样做容易出错
            array[r--] = help[--h_i];
        }*/
       // h_i 0,1,2,3, ... h_i-1
        IntStream.range(0, h_i).forEach(i -> array[l + i] = help[i]);
    }

}
