package example.design.model.proxy.command.concret;

import example.design.model.proxy.command.Command;
import example.design.model.proxy.command.entity.Light;

/**
 * @ClassName LightOnCommand
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/10 8:07 下午
 **/
public class LightOnCommand  implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.onLight();
    }
}
