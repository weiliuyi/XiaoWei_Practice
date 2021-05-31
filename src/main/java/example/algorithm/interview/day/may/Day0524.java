package example.algorithm.interview.day.may;

import org.junit.Test;

/**
 * @ClassName Day0524 阶乘相关的问题
 * @Description https://mp.weixin.qq.com/s/qtdSnjSBZdP64YhpQIy0HA
 * https://blog.csdn.net/TommyZht/article/details/46309563
 * @Author weiliuyi
 * @Date 2021/5/31 2:08 下午
 **/
public class Day0524 {

    /**
     * 1. 阶乘后的零 (方法签名 trailZeros)
     * 2。阶乘后第K个零
     */
    @Test
    public void testFactorial() {
        MyTrailZeros myTrailZeros = new MyTrailZeros();
        System.out.println(myTrailZeros.trailZeros(25));
        MyTrailZerosV2 myTrailZerosV2 = new MyTrailZerosV2();
        System.out.println(myTrailZerosV2.trailZeros(25));
        System.out.println("-----------------------------");

        FinalTrailZeros finalTrailZeros = new FinalTrailZeros();
        System.out.println(finalTrailZeros.trailZeros(25));
        System.out.println("-----------------------------");
        SampleNumberSpecificZero sampleNumberSpecificZero = new SampleNumberSpecificZero(finalTrailZeros);
        System.out.println(sampleNumberSpecificZero.numberSpecificZero(31));
        System.out.println("-----------------------------------");
        FinalNumberSpecificNumber finalSpecific = new FinalNumberSpecificNumber(finalTrailZeros);
        System.out.println(finalSpecific.numberSpecificZero(31));
    }


    private static class FinalNumberSpecificNumber extends NumberSpecificZero {

        public FinalNumberSpecificNumber(TrailZero trailZero) {
            super(trailZero);
        }

        @Override
        long numberSpecificZero(int k) {
            return rightBound(k) - leftBound(k) + 1;
        }

        //求的最左边界
        private long leftBound(int k) {
            long left = 1, right = Long.MAX_VALUE;// 左闭右开[)
            while (left < right) {
                //long mid = (left + right) / 2; 这样做会发生溢出
                long mid = left + (right - left) / 2;
                if (trailZero.trailZeros(mid) > k) {
                    right = mid;
                } else if (trailZero.trailZeros(mid) < k) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }

        private long rightBound(int k) {
            long left = 1, right = Long.MAX_VALUE;
            while (left < right) {
                //long mid = (left + right) / 2;  这样做的坏处？ 会发生溢出
                long mid = left + (right - left) / 2;
                if (trailZero.trailZeros(mid) > k) {
                    right = mid;
                } else if (trailZero.trailZeros(mid) < k) {
                    left = mid + 1;
                } else {
                    left = mid + 1;
                }
            }
            return right-1;
        }
    }


    /**
     * 阶乘后第K个零
     */
    private static class SampleNumberSpecificZero extends NumberSpecificZero {


        public SampleNumberSpecificZero(TrailZero trailZero) {
            super(trailZero);

        }

        @Override
        public long numberSpecificZero(int k) {
            int count = 0;
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                if (trailZero.trailZeros(i) < k) {
                    continue;
                }
                if (trailZero.trailZeros(i) > k) {
                    break;
                }
                if (trailZero.trailZeros(i) == k) {
                    System.out.print(i + " ");
                    count++;
                }
            }
            System.out.println();
            return count;
        }
    }


    /**
     * 结果的末尾0 一定是是因子中存在2和5
     * 也就是说 问题转化为n!可以分解出多少个因子2和5？
     * 因子2一定比因子5的个数多，这是因为每个偶数都可以分解出因子2，所以主要取决于分解出几个因子5
     * 现在的问题转化为n!最多可以分解出多少个因子5？
     * <p>
     * 125 / 5 125 / 25  125 / 125
     */
    private static class FinalTrailZeros implements TrailZero {
//        @Override
//        public int trailZeros(long n) {
//            int res = 0;
//            for (long d = n; d / 5 > 0; d = d / 5) {
//                res += d / 5;
//            }
//            return res;
//        }

        //这个问题比较 好理解
        @Override
        public int trailZeros(long n) {
            int res = 0;
            long divisor = 5;
            while (divisor <= n) {
                res += n / divisor;
                divisor *= 5;
            }
            return res;
        }

    }


    /**
     * 自己的方案二 这个也是错误的
     */
    private static class MyTrailZerosV2 implements TrailZero {
        @Override
        public int trailZeros(long n) {
            int count = 0;
            long sum = 1;  // 其实sum只需要记录sum的2 或者5的倍数就行；
            for (int i = 1; i <= n; i++) {

                if (i % 2 != 0 && i % 5 != 0) continue; //如果一个数 既不是5的倍数，也不是2的倍数，那么直接跳过

                if (i % 10 == 0) {
                    count += i / 10;
                } else {
                    sum *= i;
                    if (sum % 10 == 0) {
                        count++;//但是依然包括除了 2 和 5以外的其他因数
                        sum = sum / 10;
                    }
                }
            }
            return count;
        }
    }

    /**
     * 自己的方案1
     * 这种方式是不正确的？主要错误在 sum可能有是100的倍数，但是只是统计了一个；
     */
    private static class MyTrailZeros implements TrailZero {
        @Override
        public int trailZeros(long n) {
            int count = 0;
            long sum = 1;
            for (int i = 1; i <= n; i++) {
                sum *= i;
                if (sum % 10 == 0) {
                    count++;
                    sum = sum / 10;
                }
            }
            return count;
        }
    }

    /**
     * 阶乘结果后面的零的个数
     */
    private static interface TrailZero {
        int trailZeros(long n);
    }


    /**
     * 有多少个n，是的n！结果末尾K个0
     */
    private static abstract class NumberSpecificZero {


        TrailZero trailZero;

        public NumberSpecificZero(TrailZero trailZero) {
            this.trailZero = trailZero;
        }

        abstract long numberSpecificZero(int k);
    }

}
