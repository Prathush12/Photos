package photos.model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a photo in the application.
 * A photo has a file path, caption, date taken, and a set of tags.
 * 
 * @author Photos Team
 */
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String filePath;
    private String caption;
    private LocalDateTime dateTaken;
    private Set<Tag> tags;
    
    /**
     * Constructs a new Photo with the specified file path.
     * The date taken is set to the file's last modification time.
     * 
     * @param filePath the path to the photo file
     */
    public Photo(String filePath) {
        this.filePath = filePath;
        this.caption = "";
        this.tags = new HashSet<>();
        updateDateFromFile();
    }
    
    /**
     * Updates the date taken from the file's last modification time.
     */
    public void updateDateFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            long lastModified = file.lastModified();
            this.dateTaken = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(lastModified),
                ZoneId.systemDefault()
            );
        } else {
            this.dateTaken = LocalDateTime.now();
        }
    }
    
    /**
     * Gets the file path of the photo.
     * 
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Sets the file path of the photo.
     * 
     * @param filePath the file path to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        updateDateFromFile();
    }
    
    /**
     * Gets the caption of the photo.
     * 
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }
    
    /**
     * Sets the caption of the photo.
     * 
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    /**
     * Gets the date the photo was taken.
     * 
     * @return the date taken
     */
    public LocalDateTime getDateTaken() {
        return dateTaken;
    }
    
    /**
     * Gets all tags associated with this photo.
     * 
     * @return a set of tags
     */
    public Set<Tag> getTags() {
        return tags;
    }
    
    /**
     * Adds a tag to this photo.
     * 
     * @param tag the tag to add
     * @return true if the tag was added, false if it already exists
     */
    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }
    
    /**
     * Removes a tag from this photo.
     * 
     * @param tag the tag to remove
     * @return true if the tag was removed, false if it didn't exist
     */
    public boolean removeTag(Tag tag) {
        return tags.remove(tag);
    }
    
    /**
     * Checks if this photo has a specific tag.
     * 
     * @param tag the tag to check for
     * @return true if the photo has this tag
     */
    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Photo photo = (Photo) obj;
        return filePath.equals(photo.filePath);
    }
    
    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}

