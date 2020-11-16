package example.spring.annotation.s_import;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--16 11:43
 **/
public class ImportAnnotationTestDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(TestDemo.class);  //这里的参数代表要做操作的类

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames){
            System.out.println(name);
        }

    }
}

class AccountDao2 {
}
class TestDemo2 {
}

@Import({Object1.class,Object2.class,TestDemo2.class, MyClass.class})
 class TestDemo {

    @Bean
    public AccountDao2 accountDao2 () {
        return new AccountDao2();
    }
}

class MyClass implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"example.spring.annotation.s_import.TestDemo3"};
    }
}
class Object1 {
}

class Object2 {
}