package photos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.PhotoApp;
import photos.model.Tag;
import photos.model.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the search screen.
 * Handles searching for photos by date range or tags.
 * 
 * @author Photos Team
 */
public class SearchController {
    @FXML
    private ToggleGroup searchTypeGroup;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField tagType1Field;
    @FXML
    private TextField tagValue1Field;
    @FXML
    private TextField tagType2Field;
    @FXML
    private TextField tagValue2Field;
    @FXML
    private RadioButton andRadio;
    @FXML
    private RadioButton orRadio;
    @FXML
    private ListView<String> resultsListView;
    
    private PhotoApp photoApp;
    private User user;
    private Stage primaryStage;
    private ObservableList<String> resultsList;
    private List<Photo> searchResults;
    
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
     * Sets the primary stage.
     * 
     * @param primaryStage the primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    private GridPane dateRangePane;
    @FXML
    private VBox tagSearchPane;
    
    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        resultsList = FXCollections.observableArrayList();
        resultsListView.setItems(resultsList);
        searchResults = new ArrayList<>();
        
        // Toggle visibility based on search type
        searchTypeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                RadioButton selected = (RadioButton) newVal;
                boolean isDateSearch = selected.getText().contains("Date");
                dateRangePane.setVisible(isDateSearch);
                dateRangePane.setManaged(isDateSearch);
                tagSearchPane.setVisible(!isDateSearch);
                tagSearchPane.setManaged(!isDateSearch);
            }
        });
    }
    
    /**
     * Handles the search button action.
     */
    @FXML
    private void handleSearch() {
        RadioButton selected = (RadioButton) searchTypeGroup.getSelectedToggle();
        if (selected == null) {
            return;
        }
        
        searchResults.clear();
        resultsList.clear();
        
        if (selected.getText().contains("Date")) {
            searchByDateRange();
        } else {
            searchByTags();
        }
        
        // Display results
        for (Photo photo : searchResults) {
            String display = photo.getCaption().isEmpty() ? 
                new File(photo.getFilePath()).getName() : 
                photo.getCaption();
            resultsList.add(display);
        }
    }
    
    /**
     * Searches for photos by date range.
     */
    private void searchByDateRange() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        
        if (startDate == null || endDate == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select both start and end dates.");
            return;
        }
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        for (Album album : user.getAlbums()) {
            for (Photo photo : album.getPhotos()) {
                LocalDateTime photoDate = photo.getDateTaken();
                if (!photoDate.isBefore(startDateTime) && !photoDate.isAfter(endDateTime)) {
                    if (!searchResults.contains(photo)) {
                        searchResults.add(photo);
                    }
                }
            }
        }
    }
    
    /**
     * Searches for photos by tags.
     */
    private void searchByTags() {
        String type1 = tagType1Field.getText().trim();
        String value1 = tagValue1Field.getText().trim();
        String type2 = tagType2Field.getText().trim();
        String value2 = tagValue2Field.getText().trim();
        
        if (type1.isEmpty() || value1.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter at least one tag type and value.");
            return;
        }
        
        Tag tag1 = new Tag(type1, value1);
        boolean useTwoTags = !type2.isEmpty() && !value2.isEmpty();
        Tag tag2 = useTwoTags ? new Tag(type2, value2) : null;
        boolean useAnd = andRadio.isSelected();
        
        for (Album album : user.getAlbums()) {
            for (Photo photo : album.getPhotos()) {
                boolean matches = false;
                
                if (!useTwoTags) {
                    // Single tag search
                    matches = photo.hasTag(tag1);
                } else {
                    // Two tag search
                    boolean hasTag1 = photo.hasTag(tag1);
                    boolean hasTag2 = photo.hasTag(tag2);
                    
                    if (useAnd) {
                        matches = hasTag1 && hasTag2;
                    } else {
                        matches = hasTag1 || hasTag2;
                    }
                }
                
                if (matches && !searchResults.contains(photo)) {
                    searchResults.add(photo);
                }
            }
        }
    }
    
    /**
     * Handles the create album from results button action.
     */
    @FXML
    private void handleCreateAlbum() {
        if (searchResults.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No search results to create album from.");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter album name:");
        
        String albumName = dialog.showAndWait().orElse(null);
        if (albumName == null || albumName.trim().isEmpty()) {
            return;
        }
        
        albumName = albumName.trim();
        if (user.hasAlbum(albumName)) {
            showAlert(Alert.AlertType.ERROR, "Error", "An album with that name already exists.");
            return;
        }
        
        Album newAlbum = new Album(albumName);
        for (Photo photo : searchResults) {
            newAlbum.addPhoto(photo);
        }
        
        user.addAlbum(newAlbum);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Album created successfully with " + searchResults.size() + " photos.");
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

