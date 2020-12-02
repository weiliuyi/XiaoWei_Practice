package example.spring.Qualifer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 16:14
 **/
@Configuration
public class MyConfiguration {

    @Bean
    public A a() {
        return new A();
    }

    @Conditional(BaseCondition.class)
    @Bean
    @Primary
    public B b() {
        return new B();
    }


    @Bean
    //@ConditionalOnBean(C.class)
    public D d() {
        return new D();
    }

}
