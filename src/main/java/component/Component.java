package component;

import util.state.ComponentState;
import util.state.Stopped;

import java.util.HashMap;

// component.Component which gets added to the RuntimeEnvironment
public class Component {

    private ComponentState state = new Stopped(); //init as stopped since that's the first state of a component
    private HashMap<String, Class> classMap;

    public HashMap<String, Class> getClassMap() {
        return classMap;
    }

    public void setClassMap(HashMap<String, Class> classMap) {
        this.classMap = classMap;
    }

    public ComponentState getState() {
        return state;
    }

    public void setState(ComponentState state) {
        this.state = state;
    }
}
