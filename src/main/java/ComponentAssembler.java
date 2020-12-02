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

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        ComponentAssembler componentAssembler = new ComponentAssembler();
        componentAssembler.init();

        // fake user inputs
        componentAssembler.startRE();
        componentAssembler.addComponentRE("Counter.jar");
        componentAssembler.startComponent("Counter.jar");
        componentAssembler.startComponent("Counter.jar");
        componentAssembler.stopComponent("Counter_1");
    }

    public void init() {
        runtimeEnvironment = new RuntimeEnvironment();
    }

    public void startRE() {
        runtimeEnvironment.start();
    }

    public void stopRE(){
        runtimeEnvironment.stop();
    }

    public void addComponentRE(String fullComponentName) throws IOException, ClassNotFoundException {
        Component component = classLoader(fullComponentName);
        runtimeEnvironment.addComponentRE(component);
    }

    public void removeComponentRE(String fullComponentName){
        runtimeEnvironment.removeComponentRE(fullComponentName);
    }

    public void startComponent(String fullComponentName){
        runtimeEnvironment.startComponent(fullComponentName);
    }

    public void stopComponent(String fullThreadName) throws InvocationTargetException, IllegalAccessException {
        runtimeEnvironment.stopComponent(fullThreadName);
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
