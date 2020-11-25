package example.spring.Qualifer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 16:14
 **/
@Configuration
public class MyConfiguration {

//    @Conditional(BaseCondition.class)
//    @Bean
    public A a() {
        return new A();
    }

    @Conditional(BaseCondition.class)
    @Bean
    public B b() {
        return new B();
    }

}
