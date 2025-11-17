package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.model.PhotoApp;
import photos.model.User;

import java.io.IOException;

/**
 * Controller for the admin screen.
 * Handles user management (list, create, delete users).
 * 
 * @author Photos Team
 */
public class AdminController {
    @FXML
    private ListView<String> usersListView;
    @FXML
    private TextField newUsernameField;
    
    private PhotoApp photoApp;
    private Stage primaryStage;
    private ObservableList<String> usersList;
    
    /**
     * Sets the PhotoApp instance.
     * 
     * @param photoApp the PhotoApp instance
     */
    public void setPhotoApp(PhotoApp photoApp) {
        this.photoApp = photoApp;
        refreshUsers();
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
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        usersList = FXCollections.observableArrayList();
        usersListView.setItems(usersList);
    }
    
    /**
     * Refreshes the users list.
     */
    private void refreshUsers() {
        usersList.clear();
        for (User user : photoApp.getUsers()) {
            usersList.add(user.getUsername());
        }
    }
    
    /**
     * Handles the create user button action.
     */
    @FXML
    private void handleCreateUser() {
        String username = newUsernameField.getText().trim();
        
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a username.");
            return;
        }
        
        if (photoApp.userExists(username)) {
            showAlert(Alert.AlertType.ERROR, "Error", "User already exists.");
            return;
        }
        
        User newUser = new User(username);
        if (photoApp.addUser(newUser)) {
            refreshUsers();
            newUsernameField.clear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create user.");
        }
    }
    
    /**
     * Handles the delete user button action.
     */
    @FXML
    private void handleDeleteUser() {
        String selectedUser = usersListView.getSelectionModel().getSelectedItem();
        
        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to delete.");
            return;
        }
        
        if (selectedUser.equals("stock") || selectedUser.equals("admin")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot delete stock or admin user.");
            return;
        }
        
        if (photoApp.removeUser(selectedUser)) {
            refreshUsers();
            showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user.");
        }
    }
    
    /**
     * Handles the logout button action.
     */
    @FXML
    private void handleLogout() {
        try {
            photoApp.save();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data.");
        }
        
        // Navigate back to login screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/Login.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController controller = loader.getController();
            controller.setPhotoApp(photoApp);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Photo Application - Login");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login screen.");
        }
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

