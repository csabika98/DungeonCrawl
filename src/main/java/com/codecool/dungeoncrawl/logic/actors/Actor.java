package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 5;
    private int damage = 3;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy, boolean invisibleState) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (invisibleState) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else {
            if (isNotWall(nextCell) && isNotActor(nextCell)){
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public boolean isNotItem(Cell nextCell) {
        return nextCell.getItem() == null;
    }


    public boolean isNotActor(Cell nextCell) {
        return nextCell.getActor() == null;
    }


    public boolean isNotWall(Cell nextCell) {
        return nextCell.getTileName().equals("floor") || nextCell.getTileName().equals("empty") || nextCell.getTileName().equals("Door");
    }

    public int getHealth() {
        return health;
    }

    public void loadHealt(int h){
        health = h;
    }

    public void setHealt(int amount) {
        health += amount;
        if (health > 10) {
            health = 10;
        }


    }

    public void setDamageHealth(int damage){
        health -= damage;
    }



    public int getDamage() {
        return damage;
    }
    public void loadDagame(int d){
        damage = d;
    }

    public void setDamage(int amount) {
        damage += amount;
        if (damage > 15) {
            damage = 15;
        }
    }

    public Cell getCell() {
        return cell;
    }


    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public abstract String getName();
}
