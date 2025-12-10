package photos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User class. Each user has a username, optional password, and their own 
 * collection of albums. Users can't have duplicate album names.
 * 
 * @author Photos Team
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private List<Album> albums;
    
    /**
     * Creates a user with just a username (no password).
     */
    public User(String username) {
        this.username = username;
        this.password = null;
        this.albums = new ArrayList<>();
    }
    
    /**
     * Creates a user with username and password.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.albums = new ArrayList<>();
    }
    
    /**
     * Returns the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username.
     * 
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Returns the password, or null if there isn't one.
     * 
     * @return the password, or null if no password is set
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Returns all albums for this user.
     * 
     * @return a list of all albums belonging to this user
     */
    public List<Album> getAlbums() {
        return albums;
    }
    
    /**
     * Adds an album to the user's collection. Returns false if there's already an album with that name.
     * 
     * @param album the album to add
     * @return true if the album was added, false if an album with that name already exists
     */
    public boolean addAlbum(Album album) {
        // Check for duplicate album names
        for (Album existingAlbum : albums) {
            if (existingAlbum.getName().equals(album.getName())) {
                return false;
            }
        }
        return albums.add(album);
    }
    
    /**
     * Removes an album from the user's collection.
     * 
     * @param album the album to remove
     * @return true if the album was removed, false if it wasn't in the collection
     */
    public boolean removeAlbum(Album album) {
        return albums.remove(album);
    }
    
    /**
     * Finds an album by its name. Returns null if not found.
     * 
     * @param albumName the name of the album to find
     * @return the album with the given name, or null if not found
     */
    public Album getAlbumByName(String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }
    
    /**
     * Checks if the user has an album with this name.
     * 
     * @param albumName the name to check for
     * @return true if the user has an album with this name, false otherwise
     */
    public boolean hasAlbum(String albumName) {
        return getAlbumByName(albumName) != null;
    }
}

