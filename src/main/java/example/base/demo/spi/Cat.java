package example.base.demo.spi;

public class Cat implements Ishout {
    @Override
    public void shout() {
        System.out.println("cat miao miao miao !!!!");
    }
}
