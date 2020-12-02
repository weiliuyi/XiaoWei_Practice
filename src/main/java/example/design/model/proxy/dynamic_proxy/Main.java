package example.design.model.proxy.dynamic_proxy;

import example.design.model.proxy.ISayHello;
import example.design.model.proxy.SayHelloImpl;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--27 11:09
 **/
public class Main {
    public static void main(String[] args) {
        SayHelloImpl helloObj = new SayHelloImpl();
        HelloProxy helloProxy = new HelloProxy(helloObj);
        ISayHello sayHello = (ISayHello) Proxy.newProxyInstance(helloProxy.getClass().getClassLoader(), helloObj.getClass().getInterfaces(), helloProxy);
        System.out.println(sayHello.sayHello());
        System.out.println("main proxy = " + sayHello);
    }
}
