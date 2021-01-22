package example.base.generic.get;

import java.util.Arrays;

/**
 * @description: 获取父类型的泛型；
 * @author: weiliuyi
 * @create: 2020--03 11:58
 **/
public class A extends B<C> implements ID<IH> ,IF{

    public static void main(String[] args) {
        new A();
        System.out.println(C.class);

        System.out.println("------------superclass-------");
        System.out.println(A.class.getSuperclass());


        System.out.println("------------getInterface ------");
        System.out.println(Arrays.toString(A.class.getInterfaces()));

        System.out.println("--------------getGenericSuperclass--------------");
        System.out.println(A.class.getGenericSuperclass().getTypeName());
    }
}
