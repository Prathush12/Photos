package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.PhotoApp;
import photos.model.Tag;
import photos.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the album view screen.
 * Handles photo management within an album (add, remove, view, tag, etc.).
 * 
 * @author Photos Team
 */
public class AlbumViewController {
    @FXML
    private Label albumNameLabel;
    @FXML
    private ListView<String> photosListView;
    
    private PhotoApp photoApp;
    private User user;
    private Album album;
    private Stage primaryStage;
    private ObservableList<String> photosList;
    private int currentPhotoIndex = -1;
    
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
    }
    
    /**
     * Sets the current album.
     * 
     * @param album the current album
     */
    public void setAlbum(Album album) {
        this.album = album;
        albumNameLabel.setText("Album: " + album.getName());
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
        photosList = FXCollections.observableArrayList();
        photosListView.setItems(photosList);
        photosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentPhotoIndex = photosList.indexOf(newVal);
            }
        });
    }
    
    /**
     * Refreshes the photos list display.
     */
    public void refreshPhotos() {
        photosList.clear();
        for (Photo photo : album.getPhotos()) {
            String display = photo.getCaption().isEmpty() ? 
                new File(photo.getFilePath()).getName() : 
                photo.getCaption();
            photosList.add(display);
        }
    }
    
    /**
     * Gets the currently selected photo.
     * 
     * @return the selected photo, or null if none selected
     */
    private Photo getSelectedPhoto() {
        int index = photosListView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && index < album.getPhotos().size()) {
            return album.getPhotos().get(index);
        }
        return null;
    }
    
    /**
     * Handles the add photo button action.
     */
    @FXML
    private void handleAddPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp")
        );
        
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            Photo photo = new Photo(selectedFile.getAbsolutePath());
            
            // Check if photo already exists in album
            if (album.getPhotos().contains(photo)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Photo already exists in this album.");
                return;
            }
            
            album.addPhoto(photo);
            refreshPhotos();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Photo added successfully.");
        }
    }
    
    /**
     * Handles the remove photo button action.
     */
    @FXML
    private void handleRemovePhoto() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo to remove.");
            return;
        }
        
        album.removePhoto(photo);
        refreshPhotos();
        currentPhotoIndex = -1;
        showAlert(Alert.AlertType.INFORMATION, "Success", "Photo removed successfully.");
    }
    
    /**
     * Handles the view photo button action.
     */
    @FXML
    private void handleViewPhoto() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo to view.");
            return;
        }
        
        try {
            Scene currentScene = primaryStage.getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/PhotoDisplay.fxml"));
            Scene scene = new Scene(loader.load());
            PhotoDisplayController controller = loader.getController();
            controller.setPhoto(photo);
            controller.setPrimaryStage(primaryStage);
            controller.setPreviousScene(currentScene);
            controller.displayPhoto();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Photo: " + (photo.getCaption().isEmpty() ? 
                new File(photo.getFilePath()).getName() : photo.getCaption()));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load photo display.");
        }
    }
    
    /**
     * Handles the caption photo button action.
     */
    @FXML
    private void handleCaptionPhoto() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo to caption.");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog(photo.getCaption());
        dialog.setTitle("Caption Photo");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter caption:");
        
        String caption = dialog.showAndWait().orElse(null);
        if (caption != null) {
            photo.setCaption(caption);
            refreshPhotos();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Caption updated successfully.");
        }
    }
    
    /**
     * Handles the add tag button action.
     */
    @FXML
    private void handleAddTag() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo to tag.");
            return;
        }
        
        // Choose tag type or create new one
        List<String> tagTypes = photoApp.getTagTypes();
        List<String> options = new ArrayList<>(tagTypes);
        options.add("Create New Tag Type...");
        
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>(tagTypes.get(0), options);
        typeDialog.setTitle("Add Tag");
        typeDialog.setHeaderText(null);
        typeDialog.setContentText("Select tag type:");
        
        Optional<String> tagTypeOpt = typeDialog.showAndWait();
        if (!tagTypeOpt.isPresent()) {
            return;
        }
        
        String tagType = tagTypeOpt.get();
        
        // If user wants to create new tag type
        if (tagType.equals("Create New Tag Type...")) {
            TextInputDialog newTypeDialog = new TextInputDialog();
            newTypeDialog.setTitle("Create Tag Type");
            newTypeDialog.setHeaderText(null);
            newTypeDialog.setContentText("Enter new tag type name:");
            
            Optional<String> newType = newTypeDialog.showAndWait();
            if (!newType.isPresent() || newType.get().trim().isEmpty()) {
                return;
            }
            
            tagType = newType.get().trim();
            if (photoApp.addTagType(tagType)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "New tag type created.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Tag type already exists.");
                return;
            }
        }
        
        // Enter tag value
        TextInputDialog valueDialog = new TextInputDialog();
        valueDialog.setTitle("Add Tag");
        valueDialog.setHeaderText(null);
        valueDialog.setContentText("Enter tag value:");
        
        Optional<String> tagValue = valueDialog.showAndWait();
        if (!tagValue.isPresent() || tagValue.get().trim().isEmpty()) {
            return;
        }
        
        Tag tag = new Tag(tagType, tagValue.get().trim());
        if (photo.addTag(tag)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tag added successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Tag already exists on this photo.");
        }
    }
    
    /**
     * Handles the delete tag button action.
     */
    @FXML
    private void handleDeleteTag() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo.");
            return;
        }
        
        if (photo.getTags().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Photo has no tags.");
            return;
        }
        
        List<String> tagStrings = new ArrayList<>();
        for (Tag tag : photo.getTags()) {
            tagStrings.add(tag.toString());
        }
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(tagStrings.get(0), tagStrings);
        dialog.setTitle("Delete Tag");
        dialog.setHeaderText(null);
        dialog.setContentText("Select tag to delete:");
        
        Optional<String> selected = dialog.showAndWait();
        if (selected.isPresent()) {
            String[] parts = selected.get().split("=", 2);
            if (parts.length == 2) {
                Tag tag = new Tag(parts[0], parts[1]);
                if (photo.removeTag(tag)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Tag deleted successfully.");
                }
            }
        }
    }
    
    /**
     * Handles the copy photo button action.
     */
    @FXML
    private void handleCopyPhoto() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo to copy.");
            return;
        }
        
        List<String> albumNames = new ArrayList<>();
        for (Album a : user.getAlbums()) {
            if (!a.equals(album)) {
                albumNames.add(a.getName());
            }
        }
        
        if (albumNames.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No other albums available.");
            return;
        }
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(albumNames.get(0), albumNames);
        dialog.setTitle("Copy Photo");
        dialog.setHeaderText(null);
        dialog.setContentText("Select destination album:");
        
        Optional<String> selected = dialog.showAndWait();
        if (selected.isPresent()) {
            Album destAlbum = user.getAlbumByName(selected.get());
            if (destAlbum != null) {
                if (destAlbum.addPhoto(photo)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Photo copied successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Photo already exists in that album.");
                }
            }
        }
    }
    
    /**
     * Handles the move photo button action.
     */
    @FXML
    private void handleMovePhoto() {
        Photo photo = getSelectedPhoto();
        if (photo == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a photo to move.");
            return;
        }
        
        List<String> albumNames = new ArrayList<>();
        for (Album a : user.getAlbums()) {
            if (!a.equals(album)) {
                albumNames.add(a.getName());
            }
        }
        
        if (albumNames.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No other albums available.");
            return;
        }
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(albumNames.get(0), albumNames);
        dialog.setTitle("Move Photo");
        dialog.setHeaderText(null);
        dialog.setContentText("Select destination album:");
        
        Optional<String> selected = dialog.showAndWait();
        if (selected.isPresent()) {
            Album destAlbum = user.getAlbumByName(selected.get());
            if (destAlbum != null) {
                if (destAlbum.addPhoto(photo)) {
                    album.removePhoto(photo);
                    refreshPhotos();
                    currentPhotoIndex = -1;
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Photo moved successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Photo already exists in that album.");
                }
            }
        }
    }
    
    /**
     * Handles the previous photo button action.
     */
    @FXML
    private void handlePrevious() {
        if (album.getPhotos().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Album is empty.");
            return;
        }
        
        if (currentPhotoIndex < 0) {
            currentPhotoIndex = album.getPhotos().size() - 1;
        } else {
            currentPhotoIndex = (currentPhotoIndex - 1 + album.getPhotos().size()) % album.getPhotos().size();
        }
        
        photosListView.getSelectionModel().select(currentPhotoIndex);
        handleViewPhoto();
    }
    
    /**
     * Handles the next photo button action.
     */
    @FXML
    private void handleNext() {
        if (album.getPhotos().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Album is empty.");
            return;
        }
        
        if (currentPhotoIndex < 0) {
            currentPhotoIndex = 0;
        } else {
            currentPhotoIndex = (currentPhotoIndex + 1) % album.getPhotos().size();
        }
        
        photosListView.getSelectionModel().select(currentPhotoIndex);
        handleViewPhoto();
    }
    
    /**
     * Handles the back button action.
     */
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/resources/UserAlbums.fxml"));
            Scene scene = new Scene(loader.load());
            UserAlbumsController controller = loader.getController();
            controller.setPhotoApp(photoApp);
            controller.setUser(user);
            controller.setPrimaryStage(primaryStage);
            controller.refreshAlbums();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Albums - " + user.getUsername());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load albums screen.");
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

