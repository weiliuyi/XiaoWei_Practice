package example.algorithm.interview.order;


import java.util.Arrays;

/**
 * @author: weiliuyi
 * @create: 2020--18 09:45
 **/
public class ArrayConversion {

    /**
     *   给出一个长度为 n 的数组，和一个正整数 d,你每次可以选择其中任意一个元素 a[i]
     *   将其变为 a[i]   + d 或 a[i]   - d，这算作一次操作
     *
     *   知识点：排序、贪心
     */
    public static void main(String[] args) {
        int dur = 2;
        int[] array = new int[]{3, 5, 1, 7, 9};
        if (isCanResolve(array,dur)) {
            Arrays.sort(array,0,5);
            System.out.println(Arrays.toString(array));
            System.out.println(getStepsCounts(array,dur));
        }
    }


    private static int getStepsCounts (int[] array,int dur) {
        int count = 0;
        int mind = (array.length - 1) / 2;
        for (int i = 0;i < array.length;i++) {
            if (i <= mind) {
                count += (array[mind] - array[i]) / dur;
            } else {
                count += ( array[i] - array[mind]) / dur;
            }
        }
        return count;
    }

    private static boolean isCanResolve(int [] array,int dur) {
        boolean result = true;
        int reminder = array[0] % dur;
        for (int i = 1; i < array.length;i++) {
            if (reminder != array[i] % dur) {
                result = false;
                break;
            }
        }
        return result;
    }

}
