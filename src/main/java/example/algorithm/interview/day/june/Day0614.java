package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0614  二分查找的妙用
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484598&idx=1&sn=69edaf4a7f6bfd0b1185cae5d0689c1d&chksm=9bd7fabeaca073a8820bc93cb67a8e26fa9eaa1ab9717b7e3ac41b4aac12235067c8af3520d5&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/20 11:07 上午
 **/
public class Day0614 {

    /*
     * 二分查找算法如何应用到实际的算法中去？？？？
     * 当搜索空间有序的时候，可以通过二分搜索「剪枝」，大幅提升效率；
     */

    /**
     * KoKo 吃香蕉
     */
    @Test
    public void testEatBanana() {
        int[] piles = {3, 6, 7, 11};
        System.out.println(eatSpeed(piles, 4));
        System.out.println(eatSpeedV2(piles, 4));
        System.out.println(eatSpeed(piles, 8));
        System.out.println(eatSpeedV2(piles, 8));
        int[] pilesV2 = {30,11,23,4,20};
        System.out.println(eatSpeed(pilesV2,5));
        System.out.println(eatSpeedV2(pilesV2,5));
    }

    /**
     * 搜索空间是什么？ 吃香蕉的速度
     * 吃香蕉的速度区间[MinPiles,MaxPiles]
     *
     * 也就是在[MinPiles,MaxPiles]最左元素
     */
    public int eatSpeed(int[] piles, int h) {
        Arrays.sort(piles);
        int left = piles[0], right = piles[piles.length - 1];
        while (left <= right) {
            int mid = (left + right) / 2;
            if (isValid(piles, mid, h)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left == piles[piles.length-1] + 1 ? -1 : left;
        // return left == piles.length ? -1 : left; //这个地方是错误的；left == MaxSpeed+1时，才是无解的
    }


    public int eatSpeedV2 (int[] piles,int h) {
        Arrays.sort(piles);
        int left = piles[0],right = piles[piles.length-1] + 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (isValid(piles,mid,h)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right == piles[piles.length -1] + 1 ? -1 : right;
    }

    private Boolean isValid(int[] piles, int speed, int h) {
        int count = 0;
        for (int pile : piles) {
            //count += (pile % speed == 0 ? (pile / speed) : (pile / speed) + 1);
            count += pile / speed + (pile % speed == 0 ?  0 : 1);
        }
        return count <= h;
    }

    public static void main(String[] args) {
        int res = Math.floorDiv(5, 3);
        System.out.println(res);
        System.out.println(5 / 2);
    }

}
