package com.codecool.dungeoncrawl.logic;

public class Mapmanager {
    private GameMap map;


    public Mapmanager(){
        setMap("/map.txt");
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(String mapName) {
        map = MapLoader.loadMap(mapName);
    }
}
