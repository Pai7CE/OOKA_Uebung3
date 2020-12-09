package runtime;

import view.CLI;
import component.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ComponentAssembler {

    private RuntimeEnvironment runtimeEnvironment;
    private CLI cli;
    private File recoveryFile; // created when JRE is initialized and deleted when it is successfully stopped
    private String fileName = "recoveryFile.txt";



    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ComponentAssembler componentAssembler = new ComponentAssembler();
        componentAssembler.init(componentAssembler);
    }

    public void init(ComponentAssembler ca) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        runtimeEnvironment = new RuntimeEnvironment();
        cli = new CLI("CLI", ca);

        recoveryFile = new File(fileName);
        runtimeEnvironment.setRecoveryFile(recoveryFile);
        if(recoveryFile.createNewFile()){
            cli.print("Recovery file created. \n");
        }
        else {
            cli.print("Recovery file detected...");
            cli.print("Starting recovery attempt");
            recovery();
        }
    }

    private void recovery() throws FileNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        runtimeEnvironment.setRecovery(true);
        Scanner scanner = new Scanner(recoveryFile);

        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            String[] args = data.split(";");
            String recoveryMsg;
            if(args.length < 2){ //if it's a command without value
                Method method = this.getClass().getMethod(args[0]);
                method.invoke(this);
                recoveryMsg = "Recovered: " + method.getName();
            }
            else{ // command with passing value
                Method method = this.getClass().getMethod(args[0],String.class);
                method.invoke(this, args[1]);
                recoveryMsg = "Recovered: " + method.getName() + " " + args[1];
            }
            cli.print(recoveryMsg);
        }
        scanner.close();
        runtimeEnvironment.setRecovery(false);
    }

    public String startRE() throws IOException {
        return runtimeEnvironment.startRE();
    }

    public void stopRE() throws InvocationTargetException, IllegalAccessException, IOException {
        cli.close(); // stops the gui
        runtimeEnvironment.stopRE();
        System.exit(0);
    }

    public String addComponentRE(String fullComponentName) throws IOException, ClassNotFoundException {

        Component component = classLoader(fullComponentName);
        return runtimeEnvironment.addComponentRE(component);
    }

    public String removeComponentRE(String fullComponentName) throws IOException {
        return  runtimeEnvironment.removeComponentRE(fullComponentName);
    }

    public String startComponent(String fullComponentName) throws IOException {
        return runtimeEnvironment.startComponent(fullComponentName);
    }

    public String stopComponent(String fullThreadName) throws InvocationTargetException, IllegalAccessException, IOException {
        return runtimeEnvironment.stopComponent(fullThreadName);
    }
    public String getStates(){
        return runtimeEnvironment.getStates();
    }


    // simulated crash which won't delete the backup file
    public void crashRE(){
        runtimeEnvironment.crashRE();
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
