package example.base;

import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.Collator;
import java.util.*;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 11:36
 **/
public class StringTest {

    @Test
    public void test1 () {
        String page = "pages\\/index\\/index";
        String s = StringUtils.replaceChars(page, "\\", "");
        System.out.println(s);
    }

    @Test
    public void test2 () {
        String s = HttpUtil.get("https://you.58.com/customer/wx/rediskey?redisKey=ma:token:cache:mini:wxc4c7eabcc6ff6407");
        System.out.println(s);
    }

    public static void main(String[] args) {
        Collator colator = Collator.getInstance(Locale.CHINA);
        List<String> result = new ArrayList<>();
        result.add("河南");
        result.add("国家");
        result.add("中");
        result.add("啊");
        result.add("比");
        result.forEach(str -> {
            System.out.println(str);
        });
        System.out.println("---------------------------------");
        result.sort((o1,o2) -> colator.compare(o1,o2));
        result.forEach(str -> {
            System.out.println(str);
        });
    }
}
