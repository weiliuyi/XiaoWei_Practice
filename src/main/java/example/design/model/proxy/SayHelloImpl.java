package example.design.model.proxy;

/**
 * @description: hello接口的实现类
 * @author: weiliuyi
 * @create: 2020--27 10:57
 **/
public class SayHelloImpl implements ISayHello {
    @Override
    public String sayHello() {
        return "Hello I'm sayHelloImpl !!!!!!";
    }
}
