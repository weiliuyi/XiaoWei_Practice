package example.base.path;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class TestURL {

    /*
    *   URI 和URL 什么区别？？？
    *
    * */


    /*
    * http://localhost:8080/DemoWeb/test/index.jsp
    * 获得工程的名字：request.getContextPath() 结果：/DemoWeb
    * 得到包含工程名的当前页面的全路径：reqeust.getRequestURI() 结果：/Demo/test/index.jsp
    * 得到地址栏地址：request.getRequestURL() 结果：http://localhost:8080/DemoWeb/test/index.jsp
    * 得到当前页面所在目录下全名称：reqeust.getServletPath() 结果：/test/testpath.jsp
    * 得到页面所载服务器的全路径(实际的路径)：application.getRealPath("testpath.jsp") 结果：D:\Develop Files\apache-tomcat-5.5.15\apache-tomcat-5.5.15\webapps\DemoWeb\testpath.jsp
     *
     *  */

    private static URL url ;

    @BeforeClass
    public static void before() throws MalformedURLException {
        url  = new URL("http://www.baidu.com/java/index.html?name=weiliuyi");
    }


    @Test
    public void testURL () throws MalformedURLException {
        url = new URL("http://www.baidu.com/java/index.html?name=weiliuyi");
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

    @Test
    public void testURI () throws URISyntaxException {
        URI uri = url.toURI();
        System.out.println("uri :" + uri);
        System.out.println("authority : " + uri.getAuthority());
        System.out.println("host : " + uri.getHost());
        System.out.println("port : " +uri.getPort());
        System.out.println("query : " +uri.getQuery());
        System.out.println("rawPath : " +uri.getRawPath());
        System.out.println("path : " + uri.getPath());
        System.out.println("fragment : " + uri.getFragment());
        System.out.println("rawfragment : " +uri.getRawFragment());
    }

}
