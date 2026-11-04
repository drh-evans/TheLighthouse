package main;

public class Character {
    private String name;
    private int location;
    private String[] inventory;
    private String requiredItem;

    public Character(String name, int location, String[] characterInventory, String requiredItem) {
        this.name = name;
        this.location = location;
        this.inventory = characterInventory;
        this.requiredItem = requiredItem;
    }

    public String getName() {
        return this.name;
    }

    public int getLocation() {
        return this.location;
    }

    public String[] getInventory() {
        return this.inventory;
    }

    public String getRequiredItem() {
        return this.requiredItem;
    }
}
