package component;

import util.state.Started;
import util.state.Stopped;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import runtime.Inject;
import runtime.Logger;

public class ThreadComponent extends Thread {

    @Inject
    private Logger mylog;

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
            component.setState(new Started());
            this.startMethod.invoke(null, getName(), 0);
            mylog.sendLog("Prozess gestartet");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            mylog.sendLog("Starten fehlgeschlagen");
        }
    }

    public void stopThreadComponent() throws InvocationTargetException, IllegalAccessException {
        this.stopMethod.invoke(null);
        this.component.setState(new Stopped());
        mylog.sendLog("Prozess gestoppt");
    }
}
