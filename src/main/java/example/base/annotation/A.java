package example.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 注解A
 * @author: weiliuyi
 * @create: 2020--02 18:59
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface A {
}
