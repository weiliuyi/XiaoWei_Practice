package example.design.model.proxy.static_proxy;

import example.design.model.proxy.SayHelloImpl;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--27 11:01
 **/
public class Main {
    public static void main(String[] args) {
        ProxyHello proxy = new ProxyHello(new SayHelloImpl());
        System.out.println(proxy.sayHello());
    }
}
