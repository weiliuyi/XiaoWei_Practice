package example.algorithm.interview.day.march;


import org.junit.Test;

import java.util.Arrays;

public class Day0323 {


    /**
     * 查找最长递归子序列
     */

    @Test
    public void testMaxIncreLength() {
        int[] target = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("getMaxIncreLengthByDynamic = " + getMaxIncrementLengthByDynamic(target));

        int[] target2 = new int[]{6, 3, 5, 10, 11, 2, 9, 14, 13, 7, 4, 8, 12};
        System.out.println("target  = " + getMaxIncrementLengthByDynamic(target2));
        System.out.println("getMaxIncrementLengthDynamicV2  = " + getMaxIncrementLengthDynamicV2(target2));
        System.out.println("getMaxIncrementLengthBinarySearch  = " + getMaxIncrementLengthBinarySearch(target2));
        System.out.println("getMaxIncrementLengthBinarySearchV2  = " + getMaxIncrementLengthBinarySearchV2(target2));
    }


    /**
     * 二分查找
     */

    @Test
    public void testBinarySearch() {
        int[] orderedArr = {1, 5, 9, 20, 26, 29, 30};
        System.out.println("index  =" + baseBinarySearch(orderedArr, 26));

        int[] arr = new int[]{1, 2, 2, 2, 3};
        System.out.println("index = " + baseBinarySearch(arr, 2));

        System.out.println("baseLeftBinarySearch =" + baseLeftBinarySearch(arr, 2));

        System.out.println("leftBoundBinarySearch = " + leftBoundBinarySearch(arr, 2));
        System.out.println("leftBoundBinarySearchV2 = " + leftBoundBinarySearchV2(arr, 2));
        System.out.println("rightBoundBinarySearch = " + rightBoundBinarySearch(arr, 2));
    }


    /**
     * 二分查找的拓展，吃香蕉问题
     */
    @Test
    public void testBinarySearchEx() {
        int[] piles = new int[]{3, 6, 7, 11};
        System.out.println(minEatingSpeedForce(piles, 8));
        int[] piles2 = new int[]{30, 11, 23, 4, 20};
        System.out.println(minEatingSpeedForce(piles2, 5));
        System.out.println(minEatingSpeedBinarySearch(piles, 8));
        System.out.println(minEatingSpeedBinarySearch(piles2, 5));

    }

    /**
     * 二分查找的拓展应用，包裹运输问题
     */

    @Test
    public void testBoatCarry() {
        int[] parcels = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(minBoatCarryForce(parcels, 5));
        System.out.println(minBoatBinarySearch(parcels, 5));

    }


    /**
     * 最长子序列的递归算法的解析：
     * 1.状态 数组的下标
     * 2.dp[i]以target[i]为结尾的最长递增序列长度；
     * 3.baseCase,dp数组的下标都为1（最短为1）
     */

    private int getMaxIncrementLengthByDynamic(int[] target) {
        if (target.length == 1) return 1;
        int[] dp = new int[target.length];
        Arrays.fill(dp, 1);
        int max = dp[0];
        /**
         * 逻辑错误，第一个比target[i]小的不一定是dp[i]最大
         */
//        for (int i = 1; i < target.length; i++) {
//            int temp = i - 1;
//            //向左侧找到第一个比target[i]小的角标
//            while (temp >= 0 && target[temp] >= target[i]) temp--;
//            if (temp < 0) continue;
//            dp[i] = dp[temp] + 1;
//            max = Math.max(dp[i], max);
//        }
        /**
         * 所有比它小的最大值（还是有优化的空间的）
         */
        for (int i = 1; i < target.length; i++) {
            int temp = i - 1;
            while (temp >= 0) {
                if (target[temp] < target[i]) {
                    dp[i] = Math.max(dp[i], dp[temp] + 1);
                    max = Math.max(dp[i], max);
                }
                temp--;
            }
        }
        System.out.println("result " + Arrays.toString(dp));
        return max;
        /**
         * 相比于这种方式要优化很多
         * for (int i = 0;i < target.length;i++) {
         *     for (int j = 0;j < i;j++) {
         *         if (target[j] < target[i]) {
         *             dp[i] = Math.max(dp[j] + 1,dp[i]);
         *         }
         *     }
         * }
         */

    }


    private int getMaxIncrementLengthDynamicV2(int[] arr) {
        int result = 1;
        int[] dpTable = new int[arr.length];
        Arrays.fill(dpTable, 1);
        for (int i = 1; i < arr.length; i++) {
            int temp = 1;
            for (int j = 0; j <= i - 1; j++) {
                if (arr[i] <= arr[j]) continue;

                temp = Math.max(temp, dpTable[j] + 1);
            }
            dpTable[i] = temp;
            result = Math.max(result, temp);
        }
        return result;
    }

