package photos.model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

/**
 * Photo class. Stores info about a photo - where it's located on disk, 
 * its caption, when it was taken (from file modification date), and tags.
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
     * Creates a new photo from a file path. Gets the date from the file's 
     * last modified time since we can't read EXIF data.
     * 
     * @param filePath path to the image file
     */
    public Photo(String filePath) {
        this.filePath = filePath;
        this.caption = "";
        this.tags = new HashSet<>();
        updateDateFromFile();
    }
    
    /**
     * Updates the date by reading the file's last modified time.
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
     * Returns the file path of the photo.
     * 
     * @return the file path as a string
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Sets a new file path and updates the date from the file.
     * 
     * @param filePath the new file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        updateDateFromFile();
    }
    
    /**
     * Returns the caption of the photo.
     * 
     * @return the caption, or empty string if no caption is set
     */
    public String getCaption() {
        return caption;
    }
    
    /**
     * Sets the caption for the photo.
     * 
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    /**
     * Returns when the photo was taken (from file modification date).
     * 
     * @return the date and time the photo was taken
     */
    public LocalDateTime getDateTaken() {
        return dateTaken;
    }
    
    /**
     * Returns all tags for this photo.
     * 
     * @return a set of all tags associated with this photo
     */
    public Set<Tag> getTags() {
        return tags;
    }
    
    /**
     * Adds a tag to the photo. Returns false if the tag already exists.
     * 
     * @param tag the tag to add
     * @return true if the tag was added, false if it already exists
     */
    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }
    
    /**
     * Removes a tag from the photo. Returns false if the tag wasn't present.
     * 
     * @param tag the tag to remove
     * @return true if the tag was removed, false if it wasn't present
     */
    public boolean removeTag(Tag tag) {
        return tags.remove(tag);
    }
    
    /**
     * Checks if the photo has a specific tag.
     * 
     * @param tag the tag to check for
     * @return true if the photo has this tag, false otherwise
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

