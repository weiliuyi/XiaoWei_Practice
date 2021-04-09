package example.algorithm.interview.day.march;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--13 10:40
 **/
public class Day0313 {
    /**
     * 给你一个整数数组arr，请你帮忙统计数组中每个数的出现次数。
     * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
     */

    @Test
    public void testSpecial() {
        Integer[] test1 = new Integer[]{1, 2, 2, 1, 1, 3};
        System.out.println(checkSpecial(test1));
        Integer[] test2 = new Integer[]{1, 2};
        System.out.println(checkSpecial(test2));

    }

    /**
     * 暴力求解 ---也是高效的集合
     */
    private boolean checkSpecial(Integer[] array) {
        //统计每个数字的次数
        Map<Integer, Long> numCountMap = Arrays.stream(array).collect(groupingBy(Function.identity(), counting()));
        Set<Long> countSet = new HashSet<>();
        //判断出现的次数是否重复 version1
        for (Map.Entry<Integer, Long> entry : numCountMap.entrySet()) {
            if (countSet.contains(entry.getValue())) {
                return false;
            }
            countSet.add(entry.getValue());
        }
        return true;

        /**
         * version2 ,
         * 如果出现重复，那么一定会出现value去重之后的结果 小于去重之前的结果
         *   numCountMap.size = new HashSet(numCountMap.values).size
         *
         * version3
         * for (Map.Entry<Integer,Long> entry :numCountMap.entrySet()) {
         *     if (!countSet.add(entry.value)) {
         *         return false;
         *     }
         * }
         * return true;
         *
         */

    }


    /**
     * 请找出一种能使用所有火柴拼成一个正方形的方法。不能折断火柴
     * ，可以把火柴连接起来，并且每根火柴都要用到。
     */
    @Test
    public void test3() {
        List<Integer> stickLengthList = Arrays.asList(3, 3, 3, 3, 4);
        System.out.println(makeSquareFir(stickLengthList));

        System.out.println(makeSquare(new int[] {1,1,2,2,2}));
    }



    private boolean makeSquareFir (List<Integer> stickLengthList) {

        stickLengthList.sort((n1, n2) -> -(n1 - n2));
        int sum = stickLengthList.stream().mapToInt(num -> num).sum();
        System.out.println(JSON.toJSONString(stickLengthList) + " " + sum);
        if (sum % 4 != 0) {
            System.out.println("不是4的倍数");
            return false;
        }
        if (stickLengthList.get(0) > sum / 4) {
            System.out.println("火柴的长度过长");
            return false;
        }
        int[] chooseFlag = new int[stickLengthList.size()];
        return checkRectangle(stickLengthList, chooseFlag, sum / 4, 0, 0);
    }


    /**
     * 判断是否能够拼成正方形
     * @param stickLengthList 火柴的边长列表
     * @param chooseFlag 边长的标志位
     * @param length 拼成正方形的边长
     * @param num 已经拼成的边数
     * @param sideLength 此时已经拼成的变数；
     * @return
     */
    private boolean checkRectangle(List<Integer> stickLengthList, int[] chooseFlag, int length, int num, int sideLength) {
        if (num == 4) {  //退出的条件  如果此时已经有四条边都已经拼成；
            System.out.println("over");
            return true;
        }

        for (int i = 0; i < stickLengthList.size(); i++) {
            //判断这根火柴，是否已经选择过，如果已经选择过，跳过 2.进行剪枝
            if (chooseFlag[i] == 1 || (sideLength + stickLengthList.get(i)) > length) {
                continue;
            }
            //进行选择，设置标记位
            chooseFlag[i] = 1;
            sideLength += stickLengthList.get(i);
            if (sideLength == length) {
                sideLength = 0;
                num++;
            }
            boolean result = checkRectangle(stickLengthList, chooseFlag, length, num, sideLength);
            //如果找到一条拼成正方形的方法，就进行推出
            if (result) {
                return true;
            }

            //撤销选择，进行取消
            chooseFlag[i] = 0;
            if (sideLength == 0) {
                num--;
                sideLength = length - stickLengthList.get(i);
            } else {
                sideLength -= stickLengthList.get(0);
            }
        }
        return false;
    }

    public boolean makeSquare(int[] nums) {
        int total = Arrays.stream(nums).sum();
        if (total == 0 || (total & 3) != 0) {
            return false;
        }
        return backTrack(nums, 0, total >> 2, new int[4]);
    }

    /**
     * @param nums   火柴的边长数组
     * @param index  访问到当前火柴的位置
     * @param target 正方形的边长
     * @param flag   长度位4的数组，分别保存四个边长的长度
     * @return 这个算法存在的缺点：
     * 如果前边的数比较小，会导致递归的深度比较深，
     * <p>
     * 解决方案：从大的开始递归；
     * 剪枝：flag[i] == flag[i-1] 上一个分支没有成功，这一个分支也不会成功
     *
     * 课后联系
     */
    private boolean backTrack(int[] nums, int index, int target, int[] flag) {
        if (index == nums.length) { //验证所有的火柴是否已经访问结束
            //如果flag的4个边长都和正方形的边长相等， 返回true,否则 返回false
            return Arrays.stream(flag).allMatch(num -> num == target);
        }
        //到这一步火柴没有访问
        for (int i = 0; i < flag.length; i++) {
            //当前火柴放到size[i]这个边上，他的长度大于target,直接跳过
            //对选择进行判断
            if (flag[i] + nums[index] > target) {
                continue;
            }
            //当前火柴放入到flag[i],长度不大于target,
            flag[i] += nums[index];
            //进行放入下一根火柴，如果最终能成为正方形，直接返回true
            if (backTrack(nums, index + 1, target, flag)) {
                return true;
            }
            //撤销选择
            //如果放到当前flag[i]这个边上，最终不能构成正方形，我们就将这跟火柴从flag[i]上边移除； 然后试试其他边
            flag[i] -= nums[index];
        }
        return false;

    }

}
