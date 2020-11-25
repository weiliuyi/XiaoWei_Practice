package example.spring.validation;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @description: validation test
 * @author: weiliuyi
 * @create: 2020--23 11:35
 **/
@Component
public class ValidationTest {

    @Test
    public void test1 () {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("example.spring.validation");
        ValidationTest bean = applicationContext.getBean(ValidationTest.class);
        DateTest dateTest = new DateTest();
        dateTest.setPast(new Date(System.currentTimeMillis()+1000));
        dateTest.setFuture(new Date(System.currentTimeMillis()-1000));
        System.out.println(JSON.toJSONString(bean.test(dateTest)));

    }

    public static DateTest test(@Validated DateTest dateTest) {
        return dateTest;
    }

}


class DateTest {

    @Past
    private Date past;
    @Future
    private Date future;

    public Date getPast() {
        return past;
    }

    public void setPast(Date past) {
        this.past = past;
    }

    public Date getFuture() {
        return future;
    }

    public void setFuture(Date future) {
        this.future = future;
    }
}
