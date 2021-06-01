package example.algorithm.interview.day.may;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName Day0525 回溯算法解决  排列/组合/子集问题
 * @Description
 * @Author weiliuyi
 * @Date 2021/5/31 7:46 下午
 **/
public class Day0525 {

    @Test
    public void testSubCollection() {
        SubCollection recSubCollection = new RecursiveSubCollection();
        int[] arr = {1, 2, 3};
        List<Integer[]> recRes = recSubCollection.subCollectionList(arr);
        recRes.forEach(array -> System.out.println(Arrays.toString(array)));

        System.out.println("---------------------------");

        SubCollection backTraceSubCollection = new BackTraceSubCollection();
        List<Integer[]> backTrackRes = backTraceSubCollection.subCollectionList(arr);
        backTrackRes.forEach(array -> System.out.println(Arrays.toString(array)));
    }


    /**
     * 使用回溯算法求解子集问题
     */
    private static class BackTraceSubCollection implements SubCollection {

//        @Override
//        public List<int[]> subCollectionList(int[] arr) {
//            List<int[]> res = new ArrayList<>();
//            subCollection(arr, 0, new int[0], res);
//            return res;
//        }
//
//        private void subCollection(int[] arr, int startIndex, int[] path, List<int[]> res) {
//            res.add(path);
//            for (int i = startIndex; i < arr.length; i++) {
//                int[] tempArr = new int[path.length + 1];
//                tempArr[0] = arr[i];
//                System.arraycopy(path, 0, tempArr, 1, path.length);
//                subCollection(arr, startIndex + 1, tempArr, res);
//            }
//        }

        @Override
        public List<Integer[]> subCollectionList(int[] arr) {
            List<Integer[]> res = new ArrayList<>();
            Stack<Integer> path = new Stack<>();
            res.add(new Integer[0]);
            subCollection(arr, 0,path , res);
            return res;
        }

        private void subCollection(int[] arr, int startIndex, Stack<Integer> path, List<Integer[]> res) {
            if (startIndex >= arr.length) {
                return;
            }

            for (int i = startIndex; i < arr.length ; i++) {
                path.add(arr[i]);
                res.add(path.toArray(new Integer[0]));
                subCollection(arr,i+1,path,res);
                path.pop();
            }
        }


    }


    /**
     * 使用递归的思维解决方式: 假设我们知道规模更小的子问题的结果，如何推导出当前问题的结果呢？
     * 例如求解[1,2,3]的子集，如果知道[1,2]的子集，是否可以推导出[1,2,3]的子集
     * <p>
     * [1,2]的子集：[][1][2][1,2]
     * [1,2,3]的子集：[][1][2][1,2]   [3] [1,3],[2,3],[1,2,3]
     * <p>
     * 在[1,2]的子集的基础上增加3元素，并且连同[1,2]的所有子集
     */
    private static class RecursiveSubCollection implements SubCollection {

        @Override
        public List<Integer[]> subCollectionList(int[] arr) {
            return subCollection(arr, 0);
        }

        private List<Integer[]> subCollection(int[] arr, int startIndex) {
            List<Integer[]> res = new ArrayList<>();
            if (startIndex == arr.length) {
                res.add(new Integer[0]);
                return res;
            }

            List<Integer[]> tempRes = subCollection(arr, startIndex + 1);
            for (Integer[] subTemp : tempRes) {
                res.add(subTemp);
                if (subTemp.length == 0) {
                    res.add(new Integer[]{arr[startIndex]});
                    continue;
                }
                Integer[] tempArr = new Integer[subTemp.length + 1];
                tempArr[0] = arr[startIndex];
                System.arraycopy(subTemp, 0, tempArr, 1, subTemp.length);
                res.add(tempArr);
            }
            return res;
        }
    }


    /**
     * 求子集问题
     */
    private interface SubCollection {
        List<Integer[]> subCollectionList(int[] arr);
    }
}
