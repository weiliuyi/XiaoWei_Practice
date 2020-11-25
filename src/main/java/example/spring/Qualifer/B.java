package example.spring.Qualifer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 15:38
 **/

public class B implements TestImp {


    @Autowired
    DNoUser noUser;

    @Override
    public void test() {
        System.out.println("Hello I'm B");
    }

    @Override
    public String toString() {
        return "B{" +
                "noUser=" + noUser +
                '}';
    }
}
