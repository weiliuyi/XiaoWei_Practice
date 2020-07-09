package example.spi.demo;

import org.example.demo.Perform;
import org.example.demo.Singer;

import java.util.ServiceLoader;

public class DemoMain {


    //https://www.jianshu.com/p/46b42f7f593c
    public static void main(String[] args) {
        ServiceLoader<Ishout> shouts = ServiceLoader.load(Ishout.class);
        shouts.forEach(item -> item.shout());
        Singer singer = new Singer();
        //singer.show();

        ServiceLoader<Perform> performs = ServiceLoader.load(Perform.class,null);
        performs.forEach(perform-> perform.show());



        System.out.println(Perform.class.getName());
    }
}
