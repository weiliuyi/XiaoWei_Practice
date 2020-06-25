package example.generic;

import org.junit.Test;

public class InstanceCreateGeneric {
    @Test
    //隐式的工厂方法创建实例
    public void test1 () throws Exception {
        ClassAsFactory<Employee> cEmployee = new ClassAsFactory<>(Employee.class);
        System.out.println(cEmployee.getKind());
        ClassAsFactory<Integer> cInteger = new ClassAsFactory<>(Integer.class);
        System.out.println(cInteger.getKind());

    }

    @Test
    //显示工厂方法实例；
    public void Test2 () {
        Foo2<Integer> fooInteger = new Foo2<>(new IntegerFactory());
        System.out.println(fooInteger.getKind());
    }

    @Test
    //模板方法的设计模式
    public void Test3 () {
        IntegerGeneric integerGeneric = new IntegerGeneric();
        System.out.println(integerGeneric.getElement());
    }

}

class ClassAsFactory<T> {
    private T kind;
    @SuppressWarnings("deprecation")
    ClassAsFactory(Class<T> kind) throws Exception {
        this.kind = kind.newInstance();
    }
    T getKind() {
        return kind;
    }
}
class Employee{}

interface Factory<T>{
    T create();
}

class Foo2<T> {
    private T kind;
    <F extends Factory<T>> Foo2(F factory) {
        this.kind = factory.create();
    }
    T getKind() {
        return this.kind;
    }
}

class IntegerFactory implements Factory<Integer> {

    @Override
    public Integer create() {
        return 0;
    }
}


abstract class GenericWithCreate<T> {
    private T element;
    GenericWithCreate() {
        this.element =  create();
    }
    abstract T create ();

    T getElement () {
        return this.element;
    }
}

class IntegerGeneric extends GenericWithCreate<Integer> {

    @Override
    Integer create() {
        return 0;
    }
}