package util.state;

import component.Component;

public class Stopped implements ComponentState {

    public void nextState(Component component) {
        component.setState(new Started());
    }

    public void prevState(Component component) {
        component.setState(new Started());
    }

    public String showState(){
        return "Stopped";
    }
}
