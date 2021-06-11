package example.design.model.proxy.command;

import java.util.stream.IntStream;

/**
 * @ClassName Remote
 * @Description
 * @Author weiliuyi
 * @Date 2021/6/10 8:09 下午
 **/
public class Remote {

    private static final Command noCommand = new NoCommand();

    private static final int SLOT_COUNT = 8;

    private final Command[] onCommand;

    private final Command[] offCommand;

    public Remote() {
        onCommand = new Command[SLOT_COUNT];
        offCommand = new Command[SLOT_COUNT];
        IntStream.range(0, SLOT_COUNT).forEach(i -> {
            onCommand[i] = noCommand;
            offCommand[i] = noCommand;
        });
    }


    public void onEvent (int slot) {
        onCommand[slot].execute();
    }

    public void offEvent(int slot) {
        offCommand[slot].execute();
    }

    public void setOnCommand (int slot,Command command) {
        onCommand[slot] = command;
    }

    public void setOffCommand (int slot,Command command) {
        offCommand[slot] = command;
    }

}
