package interface_adapter.like_plant;

import interface_adapter.main.MainViewModel;
import use_case.like_plant.LikePlantOutputBoundary;

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
