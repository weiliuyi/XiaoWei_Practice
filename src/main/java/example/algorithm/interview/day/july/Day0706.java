package example.algorithm.interview.day.july;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName Day0706 随机乱置算法
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484503&idx=1&sn=e30ef74eb16ad385c16681cd6dfe15cf&chksm=9bd7fa5faca07349c6877bc69f9a27e13585f2c5ed2237ad37ac5b272611039391acc1dcd33d&scene=21#wechat_redirect
 * @Author weiliuyi
 * @Date 2021/7/6 3:14 下午
 **/
public class Day0706 {

    /**
     * 随机乱置算法：都是靠随机选择元素交换来获取随机性
     * 蒙特卡洛算法：概率均等是算法的衡量标准
     */

    @Test
    public void testShuffle() {
        int[] arr = {1,2,3,4};
        ShuffleV1 v1 = new ShuffleV1();
        v1.shuffle(arr);
        System.out.println(Arrays.toString(arr));
        IShuffle v2 = new ShuffleV2();
        v2.shuffle(arr);
        System.out.println(Arrays.toString(arr));
        IShuffle v3 = new ShuffleV3();
        v3.shuffle(arr);
        System.out.println(Arrays.toString(arr));
        IShuffle v4 = new ShuffleV4();
        v4.shuffle(arr);
        System.out.println(Arrays.toString(arr));

    }


    /**
     * 乱置随机算法的第一种方式
     */
    private static class ShuffleV1 implements IShuffle {
        @Override
        public void shuffle(int[] arr) {
            int len = arr.length;
            for (int i = 0; i < len; i++) {
                int index = random(i, len - 1);
                swap(arr,i,index);
            }
        }
    }

    private static class ShuffleV2 implements IShuffle {

        @Override
        public void shuffle(int[] arr) {
            int len = arr.length;
            for (int i = 0; i < len - 1; i++) {
                int index = random(i,len -1);
                swap(arr,i,index);
            }
        }
    }


    private static class ShuffleV3 implements IShuffle{
        @Override
        public void shuffle(int[] arr) {
            int len = arr.length;
            for (int i = len-1; i >= 0 ; i--) {
                int index = random(0,i);
                swap(arr,index,i);
            }
        }
    }

    private static class ShuffleV4 implements IShuffle {
        @Override
        public void shuffle(int[] arr) {
            int len = arr.length;
            for (int i = len -1; i > 0 ; i--) {
                int index = random(0,i);
                swap(arr,index,i);
            }
        }
    }




    private interface IShuffle {

        void shuffle (int[] arr);

        default int random (int left,int right) {
            Random random = new Random();
            return random.nextInt((right - left + 1)) + left; //[0,right - left)
        }

        default void swap (int[] arr,int i,int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
