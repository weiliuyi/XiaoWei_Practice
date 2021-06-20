package example.algorithm.interview.day.june;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Day0613 判定子序列
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247484479&idx=1&sn=31a3fc4aebab315e01ea510e482b186a&chksm=9bd7fa37aca0732103ca82e6f2cc23f475cf771696958456fc17d7662abb6b0879e8dfbaf7a1&scene=178&cur_album_id=1318883740306948097#rd
 * @Author weiliuyi
 * @Date 2021/6/15 7:38 下午
 **/
public class Day0613 {

    @Test
    public void testSubsequence() {
        String content = "cacbhbc", target = "abc";
        StrSubsequence<String, Boolean> forceSubsequence = new ForceSubsequence();
        System.out.println(forceSubsequence.isSubsequence(content, target));

        System.out.println("------------------------");
        OptimizeSubsequence optSubsequence = new OptimizeSubsequence();
        System.out.println(optSubsequence.isSubsequence(content, target));

        System.out.println("----------------------");
        MySubsequence mySubsequence = new MySubsequence();
        System.out.println(mySubsequence.isSubsequence(content,target));
        System.out.println("---------------");
        String[] targetArr = {"abc","acf","gg","chc"};
        FinalSubSequence finalSequence = new FinalSubSequence();
        Boolean[] finalRes = finalSequence.isSubsequence(targetArr, content);
        System.out.println(JSON.toJSONString(finalRes));
    }

    private static class FinalSubSequence implements StrSubsequence<String[], Boolean[]> {
        @Override
        @SuppressWarnings("unchecked")
        public Boolean[] isSubsequence(String[] targetArr, String content) {
            ArrayList<Integer>[] contentDic = new ArrayList[256];
            for (int i = 0; i < content.length(); i++) {
                if (contentDic[content.charAt(i)] == null) {
                    contentDic[content.charAt(i)] = new ArrayList<>();
                }
                contentDic[content.charAt(i)].add(i);
            }
            Boolean[] res = new Boolean[targetArr.length];
            for (int i = 0; i < targetArr.length; i++) {
                String str = targetArr[i];
                int k = 0;
                boolean subRes = Boolean.TRUE;
                for (int j = 0; j < str.length(); j++) {
                    ArrayList<Integer> posList = contentDic[str.charAt(j)];
                    if (posList == null) {
                        subRes = Boolean.FALSE;
                        break;
                    }
                    int pos = leftBound(posList,k);
                    if (pos == posList.size()) {
                        subRes = Boolean.FALSE;
                        break;
                    }
                    k = posList.get(pos) + 1;
                }
                res[i] = subRes;
            }
            return res;
        }

        private int leftBound (List<Integer> posList,int target) {
            int left = 0,right = posList.size();
            while (left < right) {
                int mid = (left + right) / 2;
                if (posList.get(mid)>= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }


    /**
     * 使用二分查找进行优化
     */
    private static class OptimizeSubsequence implements StrSubsequence<String, Boolean> {
        @Override
        @SuppressWarnings("unchecked")
        public Boolean isSubsequence(String content, String target) {
            ArrayList<Integer>[] contentDic = new ArrayList[256];
            for (int i = 0; i < content.length(); i++) {
                if (contentDic[content.charAt(i)] == null)
                    contentDic[content.charAt(i)] = new ArrayList<>();

                contentDic[content.charAt(i)].add(i);
            }

            int j = 0;
            for (int i = 0; i < target.length(); i++) {
                if (contentDic[target.charAt(i)] == null) return false;

                ArrayList<Integer> charIndexList = contentDic[target.charAt(i)];

///                int left = 0,right = charIndexList.size();
///                while (left < right) {
//                    int mid = (left + right) / 2;
//                    if (charIndexList.get(mid) >= j) {
//                        right = mid;
//                    } else {
//                        left = mid+1;
//                    }
//                }
///
///                //right==left 不存在大于下标j的元素；
// /               if (right == charIndexList.size()) return false;
//                j = charIndexList.get(right) + 1;
                int pos = leftBound(charIndexList, j);
                if (pos == charIndexList.size()) return false;
                j = charIndexList.get(pos) + 1;

            }
            return true;
        }


        /**
         * posList中小于target的最左边界
         */
        private int leftBound(List<Integer> posList, int target) {
            int left = 0, right = posList.size();
            while (left < right) {
                int mid = (left + right) / 2;
                if (posList.get(mid) >= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return right;
        }
    }
    private static class MySubsequence implements StrSubsequence<String,Boolean> {

        @Override
        @SuppressWarnings("unchecked")
        public Boolean isSubsequence(String content, String target) {
            ArrayList<Integer>[] contentDic = new ArrayList[256];
            for (int i = 0; i < content.length(); i++) {
                if (contentDic[content.charAt(i)] == null)  contentDic[content.charAt(i)] = new ArrayList<>();

                contentDic[content.charAt(i)].add(i);
            }

            int j = -1;
            for (int i = 0; i < target.length(); i++) {
                ArrayList<Integer> posList = contentDic[target.charAt(i)];
                if (posList == null)  return false;

                int pos = rightBound(posList,j);
                if (pos == posList.size()) return false;

                j = posList.get(pos) + 1;

            }
            return true;
        }

        /**
         *  posList中大于 target的最左侧的值
         */
        private int rightBound (List<Integer> posList,int target) {
            int left = 0,right = posList.size();
            while (left < right) {
                int mid = (left + right) / 2;
                if (posList.get(mid) <= target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }


    }


    /**
     * 暴力算法
     */
    private static class ForceSubsequence implements StrSubsequence<String, Boolean> {
        @Override
        public Boolean isSubsequence(String content, String target) {
            int i = 0, j = 0;
            while (i < content.length() && j < target.length()) {
                if (content.charAt(i++) == target.charAt(j)) {
                    j++;
                }
            }
            return j == target.length();
        }
    }

    /**
     * 判定子序列的接口
     */
    private interface StrSubsequence<E, R> {
        R isSubsequence(E e, String target);
    }

}
