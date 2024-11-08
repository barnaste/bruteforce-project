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

}