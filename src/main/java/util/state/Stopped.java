package util.state;

import component.Component;

public class Stopped implements ComponentState {

    public void nextState(Component component) {
        component.setState(new Started());
    }

    public void prevState(Component component) {
        component.setState(new Started());
    }

    public void showState(Component component) {
        System.out.println(component.getState());
    }
}
