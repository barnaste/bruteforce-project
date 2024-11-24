package interface_adapter.edit_plant;

import interface_adapter.main.MainViewModel;
import use_case.user_plant_view_edit.UserPlantViewEditOutputBoundary;

public class UserPlantViewEditPresenter implements UserPlantViewEditOutputBoundary {
    private MainViewModel mainViewModel;

    public UserPlantViewEditPresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView() {
        mainViewModel.firePropertyChanged("refresh");
    }
}
