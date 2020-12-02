package example.base.annotation;

import cn.hutool.core.annotation.AnnotationUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * @description: 复合注解
 * @author: weiliuyi
 * @create: 2020--26 17:51
 **/
public class ComplexAnnotation {


    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Base {
        public String value() default "";
    }

    /**
     * 复合注解
     */

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Base("hello wly")
    @interface Two {
    }

    /**
     * 复合注解的基本使用
     */

    @Two
    private String getAll() {
        return "OK";
    }

    @Test
    public void test1() throws NoSuchMethodException {
        Class<ComplexAnnotation> clazz = ComplexAnnotation.class;
        Method method = clazz.getDeclaredMethod("getAll", new Class[]{});
        Annotation[] annoArray = method.getDeclaredAnnotations();
        for (Annotation anno:annoArray) {
            System.out.println(anno);
            if (anno.annotationType() != Base.class) {
                System.out.println("complex annotation don't equals base annotation");
            }
        }
    }



    @Test
    public void test2 () throws NoSuchMethodException {
        Class<ComplexAnnotation> clazz = ComplexAnnotation.class;
        Method method = clazz.getDeclaredMethod("getAll", new Class[]{});
        Annotation[] annoArray = method.getDeclaredAnnotations();


        Base base1 = AnnotationUtils.findAnnotation(method, Base.class);
        System.out.println(base1);
        Annotation annotation = printAllAnnotation(annoArray[0], "-");
        System.out.println((annotation.annotationType()));

    }

    Annotation printAllAnnotation (Annotation anno,String prefix) {
        Annotation[] declaredAnnotations = anno.annotationType().getDeclaredAnnotations();
        for (Annotation annoTemp :declaredAnnotations) {
            System.out.println(prefix + annoTemp);
            if (annoTemp.annotationType().getName().startsWith("java.lang.annotation")) {
                continue;
            }
            if (annoTemp.annotationType() == Base.class) {
                return annoTemp;
            }
            return printAllAnnotation(annoTemp,prefix + "-");
        }
        return null;
    }
}
