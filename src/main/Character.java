package main;

import java.util.List;
import java.util.ArrayList;

public class Character {
    private String name;
    private int location;
    private List<String> inventory;
    private String requiredItem;

    public Character(String name, int location, List<String> characterInventory, String requiredItem) {
        this.name = name;
        this.location = location;
        this.inventory = new ArrayList<>(characterInventory);
        this.requiredItem = requiredItem;
    }

    public String getName() {
        return this.name;
    }

    public int getLocation() {
        return this.location;
    }

    public List<String> getInventory() {
        return this.inventory;
    }

    public String getRequiredItem() {
        return this.requiredItem;
    }

    public void addItemToInventory(String item) {
        this.inventory.add(item);
    }
}
