package photos.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an album containing photos.
 * An album has a name and a list of photos.
 * 
 * @author Photos Team
 */
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private List<Photo> photos;
    
    /**
     * Constructs a new Album with the specified name.
     * 
     * @param name the name of the album
     */
    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }
    
    /**
     * Gets the name of the album.
     * 
     * @return the album name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the album.
     * 
     * @param name the album name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets all photos in this album.
     * 
     * @return a list of photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }
    
    /**
     * Adds a photo to this album.
     * 
     * @param photo the photo to add
     * @return true if the photo was added, false if it already exists in the album
     */
    public boolean addPhoto(Photo photo) {
        if (photos.contains(photo)) {
            return false;
        }
        return photos.add(photo);
    }
    
    /**
     * Removes a photo from this album.
     * 
     * @param photo the photo to remove
     * @return true if the photo was removed, false if it didn't exist
     */
    public boolean removePhoto(Photo photo) {
        return photos.remove(photo);
    }
    
    /**
     * Gets the number of photos in this album.
     * 
     * @return the number of photos
     */
    public int getPhotoCount() {
        return photos.size();
    }
    
    /**
     * Gets the earliest date of photos in this album.
     * 
     * @return the earliest date, or null if the album is empty
     */
    public LocalDateTime getEarliestDate() {
        if (photos.isEmpty()) {
            return null;
        }
        LocalDateTime earliest = photos.get(0).getDateTaken();
        for (Photo photo : photos) {
            if (photo.getDateTaken().isBefore(earliest)) {
                earliest = photo.getDateTaken();
            }
        }
        return earliest;
    }
    
    /**
     * Gets the latest date of photos in this album.
     * 
     * @return the latest date, or null if the album is empty
     */
    public LocalDateTime getLatestDate() {
        if (photos.isEmpty()) {
            return null;
        }
        LocalDateTime latest = photos.get(0).getDateTaken();
        for (Photo photo : photos) {
            if (photo.getDateTaken().isAfter(latest)) {
                latest = photo.getDateTaken();
            }
        }
        return latest;
    }
}

