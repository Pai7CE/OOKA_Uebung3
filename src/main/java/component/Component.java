package component;

import cli.GUI;
import util.state.ComponentState;
import util.state.Stopped;

import java.lang.reflect.Method;
import java.util.HashMap;

// component.Component which gets added to the RuntimeEnvironment
public class Component {

    private ClassLoader classLoader;
    private ComponentState state;
    private HashMap<String, Class> classMap;
    private String name;
    private Method startMethod;
    private Method stopMethod;

    public Component(ClassLoader classLoader, HashMap<String, Class> classMap, String name, Method startMethod, Method stopMethod) {
        this.classLoader = classLoader;
        this.classMap = classMap;
        this.name = name;
        this.startMethod = startMethod;
        this.stopMethod = stopMethod;
        this.state = new Stopped(); // init as stopped
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public ComponentState getState() {
        return state;
    }

    public HashMap<String, Class> getClassMap() {
        return classMap;
    }

    public String getName() {
        return name;
    }

    public Method getStartMethod() {
        return startMethod;
    }

    public Method getStopMethod() {
        return stopMethod;
    }

    public void nextState() {
        this.state.nextState(this);
    }

    public void setState(ComponentState state) {
        this.state = state;
    }
}
