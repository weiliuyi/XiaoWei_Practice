package example.algorithm.interview.day.july;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0705 堆的数据结构
 * @Description
 * @Author weiliuyi
 * @Date 2021/7/5 5:01 下午
 **/
public class Day0705 {


    /**
     *   0
     * 1   2
     * <p>
     * 父结点：i
     * 左结点：2 * i + 1
     * 右结点：2* i + 2
     * <p>
     * 结点i：
     * 父节点： (i -1) / 2
     *
     *          2
     *       1     5
     *     4
     *
     *
     *     1
     *   2   5
     *  4
     *
     */

    @Test
    public void testHeap() {
        Integer[] arr = {2,1,5,4};

        MyHeapStruct myHeap = new MyHeapStruct(arr);
        while (myHeap.size != 0) {
            System.out.println(myHeap.poll());
        }
    }


    private static class MyHeapStruct implements IHeap<Integer> {

        Integer[] array;
        int size;

        public MyHeapStruct(Integer[] array) {
            initialHeap(array);
        }

        public MyHeapStruct() {
            this.size = 0;
            array = new Integer[4];
        }

        void initialHeap(Integer[] array) {
            this.size = array.length;

            for (int i = (array.length-2) / 2; i >=0 ; i--) {
                setDown(array,i);
            }
            this.array = array;
        }



        void setUp (Integer[] arr,int index) {
            if (index == 0) return;

            int p = (index -1) / 2;

            int left = (2 * p) -1,right = (2 * p) + 2;

            int min = index;

            if (arr[min] > arr[left]) min = left;

            if (right < arr.length && arr[right] < arr[min]) min = right;

            if (min != right) {
                swap(arr,min,index);
                setUp(arr,p);
            }


        }

        void setDown(Integer[] arr, int index) {
            int lastNode = (size() - 2) / 2;
            if ( size() < 2 ||  index > lastNode) return;

            int left = 2 * index + 1;
            int right = 2 * index + 2;


            int min = index;
            if (arr[left] < arr[min]) {
                min = left;
            }
            if (right < size() && arr[right] < arr[min]) {
                min = right;
            }
            if (min != index) {
                swap(arr,min,index);
                setDown(arr,min);
            }
        }

        void swap(Integer[] arr, int i, int j) {
            Integer temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }


        @Override
        public void add(Integer e) {
            array[size++] = e;
            setUp(this.array,size-1);
        }

        @Override
        public Integer poll() {
            int res = this.array[0];
            swap(this.array,0,size-1);
            size--;
            setDown(array,0);
            return res;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public String toString() {
            return "MyHeapStr{" +
                    "array=" + Arrays.toString(array) +
                    ", size=" + size +
                    '}';
        }
    }


    private interface IHeap<T> {

        void add(T e);

        T poll();

        int size();
    }

}
