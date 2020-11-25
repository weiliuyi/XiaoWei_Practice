package example.spring.Qualifer;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 15:37
 **/


public class A  implements TestImp{
    @Override
    public void test() {
        System.out.println("hello I'am A");
    }
}
