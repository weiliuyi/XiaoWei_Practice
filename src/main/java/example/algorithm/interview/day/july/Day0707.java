package example.algorithm.interview.day.july;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @ClassName Day0707 O(1)时间：能够查找或者删除数组的任意元素
 * @Description https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247487414&idx=1&sn=2be87c0c9279da447f8ac8b8406230fe&chksm=9bd7f1beaca078a865357f58ba2ff12b46490b0a773c0221e0a846c67950fa9c661664ad500e&scene=21#wechat_redirect
 * @Author weiliuyi
 * @Date 2021/7/7 3:37 下午
 **/
public class Day0707 {


    /**
     * 本题的难点：
     * 1。 插入，删除，获取随机元素的这三个操作的时间复杂度必须都是O(1)
     * 2. getRandom方法返回的元素必须等概率的返回随机元素
     * <p>
     * 插入，删除，获取哪种数据结构的时间复杂度是O(1)？
     * HashSet:hash集合的的层实现原理就是一个大数组，我们通过hash函数映射到一个索引上，如果用拉链法解决冲突，这个索引可能连着一个链表或者红黑树；
     * 这样标准的HashSet，能够实现在O(1)的时间内实现getRandom函数么？
     * 不可以，元素被整个hash函数分散到整个数组里，况且还有拉链法等解决hash冲突的机制，做不到O(1)时间等概率的获取元素；
     * <p>
     * LinkedHashSet（本质上是hash表配合双链表，元素存储在双链表中）:
     * 只是给hashSet增加了有序性，依然无法按照要求实现getRandom函数，因为底层链表结构存储元素的话，无法O(1)的时间内访问元素；
     * <p>
     * 对于getRandom方法，如果想实现「等概率」且「O(1)的时间」取出元素，一定要满足：底层使用数组实现，且数组必须是紧凑的
     * <p>
     * 如果使用数组存储元素的话，插入和删除的操作是怎么可能是O(1)呢？
     * 1。如果数组尾部进行插入和删除不会涉及到数据的搬移，时间复杂度O(1)
     * 所以想在O(1)的时间删除数组中的某一元素val，可以先把这个元素交换到数组的尾部，然后在删掉
     */


    @Test
    public void testComplexStruct() {
        IComplexStruct<Integer> myComplexImpl = new MyComplexStructImpl<>();
        IntStream.range(0, 16).forEach(num -> {
            myComplexImpl.insert(num);
            System.out.println(myComplexImpl.get(num));
        });


        Map<Integer, Integer> randomMap = new HashMap<>();
        IntStream.range(0, 1000000).forEach(num -> {
            Integer random = myComplexImpl.getRandom();
            randomMap.put(random, randomMap.getOrDefault(random, 0) + 1);
        });
        randomMap.values().stream().mapToDouble(num -> num / 1000000D).forEach(System.out::println);

    }


    @Test
    public void testFinalComplexStruct() {
        IComplexStruct<Integer> finalStruct = new ComplexStructImpl<>();
        IntStream.range(0, 10).forEach(finalStruct::insert);
        Map<Integer, Integer> randomMap = new HashMap<>();
        IntStream.range(0, 1000000).forEach(num -> {
            Integer random = finalStruct.getRandom();
            randomMap.put(random, randomMap.getOrDefault(random, 0) + 1);
        });
        randomMap.values().stream().mapToDouble(num -> num / 1000000D).forEach(System.out::println);

    }

    @Test
    public void testOptimizeComplexStruct () {
        IComplexStruct<Integer> optimizeStruct = new OptimizeComplexStructImpl<>();
        IntStream.range(0, 100).forEach(optimizeStruct::insert);
        Map<Integer, Integer> randomMap = new HashMap<>();
        IntStream.range(0, 10000000).forEach(num -> {
            Integer random = optimizeStruct.getRandom();
            randomMap.put(random, randomMap.getOrDefault(random, 0) + 1);
        });
        randomMap.values().stream().mapToDouble(num -> num / 10000000D).forEach(System.out::println);
    }


    private static class OptimizeComplexStructImpl<T> implements IComplexStruct<T> {
        List<T> list;
        Map<T, Integer> value2Index;

        public OptimizeComplexStructImpl() {
            list = new ArrayList<>();
            value2Index = new HashMap<>();
        }

