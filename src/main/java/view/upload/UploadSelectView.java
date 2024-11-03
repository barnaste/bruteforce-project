package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadSelectViewModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the File Selection component of the Upload Use Case.
 */
public class UploadSelectView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload select";

    private final UploadSelectViewModel viewModel;
    private UploadController controller;

    private final JButton cancel;
    private final JButton selectFile;

    public UploadSelectView(UploadSelectViewModel uploadSelectViewModel) {
        this.viewModel = uploadSelectViewModel;
        uploadSelectViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(UploadSelectViewModel.BACKGROUND_COLOR, true));

        this.cancel = new JButton(UploadSelectViewModel.CANCEL_BUTTON_LABEL);
        cancel.setBorderPainted(true);
        cancel.setContentAreaFilled(false);
        cancel.setFocusPainted(false);

        cancel.addActionListener((e) -> controller.escape());

        this.selectFile = new JButton(UploadSelectViewModel.UPLOAD_BUTTON_LABEL);
        selectFile.setBorderPainted(true);
        selectFile.setContentAreaFilled(false);
        selectFile.setFocusPainted(false);

        selectFile.addActionListener((e) -> openFileDialog());

        // position each component nicely within the view area using a GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 20, 10);
        this.add(cancel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(20, 10, 10, 10);
        this.add(selectFile, constraints);
    }

    public void openFileDialog() {
        // make the JFileChooser resemble the system file manager
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
               UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        // limit file choice to image files
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png"
        );
        fileChooser.setFileFilter(filter);

        // prompt user to select an image
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            controller.switchToConfirmView(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void setController(UploadController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    public void propertyChange(PropertyChangeEvent evt) {}
}
