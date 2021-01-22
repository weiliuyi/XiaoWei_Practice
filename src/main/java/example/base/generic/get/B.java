package example.base.generic.get;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--03 11:58
 **/
public class B<T> {
    private Class clazz;

    public B() {
        System.out.println(getClass());
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        System.out.println(Arrays.toString(params));
    }
}
