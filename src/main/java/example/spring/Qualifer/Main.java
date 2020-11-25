package example.spring.Qualifer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ConditionContext;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 15:42
 **/
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext contest = new AnnotationConfigApplicationContext("example.spring.Qualifer");
        B bean = contest.getBean(B.class);
        System.out.println(bean);
    }
}
