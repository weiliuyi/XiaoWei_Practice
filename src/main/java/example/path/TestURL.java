package example.path;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TestURL {
    @Test
    public void test2 () throws MalformedURLException {
        URL url = new URL("http://www.baidu.com/java/index.html");
        String file = url.getFile();
        System.out.println("file : " + file);
        System.out.println("protocol :" + url.getProtocol());
        System.out.println("port : " + url.getPort());
        System.out.println("default port :" +  + url.getDefaultPort());
        System.out.println("query :" + url.getQuery());
        System.out.println("host :" + url.getHost());
        System.out.println("authority :" + url.getAuthority());
        System.out.println("ref :" + url.getRef());
        System.out.println("userinfo :" + url.getUserInfo());
    }
}
