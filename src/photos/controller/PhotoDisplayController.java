package photos.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
}

