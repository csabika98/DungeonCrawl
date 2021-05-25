package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Chest extends Item {
    private String itemName;

    public Chest(String itemName, Cell cell) {
        super(itemName, cell);
        this.itemName = itemName;
    }

    @Override
    public String getTileName() {
        return itemName;
    }
}
