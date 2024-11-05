package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

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
        username = new JLabel();
        final JPanel header = ViewComponentFactory.buildHorizontalPanel(List.of(title, username));

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

        logOut = ViewComponentFactory.buildButton("Log Out");
        sort = ViewComponentFactory.buildButton("Sort");
        final JPanel buttons = ViewComponentFactory.buildVerticalPanel(List.of(logOut, sort));

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

        final JPanel gallery = new JPanel();
        // Temporarily give the gallery panel a border so it's visible
        gallery.setPreferredSize(new Dimension(800, 500));
        gallery.setBorder(BorderFactory.createLineBorder(Color.black));

        final JPanel body = ViewComponentFactory.buildHorizontalPanel(List.of(buttons, gallery));

        this.add(ViewComponentFactory.buildVerticalPanel(List.of(header, body)));

        this.setLayout(new GridBagLayout());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final MainState state = (MainState) evt.getNewValue();
            username.setText("Currently logged in: " + state.getUsername());
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