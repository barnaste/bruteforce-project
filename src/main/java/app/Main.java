package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * The entry point of the application. Initializes and displays the main application window
     * with the necessary views and use cases.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create and configure the AppBuilder to set up the application's views and use cases
        final AppBuilder appBuilder = new AppBuilder();

        // Build the main application window with all required views and use cases
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addLoggedInView()
                .addStartView()
                .addSignupUseCase()
                .addLoginUseCase()
                .build();

        // Pack the window to fit its components and make it visible
        application.pack();
        application.setVisible(true);
    }
}
