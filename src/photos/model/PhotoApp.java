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
 * Main app class. Keeps track of all users and handles saving/loading 
 * everything to disk using Java serialization. Also manages tag types.
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
     * Creates a new PhotoApp and sets up default tag types.
     */
    public PhotoApp() {
        this.users = new HashMap<>();
        this.tagTypes = new ArrayList<>();
        initializeDefaultTagTypes();
    }
    
    /**
     * Sets up the default tag types (location and person).
     */
    private void initializeDefaultTagTypes() {
        tagTypes.add("location");
        tagTypes.add("person");
    }
    
    /**
     * Returns all users.
     */
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
    
    /**
     * Gets a user by username. Returns null if not found.
     */
    public User getUser(String username) {
        return users.get(username);
    }
    
    /**
     * Adds a new user. Returns false if username already exists.
     */
    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }
    
    /**
     * Removes a user. Can't delete stock or admin.
     */
    public boolean removeUser(String username) {
        if (username.equals("stock") || username.equals("admin")) {
            return false; // Cannot delete stock or admin
        }
        return users.remove(username) != null;
    }
    
    /**
     * Checks if a user exists.
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
    
    /**
     * Returns all available tag types.
     */
    public List<String> getTagTypes() {
        return new ArrayList<>(tagTypes);
    }
    
    /**
     * Adds a new tag type. Returns false if it already exists.
     */
    public boolean addTagType(String tagType) {
        if (tagTypes.contains(tagType)) {
            return false;
        }
        return tagTypes.add(tagType);
    }
    
    /**
     * Saves all user data to disk.
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
     * Loads all user data from disk. Creates a new app with stock user if 
     * nothing exists yet.
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
            // Always refresh stock user to load any new photos
            app.initializeStockUser();
            return app;
        } catch (IOException | ClassNotFoundException e) {
            // If loading fails, create a new app with stock user
            PhotoApp app = new PhotoApp();
            app.initializeStockUser();
            return app;
        }
    }
    
    /**
     * Sets up the stock user and loads any photos from the data directory.
     */
    private void initializeStockUser() {
        User stockUser = new User("stock", "stock");
        Album stockAlbum = new Album("stock");
        
        // First try data/stock/ subdirectory, then fall back to data/ directory
        File[] searchDirs = {
            new File(DATA_DIR + File.separator + "stock"),
            new File(DATA_DIR)
        };
        
        for (File searchDir : searchDirs) {
            if (searchDir.exists() && searchDir.isDirectory()) {
                File[] files = searchDir.listFiles((dir, name) -> {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || 
                           lower.endsWith(".png") || lower.endsWith(".gif") || 
                           lower.endsWith(".bmp");
                });
                
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        Photo photo = new Photo(file.getAbsolutePath());
                        stockAlbum.addPhoto(photo);
                    }
                    break; // Found photos, no need to check other directories
                }
            }
        }
        
        stockUser.addAlbum(stockAlbum);
        users.put("stock", stockUser);
    }
}

