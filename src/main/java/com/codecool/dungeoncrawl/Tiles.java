package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);

    public static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;

        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }

    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall_vertical", new Tile(18, 1));
        tileMap.put("wall_vertical_left", new Tile(20, 1));
        tileMap.put("wall_down", new Tile(19, 2));
        tileMap.put("wall_connector_1", new Tile(19, 4));
        tileMap.put("wall_connector_2", new Tile(18, 4));
        tileMap.put("wall_connector_3", new Tile(19, 3));
        tileMap.put("wall_connector_4", new Tile(18, 3));
        tileMap.put("wall_bottom_right_corner", new Tile(20, 2));
        tileMap.put("wall_bottom_left_corner", new Tile(18, 2));
        tileMap.put("wall_upper_right_corner", new Tile(20, 0));
        tileMap.put("wall_upper_left_corner", new Tile(18, 0));
        tileMap.put("wall", new Tile(19, 0));
        tileMap.put("floor", new Tile(19, 1));
        //entities
        tileMap.put("player", new Tile(26, 0));
        tileMap.put("player_invisible", new Tile(24, 7));
        tileMap.put("player_no_shield", new Tile(25, 0));
        tileMap.put("player_max_shield", new Tile(31, 0));
        tileMap.put("player_medium_shield", new Tile(28, 0));
        tileMap.put("player_low_shield", new Tile(27, 0));
        tileMap.put("player_untouchable", new Tile(27, 3));
        tileMap.put("player_king", new Tile(31, 6));
        tileMap.put("skeleton", new Tile(29, 6));
        //healing items
        tileMap.put("Carrot", new Tile(18, 30));
        tileMap.put("Apple", new Tile(15, 29));
        tileMap.put("Steak", new Tile(16, 28));
        // Weapons \\ FOR TEST PURPOSE IF THEY CAN SHOW ON THE MAP! \\
        //tileMap.put("Sword", new Tile(30,1));
        tileMap.put("Dagger", new Tile(0,28));
        tileMap.put("Axe", new Tile(3,27));
        tileMap.put("Bow with arrow", new Tile(5,28));
        tileMap.put("Long Sword", new Tile(0,30));
        tileMap.put("Sceptre", new Tile(1,27));
        //shielding items
        tileMap.put("Kite", new Tile(8, 24));
        tileMap.put("Heater", new Tile(7, 26));
        tileMap.put("Pavise", new Tile(8, 26));
        //Interactive
        tileMap.put("Chest_closed", new Tile(8, 6));
        tileMap.put("Chest_open", new Tile(9, 6));
        tileMap.put("Torch", new Tile(10, 25));
        tileMap.put("Door", new Tile(17,17));
        //Decoration
        tileMap.put("Water_base", new Tile(8, 5));

        tileMap.put("Tree", new Tile(4, 1));
        tileMap.put("Tree_2", new Tile(3, 1));
        tileMap.put("Tree_3", new Tile(5, 1));

        tileMap.put("Grass", new Tile(7, 0));
        tileMap.put("Grass_2", new Tile(0, 2));
        tileMap.put("Grass_3", new Tile(13, 6));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y,double colr) {
        ColorAdjust clr = new ColorAdjust();
        Tile tile = tileMap.get(d.getTileName());
        clr.setBrightness(colr);
        context.setEffect(clr);
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);

    }
}
