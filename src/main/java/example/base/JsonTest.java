package example.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 *  JsonFile 注解可以使用在方法上
 * https://www.jinchengcom.cn/posts/dc39326b.html fastjson定制化进行序列化
 * @author: weiliuyi
 * @create: 2020--16 17:57
 **/

public class JsonTest {
    public static void main(String[] args) {
       /* MyJson myJson = new MyJson();
        myJson.setAge(11);
        System.out.println(JSON.toJSONString(myJson));*/
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("example.base");
        MyJson bean = context.getBean(MyJson.class);
        System.out.println(JSON.toJSONString(bean));
    }
}

@Component
class MyJson {

    private int age;

    @PostConstruct
    public void testPostConstruct()  {
        System.out.println("hello,I ma postConstruct注解");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JSONField(serialize = false)
    public String getHelloWorld () {
        return "Hello world!!!";
    }
}


