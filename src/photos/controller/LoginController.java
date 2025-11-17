package photos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.model.PhotoApp;
import photos.model.User;

import java.io.IOException;

/**
 * Controller for the login screen.
 * Handles user authentication and navigation to admin or user screens.
 * 
 * @author Photos Team
 */
public class LoginController {
    @FXML
    private TextField usernameField;
    
    private PhotoApp photoApp;
    private Stage primaryStage;
    
    /**
     * Sets the PhotoApp instance.
     * 
     * @param photoApp the PhotoApp instance
     */
    public void setPhotoApp(PhotoApp photoApp) {
        this.photoApp = photoApp;
    }
    
    /**
     * Sets the primary stage.
     * 
     * @param primaryStage the primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    /**
     * Handles the login button action.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a username.");
            return;
        }
        
        if (username.equals("admin")) {
            // Navigate to admin screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/Admin.fxml"));
                Scene scene = new Scene(loader.load());
                AdminController controller = loader.getController();
                controller.setPhotoApp(photoApp);
                controller.setPrimaryStage(primaryStage);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Admin - Photo Application");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load admin screen.");
            }
        } else {
            // Check if user exists
            User user = photoApp.getUser(username);
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "User does not exist.");
                return;
            }
            
            // Navigate to user screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/UserAlbums.fxml"));
                Scene scene = new Scene(loader.load());
                UserAlbumsController controller = loader.getController();
                controller.setPhotoApp(photoApp);
                controller.setUser(user);
                controller.setPrimaryStage(primaryStage);
                controller.refreshAlbums();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Albums - " + username);
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load user screen.");
            }
        }
    }
    
    /**
     * Handles the quit button action.
     */
    @FXML
    private void handleQuit() {
        try {
            photoApp.save();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data.");
        }
        primaryStage.close();
    }
    
    /**
     * Shows an alert dialog.
     * 
     * @param type the alert type
     * @param title the alert title
     * @param message the alert message
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

