package example.algorithm.interview.day.july;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * @ClassName Day0702 快速选择算法详解
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247488820&idx=1&sn=e6a58b67b0050ae8144bb8ea579cf0d0&chksm=9bd7eb3caca0622a20b407f83decfa56e969002cd4e041b859d4feba1522940a5f7b78849060&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/7/2 3:58 下午
 **/
public class Day0702 {


    /**
     * 快速选择算法，快速排序算法的亲兄弟；
     * 原始题目： 输入一个无序数组的nums,和一个正整数K，计算nums中第K大的元素
     * 第K个最大元素
     * 第K大元素
     */

    @Test
    public void testQuickSelect() {
        int[] nums = {2, 1, 5, 4};
        IQuickSelect binaryHeap = new BinaryHeap();
        System.out.println(binaryHeap.findKthLargest(nums, 2));

        IQuickSelect myQuickSelect = new MyQuickSelect();
        System.out.println(myQuickSelect.findKthLargest(nums, 2));


        IQuickSelect finalQuickSelect = new FinalQuickSelect();
        System.out.println(finalQuickSelect.findKthLargest(nums, 2));

        IQuickSelect iQuick = new MyStruckQuickSelect();
        System.out.println(iQuick.findKthLargest(nums,2));

    }

    @Test
    public void testHeap () {
        System.out.println("---------------");
        Integer[] numV2 = {2, 1, 5, 4};
        MyHeapImpl myHeap = new MyHeapImpl(numV2);
        while (myHeap.size() != 0) {
            System.out.print(myHeap.poll() + "  ");
        }
        System.out.println();

        System.out.println( -1 % 2);


        IHeap<Integer> heap = new MyHeapImpl();
        heap.offer(2);
        heap.offer(1);
        heap.offer(5);
        heap.offer(4);

        while (heap.size() != 0) {
            System.out.print(heap.poll() + "  ");
        }
        System.out.println();
    }



    private static class MyStruckQuickSelect implements IQuickSelect {

        @Override
        public int findKthLargest(int[] nums, int k) {
            IHeap<Integer> myHeap = new MyHeapImpl();
            int index = nums.length - k;
            for (int num : nums) {
                myHeap.offer(num);
                if (myHeap.size() > index) {
                    myHeap.poll();
                }
            }
            return myHeap.poll();
        }
    }

    /**
     * 二分查找的思想
     */
    private static class FinalQuickSelect implements IQuickSelect {

        @Override
        public int findKthLargest(int[] nums, int k) {
            int left = 0, right = nums.length - 1;
            k = nums.length - k; // 索引转化

            while (left <= right) {
                //在nums[lo,hi]中选一个分界点
                int p = partition(nums, left, right);
                if (p > k) {
                    //第K大的元素在[left,p-1]中
                    right = p - 1;
                } else if (p < k) {
                    //第K大的元素在[p+1,right]中
                    left = p + 1;
                } else {
                    //找到第k大的元素
                    return nums[p];
                }

            }
            return -1;
        }

        int partition(int[] nums, int left, int right) {
            int lp = left, rp = right + 1, pivot = nums[left];

            while (true) {
                while (nums[++lp] <= pivot) {
                    if (lp == right) break;
                }

                while (nums[--rp] >= pivot) {
                    if (rp == left) break;
                }

                if (lp >= rp) break;

                swap(nums, lp, rp);
            }
            swap(nums, rp, left);

            return rp;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }


    /**
     * 使用递归的方式进行
     */
    private static class MyQuickSelect implements IQuickSelect {
        @Override
        public int findKthLargest(int[] nums, int k) {

            //第K个最大元素      n-k+1
            return findTarget(nums, 0, nums.length - 1, nums.length - k);
        }

        private int findTarget(int[] nums, int left, int right, int k) {
            if (left > right) return -1;

            if (left == right && k == left) return nums[right];

            int p = partition(nums, left, right);

            if (p > k) {
                return findTarget(nums, left, p - 1, k);
            }

            if (p < k) {
                return findTarget(nums, p + 1, right, k);
            }

            return nums[p];
        }

        int partition(int[] nums, int left, int right) {
            if (left >= right) return left;

            int lp = left, rp = right + 1, pivot = nums[left];
            while (true) {
                while (nums[++lp] <= pivot) {
                    if (lp == right) break;
                }

                while (nums[--rp] >= pivot) {
                    if (rp == left) break;
                }

                if (lp >= rp) break;

                swap(nums, lp, rp);
            }
            swap(nums, left, rp);
            return rp;
        }

        void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }


    /**
     * 使用优先级队列；
     */
    private static class BinaryHeap implements IQuickSelect {

        @Override
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> queue = new PriorityQueue<>();
            for (int num : nums) {
                queue.offer(num);
                if (queue.size() > k) {
                    queue.poll();
                }
            }
            return queue.remove();
        }
    }


    /**
     * 快速选择算法；
     */
    private interface IQuickSelect {
        int findKthLargest(int[] nums, int k);
    }

    private interface IHeap<T>  {

        int size();

        T poll ();

        void offer(T e);

    }


    private static class MyHeapImpl implements  IHeap<Integer> {

        Integer[] arr;
        int size;


        public MyHeapImpl() {
            this.arr = new Integer[16];
        }

        public MyHeapImpl(Integer[] arr) {
            heapify(arr);
            this.arr = arr;
            this.size = arr.length;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public Integer poll() {
            int res = this.arr[0];
            swap(this.arr,0,size-1);
            size--;
            setDown(this.arr,0,size-1);
            return res;
        }

        @Override
        public void offer(Integer e) {
            arr[size++] = e;
            setUp(size-1);
        }

        private void heapify(Integer[] arr) {
            for (int i = (arr.length-2) / 2; i >= 0 ; i--) {
                setDown(arr,i,arr.length-1);
            }
        }

        private void setDown (Integer[] arr,int index,int end) {
            int lastNode = (end -1) / 2;

            if (end < 1 || index > lastNode) return;

           int left = 2 * index + 1;
           int right = left + 1;

           int min = index;
           if (arr[left] < arr[min])
               min = left;

           if (right <= end && arr[right] < arr[min])
               min = right;

           if (min != index) {
               swap(arr,min,index);
               setDown(arr,min,end);
           }
        }



        private void setUp (int index) {
            if (index < 1) return;

            int p = (index -1) / 2;
            if (this.arr[p] > this.arr[index]) {
                swap(this.arr,p,index);
                setUp(p);
            }
        }

        void swap (Integer[] arr,int i,int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


}
