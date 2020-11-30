package component;

import util.state.ComponentState;
import util.state.Stopped;

// component.Component which gets added to the RuntimeEnvironment
public class Component {

    private ComponentState state = new Stopped(); //init as stopped since that's the first state of a component

    public ComponentState getState() {
        return state;
    }

    public void setState(ComponentState state) {
        this.state = state;
    }
}
