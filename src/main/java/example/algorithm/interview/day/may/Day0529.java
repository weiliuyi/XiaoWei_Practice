package example.algorithm.interview.day.may;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName Day0529 合法括号的生成  回溯算法的最佳实践
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247485115&idx=1&sn=dd64bfedb1da22f308228a0933583adf&chksm=9bd7f8b3aca071a5b96e7cb9464c01c045997d36d677b14163b6b009df2aa9b1b613ace3bc5a&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/2 12:41 下午
 **/
public class Day0529 {


    /**
     * 括号问题分为两类：一类判断括号的合法性 一类是合法括号的生成
     *
     * 对于判断括号的合法性，主要借助栈的数据结构，
     * 对于括号的生成，一般都是利用回溯递归的思想；
     *
     * 输入一个正整数n，输出n对括号的所有合法组合
     *
     *
     * 性质：
     * 1。一个「合法」的括号组合的左括号数量一定等于右括号数量
     * 2。对于一个「合法」的括号组合p，必然对于任何的0 <= i < len(p)都有：字串p[0...i]中左括号的数量大于或等于有括号的数量；
     *
     */
    @Test
    public void testBracket() {
        GenerateParenthesis myGenerate = new MyGenerateParenthesisImpl();
        List<String> myRes = myGenerate.generateParenthesis(3);
        System.out.println(JSON.toJSONString(myRes));
        System.out.println("----------------------------------");
        GenerateParenthesis finalRes = new FinalGenerateParenthesis();
        System.out.println(finalRes.generateParenthesis(3));
    }

    private static class FinalGenerateParenthesis implements GenerateParenthesis {
        @Override
        public List<String> generateParenthesis(int n) {
            if (n == 0) {
                return Collections.emptyList();
            }
            List<String> res = new ArrayList<>();
            parenthesis(n,n,"",res);
            return res;
        }

        /**
         * @param left 代表剩余的左侧括号数量
         * @param right 代表剩余右侧的括号的数量
         * @param path 当前的字符串
         * @param res 结果集合
         */
        private void parenthesis (int left,int right,String path,List<String> res) {
            if (left > right) return;
            if (left < 0 || right < 0) return;
            if (left == 0 && right == 0) {
                res.add(path);
                return;
            }
            path = path + "(";
            parenthesis(left-1,right,path,res);
            path = path.substring(0,path.length()-1);

            path = path + ")";
            parenthesis(left,right-1,path,res);
            path = path.substring(0,path.length()-1);

        }
    }


    private static class MyGenerateParenthesisImpl implements GenerateParenthesis {

        @Override
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            parenthesis(n,0,0,res,"");
            return res;
        }

        private void  parenthesis (int n,int leftCount,int rightCount, List<String> res, String path) {
            if (path.length() == 2 * n) {
                res.add(path);
                return;
            }
            for (int i = 0; i < 2; i++) {
                if (i == 0 && leftCount < n) { //左括号
                    path = path+ "(";
                    parenthesis(n,leftCount+1,rightCount,res,path);
                    path = path.substring(0,path.length()-1);
                }

                if (i == 1  && leftCount > rightCount) { //保证左括号的数量大于右括号的数量
                    path = path + ")";
                    parenthesis(n,leftCount,rightCount+1,res,path);
                    path = path.substring(0,path.length()-1);
                }
            }

        }
    }

    /**
     * 生成合法括号的接口累
     */
    private interface GenerateParenthesis {
        List<String> generateParenthesis (int n);
    }
}
