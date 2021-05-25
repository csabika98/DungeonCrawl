package com.codecool.dungeoncrawl.logic.Items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private String itemName;
    private int value;

    public Item(String itemName, Cell cell) {
        this.itemName = itemName;
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}


