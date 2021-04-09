package example.base.juc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @ClassName CompletableFuture
 * @Description
 * @Author weiliuyi
 * @Date 2021/4/1 7:41 下午
 **/
public class CompletableFutureTest {


    //http://arganzheng.life/writing-asynchronous-code-with-completablefuture.html

    /**
     * 在运行时抛出异常
     */
    @Test
    public void testExceptions () {
        for (boolean failure : new boolean[] {false,true}) {
            CompletableFuture<Integer> x = CompletableFuture.supplyAsync(() -> {
                if (failure) {
                    throw new RuntimeException("something is wrong ");
                }
                return 42;
            });
            try {
                System.out.println(x.get());
                System.out.println("isCompletedExceptionally " + x.isCompletedExceptionally());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCause().getMessage());
                System.out.println("isCompletedExceptionally = " + x.isCompletedExceptionally());
            }
        }
    }


    /**
     * 异常处理方式1，catch
     */
    @Test
    public void testHandleException1 () throws ExecutionException, InterruptedException {
        for (boolean failure : new boolean[] {false,true}) {
            CompletableFuture<Integer> x = CompletableFuture.supplyAsync(() -> {
                if (failure) {
                    throw new RuntimeException("something is wrong ");
                }
                return 42;
            });
            CompletableFuture<Integer> tryX = x.exceptionally(ex -> -1);
            System.out.println(tryX.get());
            System.out.println("isCompletedExceptionally " + tryX.isCompletedExceptionally());
        }
    }

    @Test
    public void testThenBoth () throws InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object obj = new Object();
            //throw new RuntimeException("test !!!!!!!");
            System.out.println("future1 执行完成");
            obj = null;
            if (obj == null) {
                throw new RuntimeException("test");
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2 执行完成");
            return 200;
        });
        //这两个结果都执行完成，并且返回void