    /**
     * https://mp.weixin.qq.com/s/02o_OPgePjaz3dXnw9TA1w
     * 最长递归子序列二分查找算法求解  ---patience order
     * <p>
     * 放牌规则：
     * 1。只能把点数小的牌压到比它大的牌上，
     * 2。如果当前点数较大，没有可以放置的点数，则新建一个堆，把这张牌放下去
     * 3。如果当前牌有多个堆可供选择，则选择最右侧的堆放置；
     *
     * @param arr 目标数组
     * @return count
     */
    private int getMaxIncrementLengthBinarySearch(int[] arr) {
        int[] piles = new int[arr.length];
        Arrays.fill(piles, 0);
        for (int i = 0; i < arr.length; i++) {
            //在pile中找到左侧第一个小于arr[i]的下标；
            int left = 0;
            int right = arr.length;
            while (left < right) {
                int mid = (left + right) / 2;
                if (piles[mid] == 0 || piles[mid] >= arr[i]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            piles[left] = arr[i];
        }
        System.out.println("getMaxIncrementLengthBinarySearch" + Arrays.toString(piles));
        return Arrays.stream(piles).map(num -> num != 0 ? 1 : 0).sum();
    }


    /**
     * 思路同上：只是编码方式稍许不同
     */
    private int getMaxIncrementLengthBinarySearchV2(int[] arr) {
        int[] top = new int[arr.length];
        int pileCount = 0;//当前已将创建的堆数

        for (int i = 0; i < arr.length; i++) {
            int poker = arr[i];
            int left = 0, right = pileCount;
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] >= poker) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            if (left == pileCount) pileCount++;
            top[left] = poker;
        }
        return pileCount;
    }


    /*
     * 最长递增子序列，是无法进行暴力求解的，
     */
//    private int getMaxIncrementLengthForce (int[] arr) {
//    }


    /**
     * 二分查找 思路很简单，细节是魔鬼
     *
     * 二分查找框架：
     * int binarySearch (int nums,int target) {
     *     int left = 0,right = ...;
     *     while (...) {
     *         int mid = (left + right) / 2;
     *         if (nums[mid] == target) {
     *             ...
     *         } else if (nums[mid] < target) {
     *             left = ...
     *         } else if (nums[mid] > target) {
     *             right = ...
     *         }
     *     }
     *     return ...;
     * }
     * 二分查找一个技巧：不要出现else,把所有的情况用else if写清楚，这样可以清楚的展现所有的细节
     * ...:可能出现细节问题的地方；
     *
     */


    /**
     * 在有序数组中查找一个数（基本的二分查找）
     */
    public int baseBinarySearch(int[] orderedArr, int target) {

        if (orderedArr == null || orderedArr.length == 0) return -1;

        int left = 0, right = orderedArr.length - 1;//注意点 搜索区间为闭区间 [left,right]

        while (left <= right) { //注意点  循环推出的条件是 left = right + 1
            int mid = (left + right) / 2;
            if (orderedArr[mid] == target) {
                return mid;
            } else if (orderedArr[mid] > target) {
                right = mid - 1; //注意点  orderedArr[mid]已经 参与比较过了
            } else if (orderedArr[mid] < target) {
                left = mid + 1;  //注意点 orderedArr[mid]已经 参与比较过了
            }
        }
        return -1;
    }

    /**
     * 如何在一个有序数组中找到target的最左或者最右角标呢？
     * 思路一：首先找到target的index，然后向角标的左侧或者右侧进行扩展 ---缺点 算法的效率无法保证；
     * 思路二：继续使用二分查找的思想
     */

