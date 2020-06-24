package example.generic;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

public class ErasureGeneric {

    @Test
    public void test1 () {
        List<A> aList = new ArrayList<>();
        aList.add(new A());
        List<B> bList = new ArrayList<>();
        bList.add(new B());
        Map<A,B> abMap = new HashMap<>();
        abMap.put(new A(),new B());
        System.out.println(Arrays.toString(aList.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(bList.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(abMap.getClass().getTypeParameters()));


        ArrayList<String> strList = new ArrayList();
        ArrayList clist = new ArrayList();


    }

}

class A{}
class B{}