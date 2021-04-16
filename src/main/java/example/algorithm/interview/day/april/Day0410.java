package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName Day0410 贪心算法---区间调度
 * @Description https://mp.weixin.qq.com/s/NH8GFMcRm5UK0HmVhdNjMQ
 * @Author weiliuyi
 * @Date 2021/4/15 7:27 下午
 **/
public class Day0410 {

    @Test
    public void testInterval() {
        List<Internal> intervalList = Arrays.asList(new Internal(1, 3), new Internal(2, 4), new Internal(3, 6));
        System.out.println(internalSchedule(intervalList));
    }

    /**
     * 算出这些区间中，最多有几个互不相交的区间
     * 使用贪心算法：
     * 1。每次选择最早的开始，错误，特例：假如最早开始的时间跨度很大
     * 2。选择时间跨度最小  错误，特例：最小时间跨度的横跨了两个区间；
     * 3。结束时间最早，----------正确，这保证最大的剩余时间
     */

    private List<Internal> internalSchedule(List<Internal> internalList) {
        //对区间根据结束时间进行排序
        List<Internal> orderedInternals = internalList.stream().
                sorted(Comparator.comparingInt((Internal inter) -> inter.end)).
                collect(toList());
        List<Internal> result = new ArrayList<>();
        for (Internal internal : orderedInternals) {
            if (result.size() == 0) { //如果是第一个interval,直接添加
                result.add(internal);
                continue;
            }
            Internal beforeInterval = result.get(result.size() - 1);
            if (internal.start < beforeInterval.end) { //获取上一个interval,如果发生交叉
                continue;
            }
            result.add(internal);
        }
        return result;
    }

    static class Internal {
        int start;
        int end;

        public Internal(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Internal{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }
}
