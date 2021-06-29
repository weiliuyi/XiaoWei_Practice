package example.algorithm.interview.day.june;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName Day0616 分割数组的最大值
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487594&idx=1&sn=a8785bd8952c2af3b19890aa7cabdedd&chksm=9bd7ee62aca067742c139cc7c2fa9d11dc72726108611f391d321cbfc25ccb8d65bc3a66762b&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/21 12:44 下午
 **/
public class Day0616 {

    /**
     * 给定一个非负的整数数组和一个整数m，你需要将这个数组分成m个非空的连续子数组，设计一个算法使得这m个子数组各自和的最大值最小；
     */
    @Test
    public void testSplitArray() {
        int[] nums = {7,2,5,10,8};

        MaxSubArrMin myMaSubArrMin = new MyMaxSubArrMin();
        System.out.println(myMaSubArrMin.splitArray(Arrays.stream(nums).boxed().collect(toList()), 2));

        System.out.println("-------------------------");
        MaxSubArrMin myBinaryMin = new MyBinaryMaxSubArrMin();
        System.out.println(myBinaryMin.splitArray(Arrays.stream(nums).boxed().collect(toList()),2));

        System.out.println("-------------------------");
        MaxSubArrMin finalDetailBinary = new FinalBinaryDetailMaxSubArrMin();
        System.out.println(finalDetailBinary.splitArray(Arrays.stream(nums).boxed().collect(toList()),2));

    }

    /**
     * 拆成m个非空子数组，需要m-1次分割
     *
     * n个元素  共有n-1，位置能够被分割
     * 失败
     *
     * 暴力回溯算法，穷举所有的切割方法，然后求出各自切割方法的最大值，然后在求出最小值；
     *
     */
    private static class MyMaxSubArrMin implements MaxSubArrMin {
       List<List<Integer>> splitList =  new ArrayList<>();

        @Override
        public int splitArray(List<Integer> nums, int m) {
            splitArray(nums,0,m-1,new ArrayList<>());
            System.out.println(JSON.toJSONString(splitList));
            return splitList.stream().mapToInt(list -> list.stream().mapToInt(num -> num).max().orElse(-1)).min().orElse(-1);
        }

        private void  splitArray (List<Integer>nums,int start,int splitCnt,List<Integer> res) {
            if (nums.size() - start <= splitCnt) {
                return;
            }
            if (splitCnt == 0) {
                ArrayList<Integer> tempList = new ArrayList<>(res);
                tempList.add(nums.subList(start,nums.size()).stream().mapToInt(num -> num).sum());
                splitList.add(tempList);
                return;
            }

            for (int i = start; i < nums.size()-1 ; i++) {
                int subSum = nums.subList(start, i + 1).stream().mapToInt(num -> num).sum();
                res.add(subSum);
                splitArray(nums, i + 1, splitCnt - 1,res);
                res.remove(res.size()-1);
            }
        }
    }


    /**
     *
     * 小套路：如果你发现O(Nlog(N))这样对数的复杂度，一般要往二分查找的方向靠；
     * 二分查找的思路：
     * 反向思考这个问题： 现在题目固定m的值，让我们确定最大子数组和；所谓的方向思考就是说，
     * 我们可以反过来，限制一个最大子数组和max，来反推最大子数组和为max时，至少可以将nums分割成几个子数组；
     *
     * 取值区间[max(nums),sum(nums)]
     *
     */

    private static class MyBinaryMaxSubArrMin implements MaxSubArrMin {

        @Override
        public int splitArray(List<Integer> nums, int m) {
            int left = max(nums),
                    right = sum(nums) + 1; //搜索区间是左闭右开的
            while (left < right) {
                int mid = (left + right) / 2;
                int minPart = getMinPart(nums, mid); //根据分割子数组的个数进行收缩区间
                if (minPart > m) {//最大子数组的上限和低了，导致分割出子数组的个数太多了，需要增加一下
                    left = mid + 1;
                } else if (minPart < m) { //最大子数组的和高了，导致分出来的子数组的个数太少，需要减少些；
                    System.out.println(right + "----<--");
                    right = mid;
                } else {
                    //收缩右边界，达到搜索左边界的目的，
                    System.out.println(right + "---=-----");
                    right = mid;
                }
            }

            return right;
        }

        private int getMinPart (List<Integer> nums,int subMax) {
            int res = 0;
            int tempSum = 0;
            for (Integer num :nums) {
                if (tempSum + num <= subMax) {
                    tempSum += num;
                    continue;
                }
                res++;
                tempSum = num;
            }
            if (tempSum > 0) res++;

            return res;
        }

    }

    private static class FinalBinaryDetailMaxSubArrMin implements MaxSubArrMin {

        @Override
        public int splitArray(List<Integer> nums, int m) {
            int lo = max(nums), hi = sum(nums) + 1;
            for (int max = lo; max < hi; max++) {
                int minPart = getMinPart(nums, max);//如果最大子数组和为max,至少可以将nums拆分成minPart个子数组
                if (minPart <=  m) { //为什么是<= 而不是==
                    return max;
                }
            }
            return -1;
        }

        private int getMinPart (List<Integer> nums,int subMax) {
            int res = 0,tempSum = 0;
            for (Integer num:nums) {
                if (tempSum + num <= subMax) {
                    tempSum += num;
                    continue;
                }
                res++;
                tempSum = num;
            }
            if (tempSum > 0) res++;

            return res;
        }

    }


    /**
     * 各自子数组的最大值的最小值
     */
    private interface MaxSubArrMin {
        int splitArray(List<Integer> nums, int m);
    }

    private static int max (List<Integer> nums) {
        return  nums.stream().mapToInt(num -> num).max().orElse(-1);
    }

    private static int sum (List<Integer> nums) {
        return nums.stream().mapToInt(num -> num).sum();
    }
}
