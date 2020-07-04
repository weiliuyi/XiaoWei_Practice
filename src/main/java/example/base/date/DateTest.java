package example.base.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
    public void test2() {
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

    }


    @Test
    public void test3 () {
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).
                toInstant(ZoneOffset.UTC).toEpochMilli());
        System.out.println(LocalDateTime.now().
                truncatedTo(ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC)* 1000);
    }


}
