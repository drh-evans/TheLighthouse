package main;

/**
 * Represents an item in the game.
 */
public class Item {
    private String name;
    private String description;
    private int location;

    /**
     * Constructs an Item with the given name, description, and location.
     * @param name
     * @param description
     * @param location
     */
    public Item(String name, String description, int location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    /**
     * Sets the location of the item.
     * @param location
     */
    public void setLocation(int location) {
        this.location = location;
    } 
    
    /**
     * Returns the name of the item.
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of the item.
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the location of the item.
     * @return
     */
    public int getLocation() {
        return this.location;
    }
}
