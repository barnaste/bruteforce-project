package interface_adapter.main;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class MainViewModel extends ViewModel<MainState> {

    public MainViewModel() {
        super("main view");
        setState(new MainState());
    }

    // Accessor for current mode
    public MainState.Mode getCurrentMode() {
        return getState().getCurrentMode();
    }

    // Mutator for current mode
    public void setCurrentMode(MainState.Mode mode) {
        MainState state = getState();
        state.setCurrentMode(mode);
        firePropertyChanged("state");  // Notify listeners of state change
    }

}