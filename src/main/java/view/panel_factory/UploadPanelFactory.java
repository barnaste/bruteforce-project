package view.panel_factory;

import data_access.MongoImageDataAccessObject;
import data_access.MongoPlantDataAccessObject;
import data_access.MongoUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadPresenter;
import interface_adapter.upload.confirm.UploadConfirmViewModel;
import interface_adapter.upload.result.UploadResultViewModel;
import interface_adapter.upload.select.UploadSelectViewModel;
import use_case.upload.UploadInputBoundary;
import use_case.upload.UploadInteractor;
import use_case.upload.UploadOutputBoundary;
import view.ViewManager;
import view.upload.UploadConfirmView;
import view.upload.UploadResultView;
import view.upload.UploadSelectView;

import javax.swing.*;
import java.awt.*;

public class UploadPanelFactory {
    public static void createUploadPanel(Container parentPanel, JPanel cardPanel, Runnable escapeMap, MainViewModel mainViewModel) {
        // NOTE: we extend CardLayout so that whenever the top card is swapped, the
        // ENTIRE view is redrawn, and not just the region the card occupied.
        // This is because cards may be of variant dimensions. We would otherwise
        // have artefacts from previous cards if they were of larger dimensions.
        CardLayout cardLayout = new CardLayout() {
            @Override
            public void show(Container parent, String name) {
                super.show(parent, name);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        };
        cardPanel.setLayout(cardLayout);

        UploadSelectViewModel selectorViewModel = new UploadSelectViewModel();
        UploadSelectView selectorView = new UploadSelectView(selectorViewModel);
        cardPanel.add(selectorView, selectorView.getViewName());

        UploadConfirmViewModel confirmViewModel = new UploadConfirmViewModel();
        UploadConfirmView confirmView = new UploadConfirmView(confirmViewModel);
        cardPanel.add(confirmView, confirmView.getViewName());

        UploadResultViewModel resultViewModel = new UploadResultViewModel();
        UploadResultView resultView = new UploadResultView(resultViewModel);
        cardPanel.add(resultView, resultView.getViewName());

        ViewManagerModel uploadManagerModel = new ViewManagerModel();
        new ViewManager(cardPanel, cardLayout, uploadManagerModel);

        UploadOutputBoundary uploadOutputBoundary = new UploadPresenter(
                uploadManagerModel,
                selectorViewModel,
                confirmViewModel,
                resultViewModel,
                mainViewModel
        );
        UploadInputBoundary uploadInteractor = new UploadInteractor(
                uploadOutputBoundary,
                MongoImageDataAccessObject.getInstance(),
                MongoPlantDataAccessObject.getInstance(),
                MongoUserDataAccessObject.getInstance()
        );
        uploadInteractor.setEscapeMap(escapeMap);
        UploadController controller = new UploadController(uploadInteractor);

        selectorView.setController(controller);
        confirmView.setController(controller);
        resultView.setController(controller);

        uploadManagerModel.setState(selectorView.getViewName());
        uploadManagerModel.firePropertyChanged();
    }
}
