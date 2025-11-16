package photos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the photo application.
 * A user has a username, optional password, and a list of albums.
 * 
 * @author Photos Team
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private List<Album> albums;
    
    /**
     * Constructs a new User with the specified username.
     * 
     * @param username the username
     */
    public User(String username) {
        this.username = username;
        this.password = null;
        this.albums = new ArrayList<>();
    }
    
    /**
     * Constructs a new User with the specified username and password.
     * 
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.albums = new ArrayList<>();
    }
    
    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the password.
     * 
     * @return the password, or null if no password is set
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Gets all albums belonging to this user.
     * 
     * @return a list of albums
     */
    public List<Album> getAlbums() {
        return albums;
    }
    
    /**
     * Adds an album to this user.
     * 
     * @param album the album to add
     * @return true if the album was added, false if an album with the same name already exists
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
     * Removes an album from this user.
     * 
     * @param album the album to remove
     * @return true if the album was removed, false if it didn't exist
     */
    public boolean removeAlbum(Album album) {
        return albums.remove(album);
    }
    
    /**
     * Finds an album by name.
     * 
     * @param albumName the name of the album to find
     * @return the album if found, null otherwise
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
     * Checks if this user has an album with the specified name.
     * 
     * @param albumName the album name to check
     * @return true if the user has an album with this name
     */
    public boolean hasAlbum(String albumName) {
        return getAlbumByName(albumName) != null;
    }
}

