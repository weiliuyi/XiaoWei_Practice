package example.base.demo.spi;

public class Dog implements Ishout {
    @Override
    public void shout() {
        System.out.println("dog wang wang wang !!!!!");
    }
}
