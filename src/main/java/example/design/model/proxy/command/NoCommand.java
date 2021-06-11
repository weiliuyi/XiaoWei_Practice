package example.design.model.proxy.command;

/**
 * @ClassName NoCommand
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/10 8:10 下午
 **/
public class NoCommand implements Command{

    @Override
    public void execute() {
        System.out.println("do nothing .......");
    }
}
