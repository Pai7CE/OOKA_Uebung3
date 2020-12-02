import cli.GUI;
import component.Component;
import util.ComponentContainer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RuntimeEnvironment {

    private ComponentContainer componentContainer;
    private ComponentAssembler componentAssembler;
    private GUI gui;

    // initializes needed
    public void init() throws IOException, ClassNotFoundException {
        componentAssembler = new ComponentAssembler();
        componentContainer = new ComponentContainer();
        gui = new GUI("test");
        gui.print("test2");

        // Testing
        Component counterComponent;
        counterComponent = componentAssembler.loadJAR("Counter.jar");

        componentContainer.add(counterComponent);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RuntimeEnvironment re = new RuntimeEnvironment();

        re.init();
        Component component = re.componentContainer.getComponentContainer().get(0);

        Method method = re.getAnnotatedMethod(component, "Start");
        component.print(method.getName());

    }

    public Method getAnnotatedMethod(Component component, String annotation){ // annotation is case sensitive!
        Class startAnnotation = null;

        // extracting annotation
        for(Class javaClass : component.getClassMap().values()){
            if(javaClass.getName() == "Start"){
                startAnnotation = javaClass;
                break;
            }
        }

        for(Class javaClass : component.getClassMap().values()){
            for(Method method : javaClass.getMethods()){
                if(method.isAnnotationPresent(startAnnotation)){
                    return method;
                }
            }
        }
        return null;
    }



}
