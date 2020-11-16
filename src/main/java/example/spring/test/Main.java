package example.spring.test;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--07 11:54
 **/
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("example.spring.test");
        Son bean = applicationContext.getBean(Son.class);
        System.out.println(JSON.toJSON(bean));
    }
}
