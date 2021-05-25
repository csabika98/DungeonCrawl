package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;

public class WeaponItem extends Item{
    private final String itemName;

  // CURRENT WEAPON TIER LIST \\ FEEL FREE TO EXPAND!

    // TODO: CAN BE MORE WEAPONS \\

    public WeaponItem(String itemName, Cell cell) {
        super(itemName, cell);
        this.itemName = itemName;
        switch(itemName){
            case "Dagger":
                super.setValue(2);  // I SUPPOSE SET VALUE EQUALS TO THE DEALT DMG ?? I THINK DMG SYSTEM IS MISSING!!! \\
                break;
            case "Axe":
                super.setValue(4);
                break;
            case "Long Sword":
                super.setValue(6);
                break;
            case "Bow with arrow":
                super.setValue(9);
                break;
            case "Sceptre":
                super.setValue(5);
                break;
        }

    }
    // SAME WITHOUT CELL \\
    public WeaponItem(String itemName) {
        super(itemName);
        this.itemName = itemName;
        switch(itemName){
            case "Dagger":
                super.setValue(2);
                break;
            case "Axe":
                super.setValue(4);
                break;
            case "Long Sword":
                super.setValue(6);
                break;
            case "Bow with arrow":
                super.setValue(9);
                break;
        }

    }



    @Override
    public String getTileName() {
        return itemName;
    }
}
