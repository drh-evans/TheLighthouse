package main;

public class Character {
    private String name;
    private int location;
    private String hasItem;
    private String requiredItem;
    private boolean hasReceivedRequiredItem;

    public Character(String name, int location, String hasItem, String requiredItem) {
        this.name = name;
        this.location = location;
        this.hasItem = hasItem;
        this.requiredItem = requiredItem;
        this.hasReceivedRequiredItem = false;
    }

    public boolean getHasReceivedRequiredItem() {
        return this.hasReceivedRequiredItem;
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
        if (item.equals(this.requiredItem)) {
            this.hasReceivedRequiredItem = true;
        }
    }

    public String getRequiredItem() {
        return this.requiredItem;
    }
}
