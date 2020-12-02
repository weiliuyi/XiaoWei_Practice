package example.base.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--02 19:16
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface D {
}
