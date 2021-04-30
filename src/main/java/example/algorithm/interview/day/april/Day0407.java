package example.algorithm.interview.day.april;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Day0407 转盘游戏
 * @Description https://mp.weixin.qq.com/s/-6msFdfmNgvU2KI5IxGm0A
 * @Author weiliuyi
 * @Date 2021/4/30 9:52 上午
 **/
public class Day0407 {


    /**
     * 转盘游戏
     */
    @Test
    public void testFindRotateSteps() {
        String ring = "godding";
        String key = "gd";
        System.out.println(findRotateStep(ring, 0, key, 0));
        System.out.println(rotateStep(ring,key));
    }


    /**
     * 动态规划
     * 目标是求：转盘转到所需字符串的所需要的最少次数
     * 1。状态是什么
     * 当前输入的字符，和当前圆盘的位置
     * 1.dp的含义：
     * dp[i][j]:当圆盘指针指向ring[i]时，输入字符串k[j...]至少需要dp[i][j]次操作；
     * <p>
     * 2.选择 顺时针 or 逆时针 选择目标字母
     *
     *
     * TODO  这是有问题的？？？？？ 顺指针第一个   逆时针的第一个字母  如果在中间求的最大值呢？？？？
     * 思维逻辑有问题：：：：：：：：
     *
     * 应该是针对每一个元素取 顺时针 或者 逆时针最小的方式进行；
     *
     */
    private int findRotateStep(String ring, int i, String key, int j) {
        if (j == key.length()) return 0; //说明已经到了字符串的末尾

        //顺时针
        int k = i;
        while (ring.charAt(k) != key.charAt(j)) {
            k = (k + 1) % ring.length();
        }
        int step1 = k < i ? (k + ring.length() - i) : (k - i);
        //逆时针
        int l = i;
        while (ring.charAt(l) != key.charAt(j)) {
            l = (l + ring.length() - 1) % ring.length();
        }
        int step2 = l <= i ? (i - l) : (i + ring.length() - l);

        return Math.min(step1 + findRotateStep(ring, k, key, j + 1),
                step2 + findRotateStep(ring,l,key,j+1 )) + 1;
    }


    private int rotateStep (String ring,String key) {
        Map<Character, List<Integer>> charIndexListMap = new HashMap<>();
        for (int i = 0; i < ring.length(); i++) {
            Character c = ring.charAt(i);
            if (charIndexListMap.containsKey(c)) {
                charIndexListMap.get(c).add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                charIndexListMap.put(c,list);
            }
        }
        return findRotateStep(charIndexListMap,ring,0,key,0);
    }


    private int findRotateStep (Map<Character, List<Integer>> ringMap,
                                String ring,int i,String key,int j) {
        if (j == key.length()) return 0;

        int n = ring.length();

        int res = Integer.MAX_VALUE;
        for (int k : ringMap.get(key.charAt(j))) {
            //波动指针的次数
            int delta = Math.abs(k-i);
            // 顺指针 还是 逆时针
            delta = Math.min(delta,n-delta);
            //将指针拨到ring[k],继续输入key[j+1,....]
            int subProblem = findRotateStep(ringMap,ring,k,key,j+1);
            // 选择整体操作数最少
            res = Math.min(res,1 + delta + subProblem);
        }

        return res;
    }

}
