package com.codecool.dungeoncrawl;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.Blend;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Menu {

    private VBox menuBox;
    private int currentItem = 0;

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
        ContentFrame frame = new ContentFrame(createIconContent());

        HBox hbox = new HBox(15, frame);

        hbox.setTranslateX(350);
        hbox.setTranslateY(50);

        MenuItem itemExit = new MenuItem("Exit");

        menuBox = new VBox(10,
                new MenuItem("New game"),
                new MenuItem("Load game"),
                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(255);
        menuBox.setTranslateY(300);

        Text about = new Text("Dungeon Crawler");
        about.setTranslateX(50);
        about.setTranslateY(500);
        about.setFill(Color.WHITE);
        about.setFont(Font.loadFont(Objects.requireNonNull(App.class.getResource("/fonts/Minecraft.ttf")).toExternalForm(), 0));
        about.setOpacity(0.4);
        about.setStyle("-fx-font-size: 35");

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bgBlack,bg, hbox, menuBox, about);
        return root;
    }

    private Node createIconContent() {
        Image icon = new Image("icon.png");
        ImageView imageView = new ImageView(icon);

        ScaleTransition st = new ScaleTransition(Duration.millis(2000), imageView);
        st.setByX(.3);
        st.setByY(.3);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.setAutoReverse(true);

        st.play();

        return imageView;
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

        public MenuItem(String name) {
            super(40);
            setAlignment(Pos.CENTER);

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

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.WHITE : Color.GREY);
        }
    }

    private static class TriCircle extends Parent {
        public TriCircle() {
            Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
            shape1.setFill(Color.WHITE);

            Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
            shape2.setFill(Color.WHITE);
            shape2.setTranslateX(5);

            Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
            shape3.setFill(Color.WHITE);
            shape3.setTranslateX(2.5);
            shape3.setTranslateY(-5);

            getChildren().addAll(shape1, shape2, shape3);

            setEffect(new GaussianBlur(2));
        }
    }
}
