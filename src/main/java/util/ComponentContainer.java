package util;

import component.Component;

import java.util.ArrayList;
import java.util.List;

// This container contains all used components
public class ComponentContainer {
    private List<Component> componentList;

    public ComponentContainer() {
        componentList = new ArrayList<Component>(); //initializing List
    }

    public void add(Component component){
        componentList.add(component);
    }

    public void remove(Component component){
        componentList.remove(component); //TODO: Implement equals check maybe?
    }

    public List<Component> getComponentList() {
        return componentList;
    }
}
