package example.spring.annotation.s_import;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--16 11:36
 **/
@Import({Object1.class,Object2.class,TestDemo2.class, MyClass.class})
public class TestDemo {

    @Bean
    public AccountDao2 accountDao2 () {
        return new AccountDao2();
    }
}
