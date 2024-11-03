package view.upload;

import interface_adapter.upload.UploadController;
import interface_adapter.upload.UploadSelectViewModel;
import org.jetbrains.annotations.NotNull;

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

    public UploadSelectView(UploadSelectViewModel uploadSelectViewModel) {
        this.viewModel = uploadSelectViewModel;
        uploadSelectViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(UploadSelectViewModel.TRANSPARENT, true));

        // position each component nicely within the view area using a GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 10, 0, 10);
        this.add(createTopPanel(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(createMainPanel(), constraints);
    }

    @NotNull
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(UploadSelectViewModel.MAIN_PANEL_COLOR, true));
        mainPanel.setPreferredSize(new Dimension(
                UploadSelectViewModel.PANEL_WIDTH,
                UploadSelectViewModel.MAIN_PANEL_HEIGHT
        ));

        JButton selectFileBtn = new JButton(UploadSelectViewModel.UPLOAD_BUTTON_LABEL);
        selectFileBtn.setBorderPainted(true);
        selectFileBtn.setContentAreaFilled(false);
        selectFileBtn.setFocusPainted(false);

        selectFileBtn.addActionListener((e) -> openFileDialog());
        mainPanel.add(selectFileBtn, new GridBagConstraints());
        return mainPanel;
    }

    @NotNull
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(UploadSelectViewModel.TOP_PANEL_COLOR, true));
        topPanel.setPreferredSize(new Dimension(
                UploadSelectViewModel.PANEL_WIDTH,
                UploadSelectViewModel.TOP_PANEL_HEIGHT
        ));

        JButton cancelBtn = new JButton(UploadSelectViewModel.CANCEL_BUTTON_LABEL);
        cancelBtn.setBorderPainted(true);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorderPainted(false);

        cancelBtn.addActionListener((e) -> controller.escape());
        topPanel.add(cancelBtn, BorderLayout.WEST);
        return topPanel;
    }

    public void openFileDialog() {
        // make the JFileChooser resemble the system file manager
        // first store the current Look and Feel, and swap to the new system-based Look and Feel
        LookAndFeel defaultLNF = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
               UnsupportedLookAndFeelException e) {
            // NOTE: it is not particularly consequential if we reach this branch -- we just use
            // the look and feel that was set by default
            System.out.println(e.getMessage());
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

        // now return to the Look and Feel that was used before making the switch
        // NOTE that if we do not do this, the button and panel styling will be changed throughout the whole app
        try {
            UIManager.setLookAndFeel(defaultLNF);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
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
