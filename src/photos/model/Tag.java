package photos.model;

import java.io.Serializable;

/**
 * Represents a tag that can be associated with a photo.
 * A tag consists of a name and a value pair.
 * 
 * @author Photos Team
 */
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String value;
    
    /**
     * Constructs a new Tag with the specified name and value.
     * 
     * @param name the tag name (e.g., "location", "person")
     * @param value the tag value (e.g., "New Brunswick", "susan")
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
     * Gets the tag value.
     * 
     * @return the tag value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Sets the tag value.
     * 
     * @param value the tag value to set
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

