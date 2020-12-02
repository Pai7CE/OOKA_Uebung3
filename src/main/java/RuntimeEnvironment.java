import component.Component;
import component.ThreadComponent;
import util.ComponentContainer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class RuntimeEnvironment {

    private ComponentContainer componentContainer;
    private List<ThreadComponent> threadList;
    private int threadCounter = 0;

    // initializes needed
    public void start() {
        componentContainer = new ComponentContainer();
        threadList = new ArrayList<>();
    }

    public void stop(){
        // TODO: implement
    }

    public void addComponentRE(Component component){
        if(componentContainer.getComponentList().size() == 0){
            componentContainer.add(component);
            System.out.println("Component added ");
        }

        for(Component com : componentContainer.getComponentList()){
            if(com.getName().equals(component.getName())){
                System.out.println("Component won't be added since it already exists");
            }
            else{
                componentContainer.add(component);
                System.out.println("Component added ");
            }
        }
    }

    public void removeComponentRE(String fullComponentName){
        for(Component component : componentContainer.getComponentList()){
            if(component.getName().equals(fullComponentName)){
                componentContainer.remove(component);
                System.out.println("Component removed: " + fullComponentName);
            }
            else{
                System.out.println("component isn't added to RE");
            }
        }
    }

    public void startComponent(String fullComponentName){
        for(Component component : componentContainer.getComponentList()){
            if(component.getName().equals(fullComponentName)){
                ThreadComponent threadComponent =
                        new ThreadComponent(component.getName().substring(0, component.getName().length()-4) + "_" + threadCounter, component);
                threadCounter++;
                threadList.add(threadComponent);
                threadComponent.start();
                System.out.println("Component started: " + fullComponentName);
            }
            else{
                System.out.println("Component doesn't exist");
            }
        }
    }

    public void stopComponent(String fullThreadName) throws InvocationTargetException, IllegalAccessException {
        for(ThreadComponent threadComponent : threadList){
            if(threadComponent.getName().equals(fullThreadName)){
                threadComponent.stopThreadComponent();
                threadComponent.interrupt(); // stopping the actual thread
                System.out.println("Thread stopped: " + fullThreadName);
            }
            else{
                System.out.println("Thread doesn't exist");
            }
        }
    }

    public ComponentContainer getComponentContainer() {
        return componentContainer;
    }
}
