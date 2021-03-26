package example.base.date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class DateTest {



    
    @Test
    public void test1 () {
        long now = Instant.now().toEpochMilli();
        System.out.println("当前时间的毫秒值" + now);
        //日期 + 小时时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTie: " + localDateTime);
        //coordinate universal time UTC 也是GMT(格林威治标准时间)
        System.out.println(localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000);
        System.out.println(localDateTime.atZone(   //和上边下叉八个各校；
                Clock.systemDefaultZone().getZone()).toInstant().toEpochMilli());
        System.out.println( LocalDateTime.now().
                toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

        //日期
        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate: " + localDate);

        //小时时间
        LocalTime localTime = LocalTime.now();
        System.out.println("LocalTime: " + localTime);

    }

    @Test
    public void test2() throws InterruptedException {
        System.out.println(Clock.systemDefaultZone().getZone());
        //LocalDateTime now = LocalDateTime.now(ZoneId);
        //System.out.println(now);
        System.out.println("now ZoneOffset 8 " +  LocalDateTime.now().
                toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

        System.out.println("Date :" + new Date());
        System.out.println("new Date().getTime : " + new Date().getTime());

        String formatter = LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
        System.out.println("YYYY-MM-dd HH:mm:ss " + formatter);

        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now.getZone());
        System.out.println(now.toInstant().toEpochMilli());
        //Duration
        Instant start = Instant.now();
        Thread.sleep(1500);
        long l = Duration.between(start, Instant.now()).toMillis();
        System.out.println(l);

    }


    @Test
    public void test3 () {
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).
                toInstant(ZoneOffset.UTC).toEpochMilli());
        System.out.println(LocalDateTime.now().
                truncatedTo(ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC)* 1000);
    }


    @Test
    public void test4 () {
        System.out.println(new Date());
        System.out.println();
        System.out.println(Instant.now().toEpochMilli());

        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(new Date(Instant.now().toEpochMilli()));

        System.out.println(new Date(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).
                toInstant(ZoneOffset.UTC).toEpochMilli()));

    }

    @Test
    public void test5 () {
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).
                toInstant(ZoneOffset.UTC).toEpochMilli());
        System.out.println(new Date(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).
                toInstant(ZoneOffset.UTC).toEpochMilli()));

    }


    /**
     * java 程序运行环境是 CST 时区，mysql 服务是 UTC 时区。存储数据时，Date 类型存储会减去 8 小时，LocalDateTime 存储会按照 CST 直接存储。
     问题 1: 那么在不修改环境时区的情况下，这两种存储按说哪个是对的？

     问题 2: 现在读取时 Date 格式的展示正确，LocalDateTime 展示时会再加上 8 小时导致时间快 8 小时。怎么正确的解决这个问题。。
     */
    @Test
    public void test6 () {
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        long dateTime = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(new Date(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
        System.out.println(DateUtil.beginOfDay(new Date()).toJdkDate());
        System.out.println(new Date());
    }


    @Test
    public void test8 () throws Exception {
        URL url = new URL("http://funlife-api.58.com/customer/userinfo/link?userId=3331");
        System.out.println("query =  " + url.getQuery());
        System.out.println("Authority = " + url.getAuthority());
        System.out.println("host =" + url.getHost());
        System.out.println("Port =" + url.getPort());
        System.out.println("Protocol  =" + url.getProtocol());
        System.out.println("path = " + url.getPath());
        System.out.println("userInfo " + url.getUserInfo());
        String path = url.getPath();
        if (path != null) {
            System.out.println(path.substring(1,path.length()));
        }
    }

    @Test
    public void test11 () {
        String s = DateUtil.beginOfDay(new Date()).toString();
        System.out.println(s);

        String format = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        System.out.println(format);

        System.out.println(defaultFormatDate(new Date()));
        System.out.println("------------------------------------");

        System.out.println(defaultFormatDate(new Date(System.currentTimeMillis()-  (long)1000 * 3600 * 24  * 60)));
        System.out.println(defaultFormatDate(new Date(System.currentTimeMillis())));

        System.out.println("-----------");
        int in =  1000 * 3600 * 24  * 12;
        System.out.println(in);
        long in60 =  (long)1000 * 3600 * 24  * 60;
        System.out.println(in60);


        System.out.println(Integer.MAX_VALUE);
    }


    private String defaultFormatDate (Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

class  MyObject{
      private  boolean r;

    public boolean isR() {
        return r;
    }

    public void setR(boolean r) {
        this.r = r;
    }
}

    @Test
    public void test7 () {
        MyObject myObject = new MyObject();
        System.out.println(myObject.isR());
        System.out.println(JSON.toJSONString(myObject));

    }

    @Test
    public void test9 () {
        System.out.println(new Date(System.currentTimeMillis() + 100000000));
        System.out.println(get(new Date(System.currentTimeMillis() + 100000000),new Date()));
    }

    private String get(@Past Date start, @PastOrPresent Date end) {
        Set<String> rangeToSet = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR).stream()
                .map(DateUtil::formatDate).collect(toSet());
        System.out.println(rangeToSet);
        return rangeToSet.toString();
    }


    @Test
    public void test10 () {
        BigDecimal one = new BigDecimal(1000.11);
        BigDecimal two = new BigDecimal(100);

        System.out.println(one.divide(two).toPlainString());
        System.out.println(one.divide(two).toString());
        System.out.println(one.divide(two).setScale(2,BigDecimal.ROUND_DOWN).toPlainString());
       System.out.println(one.divide(two).setScale(2,BigDecimal.ROUND_DOWN).toString());
    }

    @Test
    public void test12 () {
        StringBuilder sb = new StringBuilder("shopId = ? and");
        int length = sb.length();
        sb.delete(length-4,length);
        System.out.println(sb);
    }

    @Test
    public void test13 () {
        StringBuilder sb = new StringBuilder("shopId = ?,");
        int length = sb.length();
       sb.deleteCharAt(length-1);
        System.out.println(sb);
    }

    @Test
    public void test14 () {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(5);
        integers.add(7);
        String collect = integers.stream().
                map(String::valueOf)
                .collect(joining(",", "(", ")"));
        System.out.println(collect);
    }

    @Test
    public void test15 () {
        String phone = "18210074459";
        System.out.println(phone.replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1****$2"));
    }

    @Test
    public void date () {
        System.out.println(new Date(System.currentTimeMillis()- 1000 * 60 * 60 * 24 * 15));
    }


    @Test
    public void test17 () {
        Long l1 = 11111L;
        Long l2 = 11111L;
        System.out.println(Objects.equals(l1,l2));
        ArrayList<Object> objects = new ArrayList<>(new HashSet<>());
    }

    @Test
    public void test18 () {
        Date date = new Date(1604160000000L);
        System.out.println(date);

        Date date2 = new Date(1606665600000L);
        System.out.println(date2);
    }

    @Test
    public void test19 () {
        System.out.println(DateUtil.beginOfDay(new Date()).toTimestamp().toInstant().toEpochMilli());
        System.out.println();
        System.out.println(new Date().toInstant().toEpochMilli());
        System.out.println(System.currentTimeMillis());
    }


    @Test
    public void test20 () {
        People w1 = new People(20, "w");
        People w2 = new People(19, "l");
        People w3 = new People(25, "y");
        People w4 = new People(16, "1");
        List<People> collect = Arrays.asList(w1, w2, w3, w4).stream().sorted(Comparator.comparing(People::getAge).reversed()).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
        List list = collect;
        System.out.println(JSON.toJSONString(list));


    }


    @Test
    public void test21 () {
        String date = "2020-12-07";
        DateTime parse = DateUtil.parse(date);
        System.out.println(parse);
        DateTime dateTime1 = DateUtil.offsetDay(parse, -7);
        System.out.println(dateTime1);


        DateTime dateTime = DateUtil.lastWeek();
        System.out.println(dateTime);

    }


    @Test
    public void test22 () {
        DateTime dateTime = DateUtil.beginOfMonth(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        System.out.println(simpleDateFormat.format(dateTime));
    }

    @Test
    public void test23 () {
        Long now = 99L;
        Long before = 66L;
        double v = new BigDecimal((Double.valueOf(now) - before) / now * 100).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue();
        System.out.println(v);
    }

    @Test
    public void test24 () {
        Double aDouble = calculateIncreasePercent(90.0, 0.0, Function.identity());
        System.out.println(aDouble);
        String beginMonthDayStr = "2020-12-15";
        DateTime dateTime = DateUtil.parse(beginMonthDayStr);
        String format = DateUtil.format(dateTime, "MM-dd");
        System.out.println(format);
    }



    @Test
    public void test25 () throws ParseException {
        DateTime parse = DateUtil.parse("2020-12-05");
        System.out.println(DateUtil.offsetDay(parse,-7));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse1 = sdf.parse("2020-12-05");
        System.out.println(parse1);
    }


    @Test
    public void test27() {
        DateTime dateTime = DateUtil.parseDate("2020-12-15");
        Date date = new Date(dateTime.getTime());
        System.out.println(date);

        DateTime dateTime2 = DateUtil.endOfMonth(new Date());
        String format = DateUtil.format(dateTime2, "yyyy-MM-dd");
        System.out.println(format);

        System.out.println(new Date(1608282667000L));

    }

   @Test
   public void test26 () {
       String substring = "2020-11-30".substring(0, 7);
       System.out.println(substring);
   }

    /**
     * 计算增长的百分比
     */
    private <T,N extends Number> Double calculateIncreasePercent (T now, T before,Function<T,N> function) {
        N nowNum = function.apply(now);
        N beforeNum = function.apply(before);
        if (nowNum == null ) { //只要是当前数据为null,
            return Double.valueOf("0.00");
        }
        // now 不等于null时
        if (beforeNum == null || beforeNum.doubleValue() == 0) {
            return Double.valueOf("1.00");
        }
        return  new BigDecimal((nowNum.doubleValue() - beforeNum.doubleValue()) / beforeNum.doubleValue()).
                setScale(5,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}


 class Sum{
    public static void main(String [] args){
        int j=10;
        System.out.println("j is : "+j);
        calculate(j);
        System.out.println("At last, j is : "+j);
    }
    static void calculate (int j){
        for (int i = 0;i<10;i++)
            j++;
        System.out.println("j in calculate() is: "+j);
    }
}



class People {
    private Integer age;

    private String name;

    public People(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
