package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadState;
import interface_adapter.upload.UploadSelectorViewModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * The View for the File Selection component of the Upload Use Case.
 */
public class UploadSelectorView extends JPanel implements PropertyChangeListener {
    private final String viewName = "upload selector";

    private final UploadSelectorViewModel uploadSelectorViewModel;
    private UploadController uploadController;

    private final JButton cancel;
    private final JButton selectFile;

    public UploadSelectorView(UploadSelectorViewModel uploadSelectorViewModel) {
        this.uploadSelectorViewModel = uploadSelectorViewModel;
        uploadSelectorViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());

        this.setBackground(new Color(UploadSelectorViewModel.BACKGROUND_COLOR, true));

        this.cancel = new JButton(UploadSelectorViewModel.CANCEL_BUTTON_LABEL);
        cancel.setBorderPainted(true);
        cancel.setContentAreaFilled(false);
        cancel.setFocusPainted(false);

        cancel.addActionListener((e) -> uploadController.escape());

        this.selectFile = new JButton(UploadSelectorViewModel.UPLOAD_BUTTON_LABEL);
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
        add(selectFile, constraints);
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
            uploadController.upload(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final UploadState state = (UploadState) evt.getNewValue();
        // TODO: fill this in
    }

    public void setUploadController(UploadController uploadController) {
        this.uploadController = uploadController;
    }

    public String getViewName() {
        return viewName;
    }
}
