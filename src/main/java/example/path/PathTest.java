package example.path;

import org.junit.Test;

public class PathTest {
    @Test
    public void test1 () {
        System.out.println(System.getProperty("user.dir"));     //工程的路径：D:\workspace\XiaoWei_Practice
        System.out.println("getResource(/) : " + PathTest.class.getResource("/"));//file:/D:/workspace/XiaoWei_Practice/target/classes/
        System.out.println("getResource(“”) :" + PathTest.class.getResource(""));//file:/D:/workspace/XiaoWei_Practice/target/classes/example/path/

    }
}
