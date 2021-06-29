package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0615 二分查找的妙用
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484598&idx=1&sn=69edaf4a7f6bfd0b1185cae5d0689c1d&chksm=9bd7fabeaca073a8820bc93cb67a8e26fa9eaa1ab9717b7e3ac41b4aac12235067c8af3520d5&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/20 3:18 下午
 **/
public class Day0615 {

    /**
     * 包裹运输的问题
     */
    @Test
    public void testPackage() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ShopWithinDay myShopWithinDay = new MyShopWithinDay();
        System.out.println(myShopWithinDay.shopWithinDay(arr, 5));

        System.out.println("-------------------");

        ShopWithinDay finalShopWithinDay = new FinalShopWithinDay();
        System.out.println(finalShopWithinDay.shopWithinDay(arr,5));
    }


    private static class FinalShopWithinDay implements ShopWithinDay {

        @Override
        public int shopWithinDay(int[] weights, int day) {
            int left = weights[weights.length - 1], right = Arrays.stream(weights).sum() + 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (canFinish(weights, day, mid)) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return right;
        }

        private Boolean canFinish(int[] w, int d, int cap) {
            int i = 0;
            for (int j = 0; j < d; j++) {
                int maxCap = cap;
                while ((maxCap -= w[i]) >= 0) {
                    i++;
                    if (i == w.length) return true;
                }
            }
            return false;
        }
    }


    /**
     * 自己实现
     */
    private static class MyShopWithinDay implements ShopWithinDay {
        @Override
        public int shopWithinDay(int[] weights, int day) {
            int left = weights[weights.length - 1], right = Arrays.stream(weights).sum() + 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (isValid(weights, mid, day)) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return right;
        }

        private Boolean isValid(int[] weights, int cap, int d) {
            int count = 0, tempSum = 0;
            for (int weight : weights) {
                if (tempSum + weight <= cap) {
                    tempSum += weight;
                    continue;
                }
                count++;
                tempSum = weight;  //最后一个或者最后几个货物没有进行处理；
            }
            if (tempSum > 0) count++;// 这个地方是最容易遗漏的，

            return count <= d;
        }

    }


    private interface ShopWithinDay {
        int shopWithinDay(int[] weights, int day);
    }

}
