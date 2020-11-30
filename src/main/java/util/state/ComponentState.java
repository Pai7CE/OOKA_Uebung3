package util.state;

import component.Component;

public interface ComponentState {
    void nextState(Component component);
    void prevState(Component component);
    void showState(Component component);
}
