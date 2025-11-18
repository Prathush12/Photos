package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.PhotoApp;
import photos.model.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the user albums screen.
 * Handles album management (create, delete, rename, open albums).
 * 
 * @author Photos Team
 */
public class UserAlbumsController {
    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<String> albumsListView;
    @FXML
    private TextField newAlbumNameField;
    
    private PhotoApp photoApp;
    private User user;
    private Stage primaryStage;
    private ObservableList<String> albumsList;
    
    /**
     * Sets the PhotoApp instance.
     * 
     * @param photoApp the PhotoApp instance
     */
    public void setPhotoApp(PhotoApp photoApp) {
        this.photoApp = photoApp;
    }
    
    /**
     * Sets the current user.
     * 
     * @param user the current user
     */
    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText("Albums - " + user.getUsername());
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
        albumsList = FXCollections.observableArrayList();
        albumsListView.setItems(albumsList);
    }
    
    /**
     * Refreshes the albums list display.
     */
    public void refreshAlbums() {
        albumsList.clear();
        for (Album album : user.getAlbums()) {
            String info = album.getName() + " (" + album.getPhotoCount() + " photos";
            if (album.getPhotoCount() > 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String earliest = album.getEarliestDate().format(formatter);
                String latest = album.getLatestDate().format(formatter);
                info += ", " + earliest + " - " + latest;
            }
            info += ")";
            albumsList.add(info);
        }
    }
    
    /**
     * Handles the create album button action.
     */
    @FXML
    private void handleCreateAlbum() {
        String albumName = newAlbumNameField.getText().trim();
        
        if (albumName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter an album name.");
            return;
        }
        
        if (user.hasAlbum(albumName)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Album already exists.");
            return;
        }
        
        Album newAlbum = new Album(albumName);
        if (user.addAlbum(newAlbum)) {
            refreshAlbums();
            newAlbumNameField.clear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Album created successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create album.");
        }
    }
    
    /**
     * Handles the delete album button action.
     */
    @FXML
    private void handleDeleteAlbum() {
        String selected = albumsListView.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an album to delete.");
            return;
        }
        
        String albumName = extractAlbumName(selected);
        Album album = user.getAlbumByName(albumName);
        
        if (album == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Album not found.");
            return;
        }
        
        if (user.removeAlbum(album)) {
            refreshAlbums();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Album deleted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete album.");
        }
    }
    
    /**
     * Handles the rename album button action.
     */
    @FXML
    private void handleRenameAlbum() {
        String selected = albumsListView.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an album to rename.");
            return;
        }
        
        String oldName = extractAlbumName(selected);
        Album album = user.getAlbumByName(oldName);
        
        if (album == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Album not found.");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog(oldName);
        dialog.setTitle("Rename Album");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new album name:");
        
        String newName = dialog.showAndWait().orElse(null);
        if (newName == null || newName.trim().isEmpty()) {
            return;
        }
        
        newName = newName.trim();
        if (user.hasAlbum(newName)) {
            showAlert(Alert.AlertType.ERROR, "Error", "An album with that name already exists.");
            return;
        }
        
        album.setName(newName);
        refreshAlbums();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Album renamed successfully.");
    }
    
    /**
     * Handles the open album button action.
     */
    @FXML
    private void handleOpenAlbum() {
        String selected = albumsListView.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an album to open.");
            return;
        }
        
        String albumName = extractAlbumName(selected);
        Album album = user.getAlbumByName(albumName);
        
        if (album == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Album not found.");
            return;
        }
        
        // Navigate to album view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/AlbumView.fxml"));
            Scene scene = new Scene(loader.load());
            AlbumViewController controller = loader.getController();
            controller.setPhotoApp(photoApp);
            controller.setUser(user);
            controller.setAlbum(album);
            controller.setPrimaryStage(primaryStage);
            controller.refreshPhotos();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Album: " + albumName);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load album view.");
        }
    }
    
    /**
     * Handles the search button action.
     */
    @FXML
    private void handleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/Search.fxml"));
            Scene scene = new Scene(loader.load());
            SearchController controller = loader.getController();
            controller.setPhotoApp(photoApp);
            controller.setUser(user);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Search Photos");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load search screen.");
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
     * Extracts the album name from the display string.
     * 
     * @param displayString the display string
     * @return the album name
     */
    private String extractAlbumName(String displayString) {
        int index = displayString.indexOf(" (");
        if (index > 0) {
            return displayString.substring(0, index);
        }
        return displayString;
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

