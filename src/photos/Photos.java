package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import photos.controller.LoginController;
import photos.model.PhotoApp;

import java.io.IOException;

/**
 * Main class that starts the photo app. Sets up JavaFX and loads the login screen.
 * 
 * @author Photos Team
 */
public class Photos extends Application {
    
    private PhotoApp photoApp;
    
    /**
     * Main method - just launches the JavaFX app.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Loads all the user data when the app starts.
     */
    @Override
    public void init() {
        photoApp = PhotoApp.load();
    }
    
    /**
     * Sets up the window and shows the login screen.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/Login.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController controller = loader.getController();
            controller.setPhotoApp(photoApp);
            controller.setPrimaryStage(primaryStage);
            
            primaryStage.setTitle("Photo Application - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // Handle window close event to save data
            primaryStage.setOnCloseRequest(e -> {
                try {
                    photoApp.save();
                } catch (IOException ex) {
                    System.err.println("Failed to save data on exit: " + ex.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to load login screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Saves everything when the app closes.
     */
    @Override
    public void stop() {
        try {
            photoApp.save();
        } catch (IOException e) {
            System.err.println("Failed to save data on stop: " + e.getMessage());
        }
    }
}

