package photos.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Album class. Just a collection of photos with a name. Can calculate 
 * date ranges and photo counts for display.
 * 
 * @author Photos Team
 */
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private List<Photo> photos;
    
    /**
     * Creates a new album with a name.
     */
    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }
    
    /**
     * Returns the album name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the album name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns all photos in the album.
     */
    public List<Photo> getPhotos() {
        return photos;
    }
    
    /**
     * Adds a photo. Returns false if it's already in the album.
     */
    public boolean addPhoto(Photo photo) {
        if (photos.contains(photo)) {
            return false;
        }
        return photos.add(photo);
    }
    
    /**
     * Removes a photo from the album.
     */
    public boolean removePhoto(Photo photo) {
        return photos.remove(photo);
    }
    
    /**
     * Returns how many photos are in the album.
     */
    public int getPhotoCount() {
        return photos.size();
    }
    
    /**
     * Finds the earliest date from all photos. Returns null if empty.
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
     * Finds the latest date from all photos. Returns null if empty.
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

