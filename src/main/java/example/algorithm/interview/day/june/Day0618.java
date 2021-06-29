package example.algorithm.interview.day.june;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;


/**
 * @ClassName Day0618 解开密码锁的最少次数
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485134&idx=1&sn=fd345f8a93dc4444bcc65c57bb46fc35&chksm=9bd7f8c6aca071d04c4d383f96f2b567ad44dc3e67d1c3926ec92d6a3bcc3273de138b36a0d9&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/23 12:23 下午
 **/
public class Day0618 {

    /**
     * BFS核心就是状态，从一个状态，到另外一个状态
     * 第一步：有哪些状态
     * 第二步：在第一步的基础上会产生哪些状态
     */
    @Test
    public void testBFS() {
        SecreteBFS<String> myBFS = new MySecreteBFS();
        SecreteBFS<String> finalBFS = new FinalSecreteBFS();
        DoubleSecreteBFS doubleBFS = new DoubleSecreteBFS();
        Set<String> bans = new HashSet<>();
        bans.add("8888");
        System.out.println(myBFS.minSecreteSteps("0009", bans));
        System.out.println(finalBFS.minSecreteSteps("0009", bans));
        System.out.println(doubleBFS.minSecreteSteps("0009", bans));

        System.out.println("------------------");
        Set<String> ban2 = new HashSet<>();
        ban2.add("8887");
        ban2.add("8889");
        ban2.add("8878");
        ban2.add("8898");
        ban2.add("8788");
        ban2.add("8988");
        ban2.add("7888");
        ban2.add("9888");
        System.out.println(myBFS.minSecreteSteps("8888", ban2));
        System.out.println(finalBFS.minSecreteSteps("8888", ban2));
        System.out.println(doubleBFS.minSecreteSteps("8888", ban2));

        System.out.println("----------------");
        Set<String> ban3 = new HashSet<>();
        ban3.add("0201");
        ban3.add("0101");
        ban3.add("0102");
        ban3.add("1212");
        ban3.add("2002");
        System.out.println(myBFS.minSecreteSteps("0202", ban3));
        System.out.println(finalBFS.minSecreteSteps("0202", ban3));
        System.out.println(doubleBFS.minSecreteSteps("0202", ban3));
    }

    /**
     * 双向BFS：
     * 传统的BFS框架就是从起点开始向四周扩散，遇到终点时就停止，而双向BFS则是从起点和终点同时开始扩散，当两边有交集的时候停止
     *
     * 为什么要有这么一个优化呢？
     * 1。因为按照BFS的逻辑，队列集合中的元素越多，扩散之后新的队列的元素越多，
     * 双向BFS算法中，如果我们每次都选择一个较小的集合进行扩散，那么占用的空间增长会慢一点，效率会高一点
     * 无论传统的BFS还是双向的BFS，无论做不做优化，从时间复杂度的标准来看，空间复杂度是一样的，只能说双向BFS是一种trick;
     */

    private static class DoubleSecreteBFS implements SecreteBFS<String> {

        @Override
        public int minSecreteSteps(String target, Set<String> bans) {
            String start = "0000";
            Set<String> q1 = new HashSet<>(), q2 = new HashSet<>(), visited = new HashSet<>();
            q1.add(start);
            q2.add(target);
            visited.add(start);
            visited.add(target);
            int steps = 0;
            while (!q1.isEmpty() && !q2.isEmpty()) {
                Set<String> tempSet = new HashSet<>();

                for (String tempStr : q1) {
                    if (q2.contains(tempStr)) return steps;

                    if (bans.contains(tempStr)) continue;

                    visited.add(tempStr); // 访问的时机？？？？？？？？？？？？？出队列的记录访问
                    //这是因为在

                    for (int i = 0; i < 4; i++) {
                        String addOne = addOne(tempStr, i), minusOne = minusOne(tempStr, i);
                        if (!visited.contains(addOne)) {
                            tempSet.add(addOne);
                            //visited.add(addOne);
                        }

                        if (!visited.contains(minusOne)) {
                            tempSet.add(minusOne);
                            //visited.add(minusOne);
                        }
                    }
                }
                q1 = q2;
                q2 = tempSet;
                steps++;
            }
            return -1;
        }

