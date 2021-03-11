package example.algorithm.interview.recursion;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 回溯算法
 * @author: weiliuyi
 * @create: 2021--06 11:20
 **/
public class RecallAlo {


    /**
     * 回溯算法框架
     *
     * def backTrace(路径，选择列表)
     *  if 满足结束条件 ：
     *      result.add(路径);
     *      return;
     *  for 选择 in 选择列表：
     *      做选择
     *      backTrace(路径，选择列表);
     *      撤销选择
     *
     *
     * for 选择 in 选择列表
     *
     *
     *
     * @param args
     */

    public static void main(String[] args) {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        wholeArrange(nums);
    }

    /**
     * 全排列
     */
    private static void wholeArrange (List<Integer> nums) {
        printArray(nums,new ArrayList<>());
    }

    private static void printArray(List<Integer> nums,List<Integer> result) {
        if (result.size() == nums.size()) {
            System.out.println(JSON.toJSONString(result));
            return;
        }
        for(Integer number :  nums) {
            //排除不合法的选择
             if (result.contains(number)) {
                 continue;
             }
             //做选择
             result.add(number);
             //进入下一层决策树
             printArray(nums,result);
             //取消
             result.remove(number);
        }
    }

    /**
     * 8皇后问题
     */

    private static int count = 0;

    private static int[] position = new int[]{-1,-1,-1,-1,-1,-1,-1,-1};
    // position[0] = 1 第0行 第1列 放置一个皇后

    /**
     * 为什么做做成了全排列了
     * @param row
     */
    private void eightQueen (int row) {
        if (row == 8) {
            count++;
            return;
        }

        for (int i = 0;i <= 7 ;i++) {
            if (!isInvalid(row,i)) {
                continue;
            }
            position[row] = i;
            eightQueen(row+1);
            position[row] = -1;
        }

    }

    private static boolean isInvalid (int row,int col) {
        for (int i = 0 ; i < row;i++) {
            //之前出现过列，
            if (position[i] == col) {
                return false;
            }
        }
        //第一条对角线
        //假如4行2列放置一个皇后  5行1列  3行3列 2行2列 1行1列
        //第二条对角线
        //3行1列 5行3列 6行4列 7行5列 8行6列
        for (int i = row -1,j = col+1;i >=0 && j <=7;i--,j++) {
            if (position[i] == j) {
                return false;
            }
        }

        for (int i = row-1,j=col -1;i >= 0 && j >=0;i--,j--) {
            if (position[i] == j) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test () {
        eightQueen(0);
        System.out.println(count);
    }
}
