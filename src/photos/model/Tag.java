package photos.model;

import java.io.Serializable;

/**
 * Tag class for photos. Basically just a name-value pair like "location"="Paris" 
 * or "person"="Alice". Used to organize and search photos.
 * 
 * @author Photos Team
 */
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String value;
    
    /**
     * Creates a new tag with a name and value.
     * 
     * @param name like "location" or "person"
     * @param value like "Paris" or "Alice"
     */
    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * Gets the tag name.
     * 
     * @return the tag name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the tag name.
     * 
     * @param name the tag name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the tag value.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Sets the tag value.
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tag tag = (Tag) obj;
        return name.equals(tag.name) && value.equals(tag.value);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() * 31 + value.hashCode();
    }
    
    @Override
    public String toString() {
        return name + "=" + value;
    }
}

