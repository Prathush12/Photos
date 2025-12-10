package photos.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.Tag;

import java.io.File;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the photo display screen.
 * Displays a photo with its caption, date, and tags.
 * 
 * @author Photos Team
 */
public class PhotoDisplayController {
    @FXML
    private ImageView photoImageView;
    @FXML
    private Label captionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label tagsLabel;
    
    private Photo photo;
    private Album album;
    private int photoIndex;
    private Stage primaryStage;
    private Scene previousScene;
    
    /**
     * Sets the photo to display.
     * 
     * @param photo the photo to display
     */
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
    
    /**
     * Sets the album for slideshow navigation.
     * 
     * @param album the album containing the photos
     */
    public void setAlbum(Album album) {
        this.album = album;
    }
    
    /**
     * Sets the current photo index in the album.
     * 
     * @param photoIndex the index of the current photo
     */
    public void setPhotoIndex(int photoIndex) {
        this.photoIndex = photoIndex;
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
     * Sets the previous scene to return to.
     * 
     * @param previousScene the previous scene
     */
    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }
    
    /**
     * Displays the photo and its information.
     */
    public void displayPhoto() {
        if (photo == null) {
            return;
        }
        
        // Load and display image
        File file = new File(photo.getFilePath());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            photoImageView.setImage(image);
        }
        
        // Display caption
        String caption = photo.getCaption();
        if (caption.isEmpty()) {
            caption = new File(photo.getFilePath()).getName();
        }
        captionLabel.setText("Caption: " + caption);
        
        // Display date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        dateLabel.setText("Date Taken: " + photo.getDateTaken().format(formatter));
        
        // Display tags
        if (photo.getTags().isEmpty()) {
            tagsLabel.setText("Tags: None");
        } else {
            StringBuilder tagsText = new StringBuilder("Tags: ");
            boolean first = true;
            for (Tag tag : photo.getTags()) {
                if (!first) {
                    tagsText.append(", ");
                }
                tagsText.append(tag.toString());
                first = false;
            }
            tagsLabel.setText(tagsText.toString());
        }
    }
    
    /**
     * Handles the previous photo button action for slideshow.
     */
    @FXML
    private void handlePrevious() {
        if (album == null || album.getPhotos().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No album available for slideshow.");
            return;
        }
        
        photoIndex = (photoIndex - 1 + album.getPhotos().size()) % album.getPhotos().size();
        photo = album.getPhotos().get(photoIndex);
        displayPhoto();
        primaryStage.setTitle("Photo: " + (photo.getCaption().isEmpty() ? 
            new File(photo.getFilePath()).getName() : photo.getCaption()));
    }
    
    /**
     * Handles the next photo button action for slideshow.
     */
    @FXML
    private void handleNext() {
        if (album == null || album.getPhotos().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No album available for slideshow.");
            return;
        }
        
        photoIndex = (photoIndex + 1) % album.getPhotos().size();
        photo = album.getPhotos().get(photoIndex);
        displayPhoto();
        primaryStage.setTitle("Photo: " + (photo.getCaption().isEmpty() ? 
            new File(photo.getFilePath()).getName() : photo.getCaption()));
    }
    
    /**
     * Handles the close button action.
     */
    @FXML
    private void handleClose() {
        if (previousScene != null) {
            primaryStage.setScene(previousScene);
        } else {
            primaryStage.close();
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

