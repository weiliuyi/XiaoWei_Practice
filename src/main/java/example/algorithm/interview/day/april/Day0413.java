package example.algorithm.interview.day.april;

import org.junit.Test;

/**
 * @ClassName Day0413
 * @Description
 * @Author weiliuyi
 * @Date 2021/4/13 5:08 下午
 **/
public class Day0413 {


    @Test
    public void testForKey() {
        System.out.println(getMaxA(7, 0, 0));
        System.out.println(getMaxMemo(7, 0, 0,new int[100][100][100]));
        System.out.println(getMaxAVersion2(7));
    }


    /**
     * 动态规划：不同的定义产生不同的解法；
     * https://mp.weixin.qq.com/s/DeanOw0acBNU1ZoI4cE8nw
     * 四键键盘的问题
     *
     * 动态规划问题：首先明确：选择，状态
     * 选择：A  ctrl-a  ctrl-c ctrl-v
     * 状态：需要什么信息才能把原问题拆分成规模更小的子问题？？？？？
     *      1。剩余的按键次数，n
     *      2。当前屏幕上A的数量 a_num
     *      3。剪切板中字符A的数量，copy
     *      剩余0次的时候，a_num就是我们要的答案
     * 根据选择把状态的转移表示出来：
     *
     * 按下A键,屏幕增加一个字符，同时消耗一个操作数；
     * dp (n-1,a_num+1,copy);
     *
     * 按下ctrl-v，剪切板中的字符加入屏幕中，同时消耗一个操作数
     * dp(n-1,a_num+copy,copy);
     *
     * 全选和复制必然是联合使用的，
     * ctrl+a ctrl-c 按下，剪切板中的A的数据变为屏幕上A的数量，同时消耗两个操作数
     * dp(n-2,a_num,a_num);
     */

    /**
     * 递归的方式求解：
     */
    private int getMaxA(int n, int aNum, int copy) {
        if (n <= 0) return aNum;
        //A操作
        int op1 = getMaxA(n - 1, aNum + 1, copy);
        //ctrl + v
        int op2 = getMaxA(n - 1, aNum + copy, copy);
        //ctrl+c ctrl + v
        int op3 = getMaxA(n - 2, aNum, aNum);
        return Math.max(Math.max(op1, op2), op3);
    }

    /**
     * 使用备忘录进行求解 优化
     */

    private int getMaxMemo (int n,int aNum,int copy,int[][][] memo) {
        if (n <= 0) return aNum;
        if (memo[n][aNum][copy] != 0) return memo[n][aNum][copy];

        //A操作
        int op1 = getMaxMemo(n - 1, aNum + 1, copy,memo);
        //ctrl + v
        int op2 = getMaxMemo(n - 1, aNum + copy, copy,memo);
        //ctrl+c ctrl + v
        int op3 = getMaxMemo(n - 2, aNum, aNum,memo);

        return Math.max(Math.max(op1, op2), op3);
    }

    /**
     * 第二种思路：
     *  只是定义一个状态：
     *  dp[i]表示i次操作之后最多显示多少个A
     *  深入了解问题：
     *  1。要么一直按：AAAAAAAA
     *  2。要么是：A，A，A，。。。。。 ctrl + a ,ctrl + c,ctrl + v,ctrl + v ....... ctrl + a ,ctrl + c,ctrl + v,ctrl + v...
     *
     *  这是因为开始时：n较小，ctrl 。。。这一套的操作太大，还不如一直按A
     *  当N比较大时，ctrl + v的操作收获比较大
     *
     * 《《《《《《不明白为什么会是这样子》》》》》》》》》dp[i] 表示第i次操作最多可以显示A的数量；
     *  结尾必须是 a ctrl+v结尾的
     *
     *  总结：最后一次按键：要么是A，要么是ctrl+v操作 j表示第一个ctrl+v的前一个角标位置；
     * v    a   c   v
     * a    a   a   a      。。。        a
     * j-2      j                       i  总共按了i-j次ctrl+v 屏幕共有dp[j-2] * (j - i + 1)字符
     *
     * 方式二  假设所有的结尾都是已ctrl+v结尾的；  j表示第一个ctrl+v起点的角标；
     * v    a   c   v
     * a    a   a   a      。。。        a
     * j-3          j                   i  总共按了i-j+1次ctrl+v 屏幕共有dp[j-3] * (j - i + 2)字符
     *
     */

    private int getMaxAVersion2 (int n) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + 1;//按一个A
            for (int j = 2; j < i ; j++) {
                //全选 复制 dp[n-2] 连续粘贴 i-j次
                dp[i]  = Math.max(dp[i],dp[j -2] * (i - j + 1));
            }
        }
        return dp[n];
    }

}
