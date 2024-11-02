package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadResultViewModel;
import interface_adapter.upload.UploadState;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UploadResultView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload result";

    private final UploadResultViewModel uploadResultViewModel;
    private UploadController uploadController;

    public UploadResultView(UploadResultViewModel uploadResultViewModel) {
        this.uploadResultViewModel = uploadResultViewModel;
        uploadResultViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final UploadState state = (UploadState) evt.getSource();
        // TODO: fill this in
    }

    public void setUploadController(UploadController controller) {
        this.uploadController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}