package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class ShieldingItem extends Item{
    private final String itemName;

    public ShieldingItem(String itemName, Cell cell){
        super(itemName, cell);
        this.itemName = itemName;
        switch (itemName) {
            case "Kite":
                super.setValue(2);
                break;
            case "Heater":
                super.setValue(4);
                break;
            case "Pavise":
                super.setValue(6);
                break;
        }
    }

    public ShieldingItem(String itemName){
        super(itemName);
        this.itemName = itemName;
        switch (itemName) {
            case "Kite":
                super.setValue(2);
                break;
            case "Heater":
                super.setValue(4);
                break;
            case "Pavise":
                super.setValue(6);
                break;
        }
    }

    @Override
    public String getTileName() {
        return itemName;
    }
}
