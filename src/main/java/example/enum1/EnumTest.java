package example.enum1;

import org.junit.Test;

import java.util.Arrays;


public class EnumTest {

    @Test
    public void test1 () {
        System.out.println(Week.Fri);
        System.out.println(Week.Sat);
        EnumF monday = EnumF.Monday;
        System.out.println(monday);
        System.out.println(Week.getByIndex(1));
        System.out.println(Week.getByIndex(8));
    }
}


enum Week {

    Mon(1,"星期一"),
    Tues(2,"星期二"),
    Wed(3,"星期三"),
    Thurs(4,"星期四"),
    Fri(5,"星期五"),
    Sat(6,"星期六"),
    Sun(7,"星期七"),
    Unk(-1,"星期8");
    private Integer index;
    private String name;
    Week (Integer index, String name) {
        this.index = index;
        this.name = name;
    }


    public static Enum getByIndex (int index) {
       return Arrays.stream(Week.values()).
                filter(item -> item.index == index).
                findAny().
                 orElse(Week.Unk);
    }

    @Override
    public String toString() {
        return "Week{" +
                "num=" + index +
                ", name='" + name + '\'' +
                '}';
    }
}