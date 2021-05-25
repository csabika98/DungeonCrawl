package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    CHEST("Chest"),
    FLOOR("floor"),
    WALL("wall"),
    WALL_DOWN("wall_down"),
    WALL_BOTTOM_RIGTH_CORNER("wall_bottom_right_corner"),
    WALL_BOTTOM_LEFT_CORNER("wall_bottom_left_corner"),
    WALL_UPPER_RIGHT_CORNER("wall_upper_right_corner"),
    WALL_CONNECTOR_1("wall_connector_1"),
    WALL_CONNECTOR_2("wall_connector_2"),
    WALL_CONNECTOR_3("wall_connector_3"),
    WALL_CONNECTOR_4("wall_connector_4"),
    WALL_UPPER_LEFT_CORNER("wall_upper_left_corner"),
    WALL_VERTICAL("wall_vertical"),
    WALL_VERTICAL_LEFT("wall_vertical_left"),
    TREE("Tree"),
    TREE_2("Tree_2"),
    TREE_3("Tree_3"),
    GRASS("Grass"),
    GRASS_2("Grass_2"),
    GRASS_3("Grass_3"),
    WATER("Water_base"),
    DOOR("Door");


    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
