package runtime;

import component.Component;
import component.ThreadComponent;
import util.ComponentContainer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RuntimeEnvironment {

    private ComponentContainer componentContainer;
    private List<ThreadComponent> threadList;
    private int threadCounter = 0;
    // for recovery file
    private boolean recovery = false;
    private BufferedWriter bw;
    private File recoveryFile;


    // initializes needed
    public String startRE() throws IOException {
        if(componentContainer == null){
            componentContainer = new ComponentContainer();
        }
        if(threadList == null){
            threadList = new ArrayList<>();
        }
        logToFile("startRE","");
        return "Runtime environment started.";
    }
    //stops RE and all threads
    public void stopRE() throws InvocationTargetException, IllegalAccessException, IOException {
        if(bw != null){
            bw.close();
        }
        recoveryFile.delete(); //delete upon successful stopping of the JRE
        if(threadList != null){
            for(ThreadComponent threadComponent : threadList){ //stopping all running threads
//                threadComponent.stop();
                threadComponent.stopThreadComponent();
                threadComponent.interrupt();
            }
        }
    }

    public String addComponentRE(Component component) throws IOException {
        for(Component com : componentContainer.getComponentList()){
            if(com.getName().equals(component.getName())){
                return "Component won't be added since it already exists.";
            }
        }
        componentContainer.add(component);
        logToFile("addComponentRE",component.getName());
        return "Component added.";
    }

    public String removeComponentRE(String fullComponentName) throws IOException {
        for(Component component : componentContainer.getComponentList()){
            if(component.getName().equals(fullComponentName)){
                componentContainer.remove(component);
                logToFile("removeComponentRE",fullComponentName);
                return "Component removed: " + fullComponentName;
            }
        }
        return "component isn't added to RE";
    }

    public String startComponent(String fullComponentName) throws IOException {
        for(Component component : componentContainer.getComponentList()){
            if(component.getName().equals(fullComponentName)){
                // Thread name cuts the .jar ending and adds an identifying number
                for(ThreadComponent thread : threadList){
                    if(thread.getName().equals(component.getName())){
                        return "Component already running";
                    }
                }
                ThreadComponent threadComponent =
                        new ThreadComponent(component.getName(), component);
                threadList.add(threadComponent);
                threadCounter++;
                threadComponent.start();
                logToFile("startComponent",fullComponentName);
                return "Component started: " + fullComponentName;
            }
        }
        return "Component doesn't exist";
    }

    public String stopComponent(String fullThreadName) throws InvocationTargetException, IllegalAccessException, IOException {
        for(ThreadComponent threadComponent : threadList){
            if(threadComponent.getName().equals(fullThreadName)){
                threadComponent.stopThreadComponent(); // Class method used to stop infinite loop and result in thread stopping
                threadList.remove(threadComponent);
                logToFile("stopComponent",fullThreadName);
                return "Thread stopped: " + threadComponent.getName();
            }
        }
        return "Thread doesn't exist";
    }
    // gets states of the different running threads
    public String getStates(){
        String states = "";
        for(Component component : componentContainer.getComponentList()){
            states += component.getName() + ": " + component.getState().showState()+"\n";
        }
        return states;
    }

    public void crashRE(){
        System.exit(0);
    }

    public ComponentContainer getComponentContainer() {
        return componentContainer;
    }

    public void setRecovery(boolean recovery) {
        this.recovery = recovery;
    }

    public void setRecoveryFile(File recoveryFile) {
        this.recoveryFile = recoveryFile;
    }

    private void logToFile(String arg1, String arg2) throws IOException {
        if(!recovery){
            if(bw == null){
                bw = new BufferedWriter(new FileWriter(recoveryFile, true));
            }

            bw.append(arg1+";"+arg2);
            bw.newLine();
            bw.flush(); // outputting to file
        }
    }
}