    /**
     * 思路一的实现方式,先找到targetIndex,然后向左移动索引
     *
     * @param orderedArr 排序之后的数组
     * @param target     目标数组
     */
    private int baseLeftBinarySearch(int[] orderedArr, int target) {
        int left = 0, right = orderedArr.length - 1;
        int targetIndex = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (orderedArr[mid] == target) {
                targetIndex = mid;
                break;
            } else if (orderedArr[mid] < target) {
                left = mid + 1;
            } else if (orderedArr[mid] > target) {
                right = mid - 1;
            }
        }
        /**
         * 这个部分无法保证算法的复杂度---线性
         */
        if (targetIndex == -1) return -1;
        while (targetIndex >= 0 && orderedArr[targetIndex] == target) {
            targetIndex--;
        }
        return ++targetIndex;
    }

    /**
     * 二分查找的思想 寻找左侧边界
     */
    public int leftBoundBinarySearch(int[] orderedArr, int target) {
        int left = 0, right = orderedArr.length;  //搜索区间为开区间[left,right)

        while (left < right) { //循环退出的条件 left == right
            int mid = (left + right) / 2;//此时mid将区间划分为两个左闭右开的区间[left,mid) [mid+1,right);
            if (orderedArr[mid] == target) {
                right = mid;
            } else if (orderedArr[mid] > target) {
                right = mid;
            } else if (orderedArr[mid] < target) {
                left = mid + 1;
            }
        }
        /**
         * 如果在此刻返回的话，含义为orderedArr中小于target元素的个数
         */
        //return left; //left == right 这两者是相同的
        if (left == orderedArr.length) return -1;
        return orderedArr[left] == target ? left : -1;
    }

    public int leftBoundBinarySearchV2(int[] orderedArray, int target) {
        int left = 0, right = orderedArray.length - 1;
        while (left <= right) {
            int mind = (left + right) / 2;
            if (orderedArray[mind] == target) {
                right = mind - 1;
            } else if (orderedArray[mind] > target) {
                right = mind - 1;
            } else if (orderedArray[mind] < target) {
                left = mind + 1;
            }
        }
        //循环退出的条件 left > right
        //right 此时有两种情况 1：指向
        return left < orderedArray.length && orderedArray[left] == target ? left : -1;
    }


    /**
     * 寻找右侧边界的二分查找
     */
    public int rightBoundBinarySearch(int[] orderedArray, int target) {
        int left = 0, right = orderedArray.length;//搜索区间左开右闭[left,right)
        while (left < right) { //循环终止的条件为 left == right
            int mid = (left + right) / 2;
            if (orderedArray[mid] == target) {
                left = mid + 1;
            } else if (orderedArray[mid] > target) {
                right = mid;
            } else if (orderedArray[mid] < target) {
                left = mid + 1;
            }
        }
        //return left -1; 为什么是 left - 1 这是因为循环条件终止的时候，
        // left == right,但是orderedArr[left]不是target（大于target的下一个元素） 产生这种问题的原因是 orderedArr[mid] == target left时,left = mid + 1;
        if (left == orderedArray.length) return -1;
        return orderedArray[left - 1] == target ? left - 1 : -1;
    }

    /*
     * 二分查找的思维拓展，其他应用
     * 当搜索空间有序的时候，就可以通过二分搜索「剪枝」大幅提升效率；
     */


    /**
     * koko（可可）吃香蕉
     * 思考最小和最大的速度是多少？
     * 最大值：香蕉数组的中最大值
     * 最小值：1
     */

    private int minEatingSpeedForce(int[] piles, int hours) {

        if (piles.length > hours) return -1;

        int max = Arrays.stream(piles).max().getAsInt();

        for (int speed = 1; speed <= max; speed++) { //速度从1到max
            if (canFinish(piles, speed, hours))
                return speed;
        }
        return -1;
    }

    private int minEatingSpeedBinarySearch(int[] piles, int hours) {
        if (piles.length > hours) return -1;

        int max = Arrays.stream(piles).max().getAsInt();
        int left = 1;
        int right = max + 1;
        while (left < right) {
            int mind = (left + right) / 2;
            if (canFinish(piles, mind, hours)) {
                right = mind - 1;
            } else {
                left = mind + 1;
            }
        }
        return left + 1;
    }


    private boolean canFinish(int[] piles, int speed, int hour) {
        int hourCount = 0;
        for (int pile : piles)
            hourCount += Math.ceil((double) pile / speed);

        return hourCount <= hour;
    }

    /**
     * 包裹运输问题
     * （https://mp.weixin.qq.com/s/QC24hyg0ZgjR7-LgnEzMYg）
     * <p>
     * 船的最小运载能力是max(carry)
     * 船的最大运输能力是sum(carry);
     */

    private int minBoatCarryForce(int[] parcels, int day) {
        int minCarry = Arrays.stream(parcels).max().getAsInt();
        int maxCarry = Arrays.stream(parcels).sum();
        for (int i = minCarry; i <= maxCarry; i++) {
            if (canBoatFinish(parcels, day, i))
                return i;
        }
        return -1;
    }

    private int minBoatBinarySearch(int[] parcels, int day) {
        int rMax = Arrays.stream(parcels).sum() + 1;
        int lMin = Arrays.stream(parcels).max().getAsInt();
        while (lMin < rMax) {
            int mid = (lMin + rMax) / 2;
            if (canBoatFinish(parcels, day, mid)) {
                rMax = mid;
            } else { //不能完成，说明运载能力太小，需要增大 targetCarry > mid
                lMin = mid + 1;
            }
        }
        return lMin;
    }


    private boolean canBoatFinish(int[] parcels, int day, int carry) {
        int dayCount = 0;
        int carryCount = 0;
        for (int i = 0; i < parcels.length; i++) {
            if (carryCount + parcels[i] > carry) {
                dayCount++;
                carryCount = parcels[i];
            } else if (carryCount + parcels[i] == carry) {
                dayCount++;
                carryCount = 0;
            } else {
                carryCount += parcels[i];
            }
        }
        if (carryCount > 0) dayCount++; //这个要注意；
        return dayCount <= day;
    }
}
