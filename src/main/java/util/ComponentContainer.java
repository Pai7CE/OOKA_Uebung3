package util;

import component.Component;

import java.util.ArrayList;
import java.util.List;

// This container contains all used components
public class ComponentContainer {
    private List<Component> componentContainer;

    public ComponentContainer() {
        componentContainer = new ArrayList<Component>(); //initializing List
    }

    public void add(Component component){
        componentContainer.add(component);
    }

    public void remove(Component component){
        componentContainer.remove(component); //TODO: Implement equals check maybe?
    }

    public List<Component> getComponentContainer() {
        return componentContainer;
    }
}
