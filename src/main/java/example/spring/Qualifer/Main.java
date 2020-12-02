package example.spring.Qualifer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 15:42
 **/
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext contest = new AnnotationConfigApplicationContext("example.spring.Qualifer");
        D bean = contest.getBean(D.class);
        System.out.println(bean);
    }
}
