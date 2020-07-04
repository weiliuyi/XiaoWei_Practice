package example.classloader;



import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class ClassLoaderTest {

    @Test
    public void test2 () throws Exception {
        String path = "D:\\workspace\\XiaoWei_Practice\\target\\classes\\";
        MyClassLoader xiaoweiClassLoader = new MyClassLoader(path, "xiaowei");
        MyClassLoader xiaowangClassLoader = new MyClassLoader(path, "xiaowang");
        Class<?> clazz = xiaoweiClassLoader.findClass("example.classloader.HelloClassLoader");
        @SuppressWarnings("unused")
        Object hello = clazz.newInstance();
    }

    public void test () throws Exception {
        String path = "D:\\workspace\\XiaoWei_Practice\\src\\main\\java\\";
        MyClassLoader xiaowangClassLoader = new MyClassLoader(path, "xiaowang");
        MyClassLoader xiaoweiClassLoader = new MyClassLoader(xiaowangClassLoader,path, "xiaowei");
        Class<?> clazz = xiaoweiClassLoader.findClass("example.classloader.HelloClassLoader");
        @SuppressWarnings("unused")
        Object hello = clazz.newInstance();
    }


    public static void main(String[] args) throws Exception {


        String path = "D:\\temp\\";
        MyClassLoader xiaowangClassLoader = new MyClassLoader(path, "xiaowang");
        System.out.println(xiaowangClassLoader.getParent());
        Class<?> clazz = xiaowangClassLoader.findClass("com.example.HelloClassLoader2");
        Object o = clazz.newInstance();

    }


}

@SuppressWarnings("unused")
public class HelloClassLoader {
    static{
        System.out.println("Hello Static " + HelloClassLoader.class.getClassLoader());
    }

    public HelloClassLoader() {
        System.out.println("Hello Construct " + this.getClass().getClassLoader());
    }
}


class MyClassLoader extends ClassLoader {
    private final String path;
    private final String classLoaderName;

    public MyClassLoader(String path, String classLoaderName) {
        super();
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(ClassLoader parent, String path, String classLoaderName) {
        super(parent);
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
       try {
           byte[] classData = loadClassData(name);
           return defineClass(name,classData,0,classData.length);
       } catch (Exception e) {

       }
       return null;
    }

    private byte[] loadClassData (String name) throws IOException {
        name = path + name.replaceAll("\\.","/") + ".class";
        System.out.println("<<<<<<<<<<<<name + " +  name);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(name);
            baos = new ByteArrayOutputStream();
            int i;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
        }catch (Exception e) {

        } finally {
            if (is != null) {
                is.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
        assert baos != null;
        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return "MyClassLoader{" +
                "path='" + path + '\'' +
                ", classLoaderName='" + classLoaderName + '\'' +
                '}';
    }
}



