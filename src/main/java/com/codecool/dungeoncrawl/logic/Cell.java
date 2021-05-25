package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private GameMap gameMap;
    private double brightness;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.brightness = -1.0;
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public double getBrightness(){
        return brightness;
    }
    public void setBrightness(double brightness){
        if (brightness>=0.6){
            brightness = 0.6;
        }else if(brightness<=-1.0){
            brightness = -1.0;
        }
        this.brightness = brightness;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
