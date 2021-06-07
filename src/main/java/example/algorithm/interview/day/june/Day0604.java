package example.algorithm.interview.day.june;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Day0604 分治--添加括号的所有方式
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247488970&idx=1&sn=d4eb6a371f1706d76e370be18b27afb4&chksm=9bd7ebc2aca062d4e9d62bb363a1e386cc5b42224e63c505f902275c48f03fd8f8289b717fb2&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/7 4:26 下午
 **/
public class Day0604 {

    /**
     * 添加括号的所有方式
     *
     * 给定一个含有数组运算符的字符串，为表达式添加括号，改变其运算优先级以求出不同的结果；
     *
     * 完全没有思路，超级复杂
     * 1。穷举所有的加括号的方式
     * 2。括号的优先级
     * 3。计算加括号之后的表达式
     *
     * 将问题转化为另外一种形式---操作符
     *
     *
     *
     * 1。不要思考整体，而是把目光聚集在局部，只看一个运算符；
     * 2。明确递归函数的定义是什么，相信并且利用好递归函数来解决；
     */
    @Test
    public void testDiffWays2Compute() {
        String input = "2*3-4*5";
        Ways2ComputeImpl ways2ComputeImpl = new Ways2ComputeImpl();
        List<Integer> res = ways2ComputeImpl.diffWays2Compute(input);
        System.out.println(JSON.toJSONString(res));
    }

    private class Ways2ComputeImpl implements Ways2Compute{
        @Override
        public List<Integer> diffWays2Compute(String input) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < input.length(); i++) {
                char operator = input.charAt(i);
                if (operator != '-' && operator != '*' && operator != '+') continue;


                String left = input.substring(0,i);
                String right = input.substring(i+1);
                List<Integer> lRes = diffWays2Compute(left);
                List<Integer> rRes = diffWays2Compute(right);
                lRes.forEach(l -> {
                    switch (operator) {
                        case '-' :
                            rRes.forEach( r -> res.add(l-r));
                            break;
                        case '+':
                            rRes.forEach(r -> res.add(l+r));
                            break;
                        case '*':
                            rRes.forEach(r -> res.add(l * r));
                            break;
                    }
                });
            }
            //递归的出口 如果input中不存在操作符，res 为null
            if (res.isEmpty()) {
                res.add(Integer.parseInt(input));
            }
            return res;
        }
    }


    private interface Ways2Compute {
        /**
         * 计算所有算式所有可能的结果
         */
        List<Integer> diffWays2Compute(String input);
    }

}
