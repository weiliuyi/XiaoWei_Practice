package example.algorithm.interview.test;


/**
 * @description: 一个字符串是否包含另外一个字符串  包含条件：和顺序无关，只要各个字母的数量相等就认为包含的
 * 示例： str1:abcddf  str2:cbdd    str1 包含str2 index 1 开始
 * @author: weiliuyi
 * @create: 2020--27 11:07
 **/
public class StringContain {

    /**
     * 效率最高的方式 记账算法
     */
    public static void main(String[] args) {
        String content = "abacfddbc";
        String aim = "bcdd";
        System.out.println(getSort(aim));
        System.out.println(containsVersion1(content,aim));
        System.out.println(containsVersion2(content,aim));
    }

    /**
     * 使用记账算法，和滑动窗口的策略 ------------ 固定窗口大小&变窗口大小 记账算法适用于没有顺序的结构
     * 首先根据aim进行记账(在char数组中统计各个字符的数量)，并且记录总数(总欠账)；
     * 然后从0 到 aim.length -1进行统计
     */

    private static int containsVersion2 (String content,String aim) {
        int aimLength = aim.length();
        int [] accountBook = new int[256];
        int count = 0;
        for (int i = 0; i < aimLength;i++) {
            accountBook[aim.charAt(i)] += 1;
            count++;
        }
        for (int i = 0;i < aimLength;i++ ) {
            if (accountBook[content.charAt(i)]-- > 0 ) {
                count--;
            }
        }
        if (count == 0) {
            return 0;
        }
        for (int i = 1;i < content.length() - aimLength + 1;i++) {
            if (accountBook[content.charAt(i + aimLength -1)]-- > 0) {
                count--;
            }
            if (accountBook[content.charAt(i-1)]++ >= 0) {
                count++;
            }
            if (count == 0) {
                return i;
            }
        }
        return -1;
    }



    /**
     * 暴力求解的方式，for 遍历所有得到定长度的字符串，获取到字串并且排序，在和aim排过序的进行比较；
     * @param content  长的字符串，判断是否包含其他字符串
     * @param aim 目标字符串
     * @return 返回第一次匹配上的下标
     */
    private static int containsVersion1 (String content,String aim) {
        int contentLength = content.length();
        int size = aim.length();
        String aimSort = getSort(aim);
        for (int i = 0;i < contentLength - size + 1;i++) {
            String subStr = content.substring(i,i + size);
            if (aimSort.equals(getSort(subStr))) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 根据字符串的大小进行排序
     * @param content 需要排序的字符串
     * @return 返回排序之后的字符串
     */
    private static String getSort (String content) {
        char[] chars = content.toCharArray();
        for (int i = 1;i < chars.length;i++) {
            int j = i - 1;
            char temp = chars[i];
            while (j >=0 && chars[j] > temp) {
                chars[j+1] = chars[j];
                j--;
            }
            chars[j+1] = temp;
        }
        return String.valueOf(chars);
    }

}
