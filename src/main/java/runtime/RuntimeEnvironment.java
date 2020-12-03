package runtime;

import component.Component;
import component.ThreadComponent;
import util.ComponentContainer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RuntimeEnvironment {

    private ComponentContainer componentContainer;
    private List<ThreadComponent> threadList;
    private int threadCounter = 0;

    // initializes needed
    public String start() {
        componentContainer = new ComponentContainer();
        threadList = new ArrayList<>();
        return "Runtime Environment started";
    }

    public String stop() throws InvocationTargetException, IllegalAccessException {
        for(ThreadComponent threadComponent : threadList){
            threadComponent.stopThreadComponent();
        }
        return "";
    }

    public String addComponentRE(Component component){
        for(Component com : componentContainer.getComponentList()){
            if(com.getName().equals(component.getName())){
                return "Component won't be added since it already exists";
            }
        }
        componentContainer.add(component);
        return "Component added";
    }

    public String removeComponentRE(String fullComponentName){
        for(Component component : componentContainer.getComponentList()){
            if(component.getName().equals(fullComponentName)){
                componentContainer.remove(component);
                return "Component removed: " + fullComponentName;
            }
        }
        return "component isn't added to RE";
    }

    public String startComponent(String fullComponentName){
        for(Component component : componentContainer.getComponentList()){
            if(component.getName().equals(fullComponentName)){
                // Thread name cuts the .jar ending
                ThreadComponent threadComponent =
                        new ThreadComponent(component.getName().substring(0, component.getName().length()-4) + threadCounter, component);
                threadList.add(threadComponent);
                threadCounter++;
                threadComponent.start();
                return "Component started: " + fullComponentName;
            }
        }
        return "Component doesn't exist";
    }

    public String stopComponent(String fullThreadName) throws InvocationTargetException, IllegalAccessException {
        for(ThreadComponent threadComponent : threadList){
            if(threadComponent.getName().equals(fullThreadName)){
                threadComponent.stopThreadComponent();
                threadComponent.interrupt(); // stopping the actual thread
                return "Thread stopped: " + fullThreadName;
            }
        }
        return "Thread doesn't exist";
    }

    public String getStates(){
        String states = "";
        for(ThreadComponent threadComponent : threadList){
            states += threadComponent.getComponent().getState().showState() + "\n";
        }
        return states;
    }

    public ComponentContainer getComponentContainer() {
        return componentContainer;
    }
}
