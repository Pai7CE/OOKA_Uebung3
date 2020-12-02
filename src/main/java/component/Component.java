package component;

import cli.GUI;
import util.state.ComponentState;
import util.state.Stopped;

import java.util.HashMap;

// component.Component which gets added to the RuntimeEnvironment
public class Component {

    private ComponentState state; //init as stopped since that's the first state of a component
    private HashMap<String, Class> classMap;
    private GUI gui;
    private String name;

    public Component(HashMap<String, Class> classMap, String name) {
        this.classMap = classMap;
        this.name = name;
        this.gui = new GUI(name);
        this.state = new Stopped(); // init state is stopped
    }

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

    public void print(String s){
        gui.print(s);
    }
}
