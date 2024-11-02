package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.confirm.UploadConfirmViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UploadConfirmView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload confirm";

    private final UploadConfirmViewModel viewModel;
    private UploadController controller;

    public UploadConfirmView(UploadConfirmViewModel viewModel) {
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

    }
}
