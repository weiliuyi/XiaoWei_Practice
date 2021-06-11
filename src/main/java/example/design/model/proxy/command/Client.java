package example.design.model.proxy.command;

import example.design.model.proxy.command.concret.LightOffCommand;
import example.design.model.proxy.command.concret.LightOnCommand;
import example.design.model.proxy.command.entity.Light;

/**
 * @ClassName Client
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/10 8:24 下午
 **/
public class Client {
    public static void main(String[] args) {
        Remote remote = new Remote();
        Light light = new Light();
        Command offCommand = new LightOffCommand(light);
        Command onCommand = new LightOnCommand(light);

        remote.setOffCommand(0,offCommand);
        remote.setOnCommand(0,onCommand);

        remote.offEvent(0);
        remote.onEvent(0);

    }
}
