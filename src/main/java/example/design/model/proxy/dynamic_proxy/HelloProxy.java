package example.design.model.proxy.dynamic_proxy;

import example.design.model.proxy.ISayHello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--27 11:07
 **/
public class HelloProxy implements InvocationHandler {

    private ISayHello isayHello;

    public HelloProxy() {
    }

    public HelloProxy (ISayHello iSayHello) {
        this.isayHello = iSayHello;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method);
        System.out.println("HelloProxy .......before");
        System.out.println( method.invoke(isayHello,args));
        System.out.println("HelloProxy .......After");



        return "invocationHandler Invoke!!!";
    }
}