//        CompletableFuture<Void> f3 = future1.thenAcceptBothAsync(future2, (f1, f2) -> {
//            System.out.println(f1 + f2);
//        });
        CompletableFuture<Integer> f3 = future1.thenCombineAsync(future2, Integer::sum).exceptionally(e -> {
            System.out.println("exceptionally" + e);
            return -1;
        });
        try {
            System.out.println(f3.get(2000, TimeUnit.MILLISECONDS));
        } catch (ExecutionException | TimeoutException e) {
            System.out.println("catch =" +  e);
            //f3.completeExceptionally(e);
            //System.out.println(f3.isDone());
        }
        while (!f3.isDone()) {
            Thread.sleep(1000);
        }
    }

    /**
     * thenApply和thenApplyAsync的区别？
     * 1.执行任务的线程
     * thenApplyAsync默认是ForkJoinPool.commonPool()获取一个线程并且执行
     * thenApply() 如果supplyAsync执行的速度特别快，则由主线程进行，否则,和supplyAsyn()方法是一样的线程
     *
     */
    @Test
    public void testApplyAsync () throws ExecutionException, InterruptedException {
        System.out.println("-------------");
        CompletableFuture<String> supplyAsyncWithSleep = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "supplyAsyncWithSleep Thread Id : " + Thread.currentThread();
        });
        CompletableFuture<String> thenApply = supplyAsyncWithSleep
                .thenApply(name -> name + "------thenApply Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyAsync = supplyAsyncWithSleep
                .thenApplyAsync(name -> name + "------thenApplyAsync Thread Id : " + Thread.currentThread());
        System.out.println("Main Thread Id: "+ Thread.currentThread());
        System.out.println(thenApply.get());
        System.out.println(thenApplyAsync.get());
        System.out.println("-------------No Sleep");
        CompletableFuture<String> supplyAsyncNoSleep = CompletableFuture.supplyAsync(()->{
            return "supplyAsyncNoSleep Thread Id : " + Thread.currentThread();
        });
        CompletableFuture<String> thenApplyNoSleep = supplyAsyncNoSleep
                .thenApply(name -> name + "------thenApply Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyAsyncNoSleep = supplyAsyncNoSleep
                .thenApplyAsync(name -> name + "------thenApplyAsync Thread Id : " + Thread.currentThread());
        System.out.println("Main Thread Id: "+ Thread.currentThread());
        System.out.println(thenApplyNoSleep.get());
        System.out.println(thenApplyAsyncNoSleep.get());
    }

    /**
     * 异常处理；如果发生异常，则会终止此CompletableFuture流程的执行；
     * exceptionally：可以接受异常值信息，并且返回自定义返回值；
     * handle():也能够捕获异常，并且自定义返回值，不同的是，无论是否发生异常都会触发执行；
     */

    @Test
    public void testSeq () {
        CompletableFuture<Void> thenRun = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 1;
            if (i == 1) {
                throw new RuntimeException("error");
            }
        return "success"; }).thenRun(() -> System.out.println("thenRun")).thenAccept(v -> {
            System.out.println(v + " thenAccept");
        });
        CompletableFuture.runAsync(() -> System.out.println("CompletableFuture.runAsync"));

    }


    /**
     * 组合多个CompletableFuture:
     * allOf:等待所有CompletableFuture完成以后才会执行回调函数；
     * anyOf:只要其中的CompletableFuture完成，那么就会执行回调函数，此时注意其他任务不执行
     */

    @Test
    public void testAnyAll () throws InterruptedException {
        CompletableFuture<Integer> one = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> two = CompletableFuture.supplyAsync(() -> 2);
        CompletableFuture<Integer> three = CompletableFuture.supplyAsync(() -> 3);
        CompletableFuture<Integer> four = CompletableFuture.supplyAsync(() -> 4);
        CompletableFuture<Integer> five = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> six = CompletableFuture.supplyAsync(() -> 6);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(one, two, three, four, five, six);
        voidCompletableFuture.thenApply(v-> Stream.of(one,two,three,four, five, six)
                .map(CompletableFuture::join)
                .collect(Collectors.toList())).
                thenAccept(System.out::println);

        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println("1");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            System.out.println("1");
        });
        Thread.sleep(100000);
    }

    @Test
    public void testTask () {
        stopWatch(()-> {
            Stream<CompletableFuture<Integer>> xs = Stream.of(1, 2, 3, 4, 5).map(x -> CompletableFuture.supplyAsync(() -> compute(x)));
            CompletableFuture<Integer> sum = xs.reduce(CompletableFuture.completedFuture(compute(0)), (x, y) -> x.thenCombineAsync(y, Integer::sum));
            CompletableFuture<Integer>[] ys = Stream.of(1, 2, 3).map(x -> sum.thenApply(s -> multiply(s, x))).toArray(CompletableFuture[]::new);
            CompletableFuture<Integer> max = CompletableFuture.allOf(ys).thenApply(( v ->
                    Arrays.stream(ys).
                            map(y -> y.getNow(Integer.MAX_VALUE)).
                            max(Comparator.naturalOrder()).
                            get()
            ));
            try {
                System.out.println("max result = " +  max.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static int compute (int x) {
        println("Computing " + x + "...");
        sleep(2, SECONDS);
        println("Computed " + x + ".");
        return x;
    }


    private static void println (String message) {
        System.out.println("[" + Thread.currentThread().getName() + "]: " + message);
    }

    private static int multiply(final int x, final int y) {
        println("Computing " + x + " * " + y + "...");
        sleep(2, SECONDS);
        final int r = x * y;
        println("Computed " + x + " * " + y + " = " + r + ".");
        return r;
    }

    private static void sleep (int duration, TimeUnit unit) {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void stopWatch (Runnable action) {
        long begin = System.currentTimeMillis();
        action.run();
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - begin) / 1000 + " seconds.");
    }


    @Test
    public void testTask2 () {
        Stream<CompletableFuture<Integer>> sc = Stream.of(1, 2, 3, 4, 5).map(x -> CompletableFuture.supplyAsync(() -> compute(x)));
        CompletableFuture<Integer> sum = sc.reduce(CompletableFuture.completedFuture(compute(0)), (x, y) -> x.thenCombine(y,
                (i,j) -> j + i));

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for (Integer x : Arrays.asList(1, 2, 3)) {
            CompletableFuture<Integer> integerCompletableFuture = sum.thenApplyAsync(s -> multiply(x, s));
            list.add(integerCompletableFuture);
        }
        CompletableFuture<Integer>[] ys = list.toArray(new CompletableFuture[0]);
        CompletableFuture<Integer> max = CompletableFuture.allOf(ys).thenApply(v ->
             Arrays.stream(ys).
                    map(y -> y.getNow(Integer.MAX_VALUE)).
                    max(Comparator.naturalOrder()).get()
        );
        try {
            System.out.println("result  = " + max.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTask3 () {
        CompletableFuture<Integer> sum = Stream.of(1, 2, 3, 4, 5).
                map(x -> CompletableFuture.supplyAsync(() -> compute(x))).
                reduce(CompletableFuture.completedFuture(compute(0)), (x, y) -> x.thenCombineAsync(y, Integer::sum));
        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for (Integer x : Arrays.asList(1, 2, 3)) {
            CompletableFuture<Integer> integerCompletableFuture = sum.thenApply(s -> multiply(x, s));
            list.add(integerCompletableFuture);
        }
        CompletableFuture<Integer>[] ys  = list.toArray(new CompletableFuture[0]);
        CompletableFuture<Integer> max = CompletableFuture.allOf(ys).thenApply(v ->
                Stream.of(ys).map(y -> y.getNow(Integer.MAX_VALUE)).max(Comparator.naturalOrder()).get()
        );

        try {
            System.out.println("result = " +  max.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}
