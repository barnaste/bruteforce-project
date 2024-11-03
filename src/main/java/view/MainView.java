package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.logout.LogoutController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;

import interface_adapter.sort.SortController;

/**
 * The Main View, for when the user is logged into the program.
 */
public class MainView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final MainViewModel mainViewModel;

    private LogoutController logoutController;
    private SortController sortController;

    private final JLabel username;

    private final JButton logOut;
    private final JButton sort;

    private final JTextField passwordInputField = new JTextField(15);

    public MainView(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.mainViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        logOut = new JButton("Log Out");
        sort = new JButton("Sort");
        buttons.add(logOut);
        buttons.add(sort);

        final JPanel gallery = new JPanel();
        // Temporarily give the gallery panel a border so it's visible
        gallery.setPreferredSize(new Dimension(500, 300));
        gallery.setBorder(BorderFactory.createLineBorder(Color.black));

        final JPanel mainPanel = new JPanel();
        mainPanel.add(buttons);
        mainPanel.add(gallery);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MainState currentState = mainViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                mainViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        logOut.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOut)) {
                        final MainState mainState = mainViewModel.getState();
                        logoutController.execute(mainState.getUsername());
                    }
                }
        );

        sort.addActionListener(
                evt -> {
                    if (evt.getSource().equals(sort)) {
                        // TODO: Implement
                        sortController.execute();
                    }
                }
        );

        this.add(title);
        this.add(usernameInfo);
        this.add(username);

        this.add(mainPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final MainState state = (MainState) evt.getNewValue();
            username.setText(state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setSortController(SortController sortController) {
        this.sortController = sortController;
    }
}