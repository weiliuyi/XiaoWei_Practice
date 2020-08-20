package example.base.generic;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTest {

    @Test
    public void testSuperClass () {
        Student student = new Student();
        System.out.println(student.getClass().getSuperclass());
        System.out.println(student.getClass().getGenericSuperclass());
        Class<?> superClass = student.getClass().getSuperclass();
        Type type = student.getClass().getGenericSuperclass();
        Class<? extends Type> gSuperClass = type.getClass();
        System.out.println("typeName "  + type.getTypeName());
        System.out.println("superClass :" + superClass  + " gSuperClass " + gSuperClass);
        Type[] genericInterfaces = gSuperClass.getGenericInterfaces();
        System.out.println(genericInterfaces);

    }

    @Test
    public void testSuperClass2 () {
        Student student = new Student();
        System.out.println(student);
//        Class<? extends Student> clazz = student.getClass();
//        ParameterizedType superclass = (ParameterizedType)clazz.getGenericSuperclass();
//        Type[] types = superclass.getActualTypeArguments();
//        Arrays.stream(types).forEach(t -> System.out.println(t.getTypeName()));
        //System.out.println(types);
    }


    @Test
    public void testGeneric () {
        String[] strs = new String[3];
        Object[] objs = strs;
        objs[0] = "hello";
        objs[1] = 1;
    }
}

class Person<T> {
    private Class clazz;

    public Person () {
        Type genType = this.getClass().getGenericSuperclass();
        System.out.println("super this " + this);
        System.out.println("super class "  +  this.getClass());
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.clazz = (Class<T>) params[0];
        System.out.println( clazz.getTypeName());
    }
}

class Student extends Person<GenericTest> {
    public Student () {
        super();
        System.out.println("subclass " + this);
    }
}