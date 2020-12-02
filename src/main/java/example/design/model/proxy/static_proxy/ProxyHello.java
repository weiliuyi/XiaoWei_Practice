package example.design.model.proxy.static_proxy;

import example.design.model.proxy.ISayHello;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--27 11:00
 **/
public class ProxyHello implements ISayHello {

    private ISayHello sayHello;

    public ProxyHello(ISayHello sayHello) {
        this.sayHello = sayHello;
    }

    @Override
    public String sayHello() {
        System.out.println("sayHello before !!");
        System.out.println(sayHello.sayHello());
        System.out.println("sayHello after !!!");
        return "proxy sayHello";
    }
}
