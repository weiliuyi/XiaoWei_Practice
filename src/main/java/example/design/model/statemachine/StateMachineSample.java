package example.design.model.statemachine;

import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--06 17:27
 **/
public class StateMachineSample extends AbstractStateMachine<StateMachineSample,DoorState,DoorEvent,String> {

    public void openDoorAction (DoorState from,DoorState to,DoorEvent event,String context) {
        System.out.println(" openDoorAction  Transition from '"+from+"' to '"+to+"' on event '"+event+ "' with context '"+context+"'.");
    }

    public void closeDoorAction(DoorState from,DoorState to,DoorEvent event,String context){
        System.out.println("closeDoorAction Transition from '"+from+"' to '"+to+"' on event '"+event+ "' with context '"+context+"'.");
    }

    public void openStateEntry (DoorState from,DoorState to,DoorEvent event,String context) {
        System.out.println("openStateEntry '"+from+"' to '"+to+"' on event '"+event+
                "' with context '"+context+"'.");
    }

    protected void closeStateExit(DoorState from, DoorState to, DoorEvent event, String context) {
        System.out.println("closeStateExit '"+from+"' to '"+to+"' on event '"+event+
                "' with context '"+context+"'.");
    }



}
