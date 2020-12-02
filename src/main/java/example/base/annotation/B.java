package example.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 注解B
 * @author: weiliuyi
 * @create: 2020--02 19:02
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface B {

}
