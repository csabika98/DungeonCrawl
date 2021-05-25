package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealingItem extends Item {
    private final String itemName;

    public HealingItem(String itemName, Cell cell) {
        super(itemName, cell);
        this.itemName = itemName;
        switch (itemName) {
            case "Apple":
                super.setValue(2);
                break;
            case "Carrot":
                super.setValue(4);
                break;
            case "Steak":
                super.setValue(6);
                break;
        }
    }
    public HealingItem(String itemName) {
        super(itemName);
        this.itemName = itemName;
        switch (itemName) {
            case "Apple":
                super.setValue(2);
                break;
            case "Carrot":
                super.setValue(4);
                break;
            case "Steak":
                super.setValue(6);
                break;
        }
    }

    @Override
    public String getTileName() {
        return itemName;
    }
}