        @Override
        public boolean insert(T val) {
            if (value2Index.containsKey(val))
                return false;
            list.add(val);
            value2Index.put(val, list.size() - 1);
            return true;
        }

        @Override
        public boolean remove(T val) {
            if (!value2Index.containsKey(val))
                return false;
            //维护映射关系
            Integer index = value2Index.remove(val);
            value2Index.put(val,index); //维护交换 引起的映射关系的改变
            swap(list,index,list.size()-1);

            list.remove(list.size()-1);
            return true;
        }

        @Override
        public T get(T val) {
            return val;
        }

        @Override
        public T getRandom() {
            Random random = new Random();
            return list.get(random.nextInt(this.list.size()));

        }

        public void swap (List<T> list,int i,int j) {
            T temp = list.get(i);
            list.set(i,list.get(j));
            list.set(j,temp);
        }
    }


    private static class ComplexStructImpl<T> implements IComplexStruct<T> {
        Map<T, Integer> value2IndexMap;
        Object[] arr;
        int size;

        public ComplexStructImpl() {
            this.value2IndexMap = new HashMap<>();
            this.arr = new Object[10];
        }

        @Override
        public boolean insert(T val) {
            if (value2IndexMap.containsKey(val))
                return false;
            arr[size++] = val;
            value2IndexMap.put(val, size - 1);
            return true;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(T val) {
            if (!value2IndexMap.containsKey(val))
                return false;
            //修改映射关系，分为两步：1。删除index的映射关系 2。维护交换位置导致映射关系的改变
            Integer index = value2IndexMap.remove(val);
            value2IndexMap.put((T) this.arr[size - 1], index);

            swap(arr, index, size - 1);
            size--;
            return true;
        }

        @Override
        public T get(T val) {
            return val;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T getRandom() {
            Random random = new Random();
            int index = random.nextInt(size);
            return (T) this.arr[index];
        }


        void swap(Object[] arr, int i, int j) {
            Object temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


    /**
     * 按照自己的逻辑实现
     * 使用了类似于数组的结构，
     *
     * @param <T>
     */
    private static class MyComplexStructImpl<T> implements IComplexStruct<T> {

        Map<T, Integer> valueIndexMap;
        Entry<T>[] arr;


        @SuppressWarnings("unchecked")
        public MyComplexStructImpl() {
            this.valueIndexMap = new HashMap<>();
            arr = new Entry[16];
        }

        @Override
        public boolean insert(T val) {
            int index = val.hashCode() % arr.length;
            this.valueIndexMap.put(val, index);
            this.arr[index] = new Entry<>(val, this.arr[index]);
            return true;
        }

        @Override
        public boolean remove(T val) {
            int index = val.hashCode() % arr.length;
            Entry<T> cur = this.arr[index], pre = null;

            while (cur != null) {
                if (!cur.value.equals(val)) {
                    pre = cur;
                    cur = cur.next;
                    continue;
                }

                //删除逻辑
                if (pre == null)
                    this.arr[index] = null;
                else
                    pre.next = cur.next;
            }
            //删除映射关系
            this.valueIndexMap.remove(val);
            return true;
        }

        @Override
        public T get(T val) {
            int index = val.hashCode() % arr.length;
            Entry<T> temp = this.arr[index];
            while (temp != null) {
                if (val.equals(temp.value)) {
                    return temp.value;
                }
                temp = temp.next;
            }
            return null;
        }

        @Override
        public T getRandom() {
            List<Integer> list = new ArrayList<>(valueIndexMap.values());
            Random random = new Random();
            int index = random.nextInt(list.size());
            //没有实现随机
            return this.arr[list.get(index)].value;
        }

        private static class Entry<T> {
            T value;
            Entry<T> next;

            public Entry(T value, Entry<T> next) {
                this.value = value;
                this.next = next;
            }
        }
    }


    /**
     * 设计一个支持在平均时间复杂度O（1）下，执行一下操作数据结构
     */
    private interface IComplexStruct<T> {
        /**
         * 当元素不存在时，向集合中插入该项
         */
        @SuppressWarnings("UnusedReturnValue")
        boolean insert(T val);

        /**
         * 元素value存在时，从集合中移除该项
         */
        boolean remove(T val);


        /**
         *
         */
        T get(T val);

        /**
         * 随机返回现有集合中的一项，每个元素应该有相同的概率被返回
         */
        T getRandom();
    }

}
