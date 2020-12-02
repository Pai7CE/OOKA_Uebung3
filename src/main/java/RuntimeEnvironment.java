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

        // Testing
        Component counterComponent;
        counterComponent = componentAssembler.loadJAR("Counter.jar");

        componentContainer.add(counterComponent);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RuntimeEnvironment re = new RuntimeEnvironment();

        re.init();
        re.componentContainer.getComponentContainer().get(0);

        re.startMethod(re.componentContainer.getComponentContainer().get(0));

    }

    public void startMethod(Component component){
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
                    System.out.println("Annotation present");
                }
            }
        }
    }

}
