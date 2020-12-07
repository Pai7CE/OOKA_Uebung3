package runtime;

import view.CLI;
import component.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ComponentAssembler {

    private RuntimeEnvironment runtimeEnvironment;
    private CLI cli;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        ComponentAssembler componentAssembler = new ComponentAssembler();
        componentAssembler.init(componentAssembler);
    }

    public void init(ComponentAssembler ca) {
        runtimeEnvironment = new RuntimeEnvironment();
        cli = new CLI("CLI", ca);
    }

    public String startRE() {
        return runtimeEnvironment.start();

    }

    public void stopRE() throws InvocationTargetException, IllegalAccessException {
        cli.close(); // stops the gui
        runtimeEnvironment.stop();
    }

    public String addComponentRE(String fullComponentName) throws IOException, ClassNotFoundException {
        Component component = classLoader(fullComponentName);
        return runtimeEnvironment.addComponentRE(component);
    }

    public String removeComponentRE(String fullComponentName){
        return  runtimeEnvironment.removeComponentRE(fullComponentName);
    }

    public String startComponent(String fullComponentName){
        return runtimeEnvironment.startComponent(fullComponentName);
    }

    public String stopComponent(String fullThreadName) throws InvocationTargetException, IllegalAccessException {
        return runtimeEnvironment.stopComponent(fullThreadName);
    }
    public String getStates(){
        return runtimeEnvironment.getStates();
    }

    public Component classLoader(String pathToJar) throws IOException, ClassNotFoundException {
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration<JarEntry> e = jarFile.entries();

        HashMap<String, Class> classMap = new HashMap<>();

        URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);

            classMap.put(c.getName(), c);
        }
        Method startMethod = getAnnotatedMethod(classMap, "Start");
        Method stopMethod = getAnnotatedMethod(classMap, "Stop");
        Component component = new Component(cl, classMap, pathToJar, startMethod, stopMethod);
        return component;
    }

    public Method getAnnotatedMethod(Map<String, Class> classMap, String annotation){ // annotation is case sensitive!
        Class startAnnotation = null;

        // extracting annotation
        for(Class javaClass : classMap.values()){
            if(javaClass.getName() == annotation){
                startAnnotation = javaClass;
                break;
            }
        }

        // searching for that annotation
        for(Class javaClass : classMap.values()){
            for(Method method : javaClass.getMethods()){
                if(method.isAnnotationPresent(startAnnotation)){
                    return method;
                }
            }
        }
        return null;
    }

}
