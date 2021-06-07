package example.algorithm.interview.day.june;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName Day0605 前缀和/差分数组
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487011&idx=1&sn=5e2b00c1c736fd7afbf3ed35edc4aeec&chksm=9bd7f02baca0793d569a9633cc14117e708ccc9eb41b7f0add430ea78f22e4f2443f421c6841&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/7 7:53 下午
 **/
public class Day0605 {

    @Test
    public void testPrefixMinusArray () {
        int[][] booking = {
                {1,2,10},
                {2,3,20},
                {2,5,25}
        };
        int[] res = corpFlightBooking(booking, 5);
        System.out.println(Arrays.toString(res));
    }

    /**
     * 航班预定问题
     * 有n个航班，他们分别从1到n进行编号
     * 有一个航班预定表，表中第i条预定记录booking[i]=[i,j,k]
     * 表示 从第i个航班到第j个航班预定的k个作为(差分数组)
     * 请你返回一个长度为n的数组，按照航班的标号舒徐返回每个航班上预定的作为；
     */

    int[] corpFlightBooking (int[][] bookings,int n) {
        int[] res = new int[n]; //差分数组
        for (int[] booking : bookings) {
            int i = booking[0], j = booking[1], k = booking[2];
            res[i-1] += k;
            if (j < n) {
                res[j] -= k;
            }
        }

        for (int i = 1; i < n; i++) {
            res[i] = res[i-1] + res[i];
        }
        return res;
    }

}
