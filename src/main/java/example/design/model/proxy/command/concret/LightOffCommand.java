package example.design.model.proxy.command.concret;

import example.design.model.proxy.command.Command;
import example.design.model.proxy.command.entity.Light;

/**
 * @ClassName LightOffCommand
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/10 8:08 下午
 **/
public class LightOffCommand  implements Command {

    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.offLight();
    }
}
