package util.state;

import component.Component;

public class Started implements ComponentState {

    public void nextState(Component component) {
        component.setState(new Stopped());
    }

    public void prevState(Component component) {
        component.setState(new Stopped());
    }

    public void showState(Component component) {
        System.out.println(component.getState());
    }
}
