package interface_adapter.user_plant_info_edit;

import interface_adapter.main.MainViewModel;
import use_case.user_plant_info_edit.UserPlantInfoEditOutputBoundary;

public class UserPlantInfoEditPresenter implements UserPlantInfoEditOutputBoundary {
    private MainViewModel mainViewModel;

    public UserPlantInfoEditPresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView() {
        mainViewModel.firePropertyChanged("refresh");
    }
}
