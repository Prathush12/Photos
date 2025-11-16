package photos.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main application model that manages users and data persistence.
 * Handles serialization and deserialization of user data.
 * 
 * @author Photos Team
 */
public class PhotoApp implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.dat";
    
    private Map<String, User> users;
    private List<String> tagTypes;
    
    /**
     * Constructs a new PhotoApp instance.
     * Initializes the users map and default tag types.
     */
    public PhotoApp() {
        this.users = new HashMap<>();
        this.tagTypes = new ArrayList<>();
        initializeDefaultTagTypes();
    }
    
    /**
     * Initializes default tag types.
     */
    private void initializeDefaultTagTypes() {
        tagTypes.add("location");
        tagTypes.add("person");
    }
    
    /**
     * Gets all users.
     * 
     * @return a list of all users
     */
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
    
    /**
     * Gets a user by username.
     * 
     * @param username the username
     * @return the user if found, null otherwise
     */
    public User getUser(String username) {
        return users.get(username);
    }
    
    /**
     * Adds a new user.
     * 
     * @param user the user to add
     * @return true if the user was added, false if a user with the same username already exists
     */
    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }
    
    /**
     * Removes a user.
     * 
     * @param username the username of the user to remove
     * @return true if the user was removed, false if the user didn't exist
     */
    public boolean removeUser(String username) {
        if (username.equals("stock") || username.equals("admin")) {
            return false; // Cannot delete stock or admin
        }
        return users.remove(username) != null;
    }
    
    /**
     * Checks if a user exists.
     * 
     * @param username the username to check
     * @return true if the user exists
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
    
    /**
     * Gets all tag types.
     * 
     * @return a list of tag type names
     */
    public List<String> getTagTypes() {
        return new ArrayList<>(tagTypes);
    }
    
    /**
     * Adds a new tag type.
     * 
     * @param tagType the tag type to add
     * @return true if the tag type was added, false if it already exists
     */
    public boolean addTagType(String tagType) {
        if (tagTypes.contains(tagType)) {
            return false;
        }
        return tagTypes.add(tagType);
    }
    
    /**
     * Saves the application data to disk.
     * 
     * @throws IOException if an I/O error occurs
     */
    public void save() throws IOException {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(USERS_FILE))) {
            oos.writeObject(this);
        }
    }
    
    /**
     * Loads the application data from disk.
     * 
     * @return the loaded PhotoApp instance, or a new instance if no data exists
     */
    public static PhotoApp load() {
        File usersFile = new File(USERS_FILE);
        if (!usersFile.exists()) {
            PhotoApp app = new PhotoApp();
            app.initializeStockUser();
            return app;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(USERS_FILE))) {
            PhotoApp app = (PhotoApp) ois.readObject();
            // Ensure stock user exists
            if (!app.userExists("stock")) {
                app.initializeStockUser();
            }
            return app;
        } catch (IOException | ClassNotFoundException e) {
            // If loading fails, create a new app with stock user
            PhotoApp app = new PhotoApp();
            app.initializeStockUser();
            return app;
        }
    }
    
    /**
     * Initializes the stock user with stock photos.
     */
    private void initializeStockUser() {
        User stockUser = new User("stock", "stock");
        Album stockAlbum = new Album("stock");
        
        File stockDir = new File(DATA_DIR);
        if (stockDir.exists() && stockDir.isDirectory()) {
            File[] files = stockDir.listFiles((dir, name) -> {
                String lower = name.toLowerCase();
                return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || 
                       lower.endsWith(".png") || lower.endsWith(".gif") || 
                       lower.endsWith(".bmp");
            });
            
            if (files != null) {
                for (File file : files) {
                    Photo photo = new Photo(file.getAbsolutePath());
                    stockAlbum.addPhoto(photo);
                }
            }
        }
        
        stockUser.addAlbum(stockAlbum);
        users.put("stock", stockUser);
    }
}

