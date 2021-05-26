package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Items.Item;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    Inventory inventory;
    private String name;
    private int shieldDuration = 0;
    private String playerState = "player";
    private boolean isInvisible = false;

    public Player(Cell cell) {
        super(cell);
        inventory = new Inventory();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getTileName() {
        return playerState;
    }

    public void setTileName(String tile) {
        playerState = tile;
    }

    public void setisInvisible(boolean state){
        isInvisible = state;
    }
    public boolean getisInvisible(){
        return isInvisible;
    }

    public int getShieldDuration() {
        return shieldDuration;
    }

    public void setShieldDuration(int amount) {
        shieldDuration += amount;
        if (shieldDuration < 0) {
            shieldDuration = 0;
        }else if (shieldDuration > 15){
            shieldDuration = 15;
        }
    }

    public static class Inventory {
        private Map<Item, String> inventory = new HashMap<>();

        public void addItem(Item item) {
            inventory.put(item, String.valueOf(item.getValue()));
        }

        public void removeItem(Item item) {
            inventory.remove(item);
        }

        public Map<Item, String> getItems() {
            return inventory;
        }
    }
}
