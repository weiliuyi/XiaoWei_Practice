package example.base;

import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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
}
