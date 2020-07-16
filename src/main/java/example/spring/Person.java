package example.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Person implements BeanPostProcessor,
        BeanNameAware,
        BeanFactoryAware ,
        ApplicationContextAware ,
        InitializingBean,
        DisposableBean {

    private int id;
    private String name;
    private int age;
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

   // public void

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("BeanPostProcessor Interface  PostProcessBeforeInitialization ");
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("BeanPostProcessor Interface  PostProcessAfterInitialization" );
        return o;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware Interface " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware Interface setBeanFactory ");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware Interface setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean Interface afterPropertiesSet ");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean Interface destroy");
    }
}
