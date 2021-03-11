package example.design.model.statemachine;

import org.junit.Test;
import org.squirrelframework.foundation.fsm.Condition;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--06 17:35
 **/
public class MachineTest {
    @Test
    public void test1 () {
        StateMachineBuilder<StateMachineSample, DoorState, DoorEvent, String> machineBuilder = StateMachineBuilderFactory.create(StateMachineSample.class, DoorState.class, DoorEvent.class, String.class);
        //开灯流程
        machineBuilder.externalTransition().from(DoorState.CLOSE).to(DoorState.OPEN).on(DoorEvent.OPENDOOR).when(new Condition<String>() {
            @Override
            public boolean isSatisfied(String s) {
                return s.contains("wly test");
            }

            @Override
            public String name() {
                return "wly  name";
            }
        }).callMethod("openDoorAction");
        //关灯流程
        machineBuilder.externalTransition().from(DoorState.OPEN).to(DoorState.CLOSE).on(DoorEvent.CLOSEDOOR).callMethod("closeDoorAction");
        machineBuilder.onEntry(DoorState.OPEN).callMethod("openStateEntry");
        machineBuilder.onEntry(DoorState.CLOSE).callMethod("closeStateExit");

        StateMachineSample sms = machineBuilder.newStateMachine(DoorState.CLOSE);

        sms.fire(DoorEvent.OPENDOOR,"wly test");
        sms.fire(DoorEvent.CLOSEDOOR,"wly test");

        System.out.println("Current state is "+ sms.getCurrentState());
    }
}
