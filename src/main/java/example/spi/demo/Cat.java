package example.spi.demo;

public class Cat implements Ishout {
    @Override
    public void shout() {
        System.out.println("cat miao miao miao !!!!");
    }
}
