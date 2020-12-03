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
    //stops RE and all threads
    public void stop() throws InvocationTargetException, IllegalAccessException {
        if(threadList != null){
            for(ThreadComponent threadComponent : threadList){ //stopping all running threads
                threadComponent.stopThreadComponent();
            }
        }
        System.exit(0);
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
                // Thread name cuts the .jar ending and adds an identifying number
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
                threadComponent.stopThreadComponent(); // Class method used to stop infinite loop and result in thread stopping
                threadList.remove(threadComponent);
                return "Thread stopped: " + threadComponent.getName();
            }
        }
        return "Thread doesn't exist";
    }
    // gets states of the different running threads
    public String getStates(){
        String states = "";
        for(ThreadComponent threadComponent : threadList){
            states += threadComponent.getName() + ": " + threadComponent.getComponent().getState().showState() + "\n";
        }
        return states;
    }

    public ComponentContainer getComponentContainer() {
        return componentContainer;
    }
}
