package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private int shield;
    private int damage;

    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.damage = player.getDamage();
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.shield = player.getShieldDuration();
        this.hp = player.getHealth();

    }

    public PlayerModel(int id, String playerName, int hp, int shield,int damage ,int x,int y) {
        super.id = id;
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.shield = shield;
        this.hp = hp;
        this.damage = damage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage(){
        return damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