        private String addOne(String str, int j) {
            char[] arr = str.toCharArray();
            if (arr[j] == '9') {
                arr[j] = '0';
            } else {
                arr[j] += 1;
            }
            return String.valueOf(arr);
        }

        private String minusOne(String str, int j) {
            char[] arr = str.toCharArray();
            if (arr[j] == '0') {
                arr[j] = '9';
            } else {
                arr[j] -= 1;
            }
            return String.valueOf(arr);
        }


    }


    /**
     * 示例：
     * 初始：0000
     */
    private static class MySecreteBFS implements SecreteBFS<String> {
        @Override
        public int minSecreteSteps(String target, Set<String> bans) {
            int[] start = {0, 0, 0, 0};
            Queue<int[]> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>(); // 防止走回头路
            int steps = 0;
            queue.add(start);
            visited.add(convert2String(start));
            while (!queue.isEmpty()) {
                int qz = queue.size();
                for (int i = 0; i < qz; i++) {
                    int[] tempArr = queue.remove();
                    if (StringUtils.equals(convert2String(tempArr), target)) {
                        return steps;
                    }
                    for (int j = 0; j < 4; j++) {
                        int[] addArr = addOne(tempArr, j), minusArr = minusOne(tempArr, j);

                        String addStr = convert2String(addArr), minusStr = convert2String(minusArr);

                        if (!bans.contains(addStr) && !visited.contains(addStr)) {
                            visited.add(addStr);
                            queue.add(addArr);
                        }
                        if (!bans.contains(minusStr) && !visited.contains(minusStr)) {
                            visited.add(minusStr);
                            queue.add(minusArr);
                        }
                    }
                }
                steps++;
            }
            return -1;
        }
    }


    /**
     * 最大的区别就是 bans的处理时机不同
     */
    private static class FinalSecreteBFS implements SecreteBFS<String> {
        @Override
        public int minSecreteSteps(String target, Set<String> bans) {
            Queue<int[]> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            int[] start = {0, 0, 0, 0};
            int step = 0;
            queue.offer(start);
            visited.add(convert2String(start));
            while (!queue.isEmpty()) {
                int qz = queue.size();
                for (int i = 0; i < qz; i++) {
                    int[] tempArr = queue.remove();
                    String tempStr = convert2String(tempArr);
                    if (StringUtils.equals(tempStr, target)) {
                        return step;
                    }

                    if (bans.contains(tempStr)) continue;

                    for (int j = 0; j < 4; j++) {
                        int[] addOne = addOne(tempArr, j), minusOne = minusOne(tempArr, j);
                        String addStr = convert2String(addOne), minusStr = convert2String(minusOne);
                        if (!visited.contains(addStr)) {
                            queue.add(addOne);
                            visited.add(addStr);
                        }
                        if (!visited.contains(minusStr)) {
                            queue.add(minusOne);
                            visited.add(minusStr);
                        }
                    }
                }
                step++;
            }
            return -1;
        }
    }


    /**
     * 密码的最少的步数
     */
    private interface SecreteBFS<T> {

        int minSecreteSteps(T target, Set<String> bans);

        default int[] addOne(int[] nums, int i) {
            int[] newArr = Arrays.copyOf(nums, nums.length);
            if (newArr[i] == 9) {
                newArr[i] = 0;
            } else {
                newArr[i] += 1;
            }
            return newArr;
        }

        default int[] minusOne(int[] nums, int i) {
            int[] newArr = Arrays.copyOf(nums, nums.length);
            if (newArr[i] == 0) {
                newArr[i] = 9;
            } else {
                newArr[i] -= 1;
            }
            return newArr;
        }

        default String convert2String(int[] nums) {
            StringBuilder res = new StringBuilder();
            for (int num : nums) {
                res.append(num);
            }
            return res.toString();
        }
    }
}
