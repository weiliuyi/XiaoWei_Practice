package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @ClassName Day0603 分治算法详解
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247488970&idx=1&sn=d4eb6a371f1706d76e370be18b27afb4&chksm=9bd7ebc2aca062d4e9d62bb363a1e386cc5b42224e63c505f902275c48f03fd8f8289b717fb2&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/4 8:16 下午
 **/
public class Day0603 {


    @Test
    public void testDivide() {
        int[] arr = {9,8,6,8,4,3,10,16,5};
        MergerSort mergerSorted = new MergerSortImpl();
        mergerSorted.mergerSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static class MergerSortImpl implements MergerSort {

        @Override
        public void mergerSort(int[] arr) {
            mergerSort(arr,0,arr.length-1);
        }

        /**
         * arr[low,high]进行排序
         */
        private void mergerSort(int[] arr, int low, int high) {
            if (low == high) {
                return;
            }
            int mid = (low + high) / 2;
            mergerSort(arr,low,mid);
            mergerSort(arr,mid+1,high);
            //合并两个数组
            mergerArr(arr,low,mid,high);

        }


        public void mergerArr (int[] arr,int low,int mid,int high) {
//这种方式是错误的，无法正确的进行合并数组，
//如果合并的过程：la > mid跳出循环，也就是左侧的首先排序完成，如果右侧先排序完成(lr > high),左侧较大数数没有进行合并；
//但是不会出现上述情况；
//            int la = low,lr= mid+1;
//            while (la <= mid && lr <= high) {
//                if (arr[la] <= arr[lr]) {
//                    la++;
//                } else {
//                    //交换两个数，这是有问题的？？ 左右数组交换对应的位置之后，其实并没有进行排序；
//                    int temp = arr[la];
//                    arr[la] = arr[lr];
//                    arr[lr] = temp;
//                    la++;
//                    lr++;
//                }
//            }

            int[] tempArr = new int[high - low + 1];
            int i = 0;
            int p1 = low,p2= mid + 1;
            while (p1 <= mid && p2 <= high) {
                tempArr[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
            }
            while (p1 <= mid) {
                tempArr[i++] = arr[p1++];
            }
            while (p2 <= high) {
                tempArr[i++] = arr[p2++];
            }
            IntStream.range(0, tempArr.length)
                    .forEach(j -> arr[low + j] = tempArr[j]);
        }
    }


    /**
     * 归并排序的接口类
     */
    private interface MergerSort {
        void mergerSort(int[] arr);
    }

}
