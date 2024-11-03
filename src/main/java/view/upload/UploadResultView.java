package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadResultViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UploadResultView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload result";

    private final UploadResultViewModel viewModel;
    private UploadController controller;

    public UploadResultView(UploadResultViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);
    }

    public void setUploadController(UploadController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // final UploadState state = (UploadState) evt.getSource();
        // TODO: fill this in
    }
}