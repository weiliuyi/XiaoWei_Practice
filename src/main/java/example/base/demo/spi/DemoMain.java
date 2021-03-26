//package example.base.demo.spi;
//
//import org.example.demo.Perform;
//import org.example.demo.Singer;
//
//import java.util.Iterator;
//import java.util.ServiceLoader;
//
//public class DemoMain {
//
//
//    //https://www.jianshu.com/p/46b42f7f593c
//    public static void main(String[] args) {
//        ServiceLoader<Perform> performs = ServiceLoader.load(Perform.class,null);
//        Iterator<Perform> it = performs.iterator();
//        while (it.hasNext()) {
//            Perform perform = it.next();
//            perform.show();
//        }
//
//
////        System.out.println(Perform.class.getName());
//    }
//}
