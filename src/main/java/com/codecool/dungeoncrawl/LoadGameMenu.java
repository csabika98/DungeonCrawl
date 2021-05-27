package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadGameMenu{
    GameDatabaseManager gdm = new GameDatabaseManager();
    List<PlayerModel> players = new ArrayList<>();

    private VBox menuBox;
    private int currentItem = 0;

    public void ClearList() {
        players.clear();
    }

    public PlayerModel Attributes(int id) {
        for (PlayerModel player : players) {
            if (player.getId() == id){
                return player;
            }
        }
        return null;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public VBox getMenuBox() {
        return menuBox;
    }


    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);

        Rectangle bg = new Rectangle(900, 600);
        //secondary rectangle to fade the background to black
        Rectangle bgBlack = new Rectangle(900,600);
        bgBlack.setFill(Color.BLACK);
        bgBlack.setBlendMode(BlendMode.MULTIPLY);
        //create a texture inside the rectangle and repeat it in a pattern
        Image texturePattern = new Image("bgPattern.png");
        ImagePattern bgPattern = new ImagePattern(texturePattern,10,10, 20, 20, false);
        bg.setFill(bgPattern);
        bg.setOpacity(0.2);

        MenuItem itemExit = new MenuItem("Back", 9999);

        menuBox = new VBox(10);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(220);
        menuBox.setTranslateY(50);

        try {
            gdm.setup();
            players.addAll(gdm.getPlayers());
        } catch (Exception e) {
            System.out.println(e);
        }
        for (PlayerModel player: players) {
            MenuItem save = new MenuItem(player.getPlayerName(),player.getId());
            menuBox.getChildren().add(save);
        }
        menuBox.getChildren().add(itemExit);


        Text about = new Text("Dungeon Crawler");
        about.setTranslateX(50);
        about.setTranslateY(500);
        about.setFill(Color.WHITE);
        about.setFont(Font.loadFont(Objects.requireNonNull(App.class.getResource("/fonts/Minecraft.ttf")).toExternalForm(), 0));
        about.setOpacity(0.4);
        about.setStyle("-fx-font-size: 35");

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bgBlack,bg, menuBox, about);
        return root;
    }

    public MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) {
            setAlignment(Pos.CENTER);

            Rectangle frame = new Rectangle(200, 200);
            frame.setArcWidth(25);
            frame.setArcHeight(25);
            frame.setStroke(Color.WHITESMOKE);

            getChildren().addAll(frame, content);
        }
    }

    public static class MenuItem extends HBox {
        private ImageView c1 = new ImageView(new Image("menu_left_arrow.png", 50, 50, false, false));
        private ImageView c2 = new ImageView(new Image("menu_right_arrow.png", 50, 50, false, false));
        private Text text;
        private int playerId;

        public MenuItem(String name,int id) {
            super(40);
            setAlignment(Pos.CENTER);

            playerId = id;
            text = new Text(name);
            //load custom font from font folder
            text.setFont(Font.loadFont(Objects.requireNonNull(App.class.getResource("/fonts/Minecraft.ttf")).toExternalForm(), 0));
            //custom css styling for font size
            text.setStyle("-fx-font-size: 40;");

            getChildren().addAll(c1, text, c2);
            setActive(false);
        }

        public Text getText() {
            //get current pointing button text object to read the text
            return text;
        }
        public int getPlayerId(){
            return playerId;
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.WHITE : Color.GREY);

        }
    }
}
