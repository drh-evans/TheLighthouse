package main;

public class Character {
    private String name;
    private int location;
    private String hasItem;
    private String requiredItem;

    public Character(String name, int location, String hasItem, String requiredItem) {
        this.name = name;
        this.location = location;
        this.hasItem = hasItem;
        this.requiredItem = requiredItem;
    }

    public String getName() {
        return this.name;
    }

    public int getLocation() {
        return this.location;
    }

    public String getHasItem() {
        return this.hasItem;
    }

    public void setHasItem(String item) {
        this.hasItem = item;
    }

    public String getRequiredItem() {
        return this.requiredItem;
    }
}
