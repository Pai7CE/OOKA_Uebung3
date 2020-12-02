import component.Component;
import util.ComponentContainer;

import java.io.IOException;
import java.lang.reflect.*;

public class RuntimeEnvironment {

    private ComponentContainer componentContainer;
    private ComponentAssembler componentAssembler;

    // initializes needed
    public void init() throws IOException, ClassNotFoundException {
        componentAssembler = new ComponentAssembler();
        componentContainer = new ComponentContainer();
        Component counterComponent;
        counterComponent = componentAssembler.loadJAR("Counter.jar");

        componentContainer.add(counterComponent);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RuntimeEnvironment re = new RuntimeEnvironment();

        re.init();

    }



}
