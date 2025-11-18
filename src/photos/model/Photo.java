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
     * Returns the file path.
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Sets a new file path and updates the date.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        updateDateFromFile();
    }
    
    /**
     * Returns the caption.
     */
    public String getCaption() {
        return caption;
    }
    
    /**
     * Sets the caption.
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    /**
     * Returns when the photo was taken (from file mod date).
     */
    public LocalDateTime getDateTaken() {
        return dateTaken;
    }
    
    /**
     * Returns all tags for this photo.
     */
    public Set<Tag> getTags() {
        return tags;
    }
    
    /**
     * Adds a tag. Returns false if the tag already exists.
     */
    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }
    
    /**
     * Removes a tag. Returns false if it wasn't there.
     */
    public boolean removeTag(Tag tag) {
        return tags.remove(tag);
    }
    
    /**
     * Checks if the photo has a specific tag.
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

