package example.generic;



import java.util.Iterator;
import java.util.Random;

public class CoffeeGenerator implements Generator<Coffee>,Iterable<Coffee> {

    //private Class[] types = {MoCha.class,AmericaCoffee.class,ChinexeCoffee.class};
    private final Class[] types = new Class[]{MoCha.class};


    private final Random random = new Random(47);

    private int size;
    public CoffeeGenerator () {}
    public CoffeeGenerator(int size) {this.size = size;}



    @Override
    public Coffee next()  {
        try {
            return (Coffee) types[random.nextInt(types.length)].newInstance();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return null;
    }

    class CoffeeIterator implements Iterator<Coffee> {
        int count = size;

        @Override
        public boolean hasNext() {
           return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            return CoffeeGenerator.this.next();
        }
    }


    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }


    public static void main(String[] args) {
       Iterable<Coffee> generator = new CoffeeGenerator(20);
        for (Coffee coffee : generator) {
            System.out.println(coffee);
        }


    }
}

interface Generator<T> {
    T next () ;
}


class Coffee {
    private static int counter = 0;
    private final int id = ++counter;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " id " + id;
    }
}

class MoCha extends Coffee{}

class AmericaCoffee extends Coffee {}

class ChinexeCoffee extends Coffee {}

