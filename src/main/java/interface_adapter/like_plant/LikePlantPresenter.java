package interface_adapter.like_plant;

import interface_adapter.main.MainViewModel;
import use_case.like_plant.LikePlantOutputBoundary;

/**
 * Presenter for the "like plant" use case.
 * Handles the presentation logic by updating the view through the MainViewModel.
 */
public class LikePlantPresenter implements LikePlantOutputBoundary {
    private final MainViewModel mainViewModel;

    public LikePlantPresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView() {
        mainViewModel.firePropertyChanged("refresh");
    }
}
