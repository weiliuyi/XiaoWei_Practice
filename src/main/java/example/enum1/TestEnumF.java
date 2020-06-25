package example.enum1;

import org.junit.Test;

import java.util.Arrays;



public class TestEnumF {

    @Test
    public void myTest1() {
        System.out.println(EnumF.Monday);
        System.out.println(EnumF.valueOf("Monday"));
        System.out.println("-------------------------");
        System.out.println(Arrays.toString(EnumF.values()));
        EnumF[] values = EnumF.values();
        Arrays.stream(values).forEach(System.out::println);
    }
}

enum EnumF {
    Monday (1),
    Wednesday(2);

    private Integer index;
    EnumF (Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "EnumF{" +
                "index=" + index +
                '}';
    }
}
