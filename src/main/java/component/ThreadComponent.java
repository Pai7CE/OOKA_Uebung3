package component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ThreadComponent extends Thread {

    private volatile boolean stop = false;
    private Component component;
    private Method startMethod;
    private Method stopMethod;

    public ThreadComponent(String name, Component c) {
        this.setName(name);
        this.component = c;
        this.setContextClassLoader(c.getClassLoader());
        this.startMethod = c.getStartMethod();
        this.stopMethod = c.getStopMethod();
    }

    public Component getComponent() {
        return component;
    }

    public void run() {
        try {
            Object startReturnedObject = this.startMethod.invoke(null, getName(), 0);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void stopThreadComponent() throws InvocationTargetException, IllegalAccessException {
        //TODO
        this.stopMethod.invoke(null);
        this.component.nextState();
    }
}
