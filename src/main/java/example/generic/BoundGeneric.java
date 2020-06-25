package example.generic;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoundGeneric {

    @Test
    public void test1 () {
        ArrayMaker<String> arrayMaker = new ArrayMaker<>(String.class);
        String[] strs = arrayMaker.createInstance(2);
        System.out.println(Arrays.toString(strs));
    }

    @Test
    public void Test2() {
       ListMaker<String> listMaker = new ListMaker<>();
        List<String> list = listMaker.createList();
        list.forEach((item) -> {});
        List<String> list2 = listMaker.createList("wly", 2);
        list2.forEach(System.out::println);
    }
}

class ListMaker<T> {

    //@SuppressWarnings("all")
    List<T> createList () {
       return new ArrayList<T>(); //这个地方会抛出来泛型警告；
    }

    @SuppressWarnings("SameParameterValue")
    List<T> createList (T t,int size) {
        List<T> list = new ArrayList<>();
        for (int i = 0;i < size;i++) {
            list.add(t);
        }
        return list;
    }
}



class ArrayMaker<T> {
    private Class<T> kind;

    ArrayMaker(Class<T> kind) {
        this.kind = kind;
    }
    @SuppressWarnings({"unchecked","SameParameterValue"})
    T[] createInstance(int size) {
       return (T[]) Array.newInstance(this.kind,size);
    }
}