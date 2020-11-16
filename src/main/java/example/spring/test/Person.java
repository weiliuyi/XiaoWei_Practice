package example.spring.test;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--07 11:53
 **/
@Service
public class Person {
    private String name = "hello";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
