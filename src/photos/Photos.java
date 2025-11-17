package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import photos.controller.LoginController;
import photos.model.PhotoApp;

import java.io.IOException;

/**
 * Main application class for the Photo Application.
 * This is the entry point for the JavaFX application.
 * 
 * @author Photos Team
 */
public class Photos extends Application {
    
    private PhotoApp photoApp;
    
    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Initializes the application and loads data.
     */
    @Override
    public void init() {
        photoApp = PhotoApp.load();
    }
    
    /**
     * Starts the JavaFX application.
     * 
     * @param primaryStage the primary stage for this application
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
     * Called when the application is stopped.
     * Saves the application data.
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

