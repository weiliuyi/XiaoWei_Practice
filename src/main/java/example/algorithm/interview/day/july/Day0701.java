package example.algorithm.interview.day.july;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0701 快速排序
 * @Description
 * @Author weiliuyi
 * @Date 2021/7/2 4:41 下午
 **/
public class Day0701 {


    @Test
    public void testQuickSort() {
        IQuickSort myQuickSort = new MyQuickSort();
        int[] nums = {8, 9, 2, 1, 3, 3};
        int[] numV2 = Arrays.copyOf(nums,nums.length);
        myQuickSort.quickSort(nums);
        System.out.println(Arrays.toString(nums));

        IQuickSort finalQuickSort =  new FinalQuickSort();
        finalQuickSort.quickSort(numV2);
        System.out.println(Arrays.toString(numV2));
    }


    private static class FinalQuickSort extends AbstractQuickSort {
        @Override
        public int partition(int[] nums, int left, int right) {
            int pivot = nums[left];//将nums[lo]作为默认分界点 pivot
            int lp = left,rp = right+1; // rp = right + 1 这是因为while中先执行--
            while (true) {
                //保证[left,lp） 都小于pivot  lp指向大于pivot的元素
                while (nums[++lp] <= pivot) {// 左侧指针指向大于 pivot的
                    if (lp == right) break; //为什么判断 lp == right  而不是判断 lp == rp 这是因为 rp = right + 1,如果数组从大到到小排列的时候，会发生数组角标越界异常；
                }
                //保证(rp,right] 都是大于pivot, rp指向小于pivot的元素
                while (nums[--rp] >= pivot) {// 右侧指针指向小于pivot
                    if (rp == left ) break; // 这是是可以 rp == lp rp可能指向大于pivot的元素，但是会导致 swap(nums,rp,left)作物，此时
                }
                if (lp >= rp) break;
                //如果走到这一步，一定有nums[lp] > pivot,nums[rp] < pivot,所以需要交换 nums[lp] nums[rp] 保证nums[left,lp] < pivot < nums[rp,right]
                swap(nums,lp,rp);
            }
            swap(nums,rp,left);
            return rp;
        }

        void swap (int[] nums,int i ,int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    private static class MyQuickSort extends AbstractQuickSort {

        @Override
        public int partition(int[] nums, int left, int right) {
            if (left == right) return left;

            int pivot = nums[left];
            while (true) {
                while (left < right && nums[right] >= pivot) right--;
                if (left == right) {
                    break;
                }
                nums[left++] =  nums[right];
                while (left < right && nums[left] <= pivot) left++;
                if (left == right) {
                    break;
                }
                nums[right--] = nums[left];
            }
            //循环跳出的条件是 left== right
            nums[left] = pivot;
            return left;
        }

    }

    private static abstract class AbstractQuickSort implements IQuickSort {
        @Override
        public void quickSort(int[] nums) {
            quickSort(nums, 0, nums.length - 1);
        }

        private void quickSort(int[] nums, int left, int right) {
            if (left >= right) return;
            int p = partition(nums, left, right);
            quickSort(nums, left, p - 1);
            quickSort(nums, p + 1, right);
        }

        public abstract int partition(int[] nums, int left, int right);
    }

    /**
     * 快速排序接口类
     */
    private interface IQuickSort {
        void quickSort(int[] nums);
    }
}
