package component;

import util.state.ComponentState;
import util.state.Stopped;

// component.Component which gets added to the RuntimeEnvironment
public class Component {

    private ComponentState state = new Stopped(); //init as stopped since that's the first state of a component
    private Class javaClass;
    private String name;

    public Class getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(Class javaClass) {
        this.javaClass = javaClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentState getState() {
        return state;
    }

    public void setState(ComponentState state) {
        this.state = state;
    }
}
