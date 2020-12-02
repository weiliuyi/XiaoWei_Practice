package example.spring.Qualifer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--26 15:02
 **/

@Configuration
public class ConfigurationV2 {

    @Bean
    public C c () {
        return new C();
    }
}
