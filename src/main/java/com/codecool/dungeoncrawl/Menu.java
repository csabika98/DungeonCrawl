package com.codecool.dungeoncrawl;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Menu {

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;
    private int currentItem = 0;

    private int messages = 0;

    public int getCurrentItem() {
        return currentItem;
    }

    public VBox getMenuBox() {
        return menuBox;
    }

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);

        Rectangle bg = new Rectangle(900, 600);

        ContentFrame frame1 = new ContentFrame(createLeftContent());
        ContentFrame frame2 = new ContentFrame(createMiddleContent());
        ContentFrame frame3 = new ContentFrame(createRightContent());

        HBox hbox = new HBox(15, frame1, frame2, frame3);
        hbox.setTranslateX(120);
        hbox.setTranslateY(50);

        MenuItem itemExit = new MenuItem("EXIT");

        menuBox = new VBox(10,
                new MenuItem("New game"),
                new MenuItem("Load game"),
                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(360);
        menuBox.setTranslateY(300);

        Text about = new Text("Dungeon Crawler");
        about.setTranslateX(50);
        about.setTranslateY(500);
        about.setFill(Color.WHITE);
        about.setFont(FONT);
        about.setOpacity(0.2);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bg, hbox, menuBox, about);
        return root;
    }

    private Node createLeftContent() {
        final Text inbox = new Text("You have " + messages + " new message(-s)");
        inbox.setFill(Color.WHITE);

        bgThread.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), inbox);
                tt.setToY(150);

                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), inbox);
                ft.setToValue(0);

                ParallelTransition pt = new ParallelTransition(tt, ft);
                pt.setOnFinished(e -> {
                    inbox.setTranslateY(-150);
                    inbox.setText("You have " + ++messages + " new message(-s)");

                    TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), inbox);
                    tt2.setToY(0);

                    FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5), inbox);
                    ft2.setToValue(1);

                    ParallelTransition pt2 = new ParallelTransition(tt2, ft2);
                    pt2.play();
                });
                pt.play();
            });
        }, 2, 5, TimeUnit.SECONDS);

        return inbox;
    }

    private Node createMiddleContent() {
        String title = "MKX Menu App";
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT);
            letter.setFill(Color.WHITE);
            letters.getChildren().add(letter);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2), letter);
            tt.setDelay(Duration.millis(i * 50));
            tt.setToY(-25);
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.play();
        }

        return letters;
    }

    private Node createRightContent() {
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
        private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
        private Text text;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(Font.loadFont("resources/fonts/Minecraft.ttf", 240));

            getChildren().addAll(c1, text, c2);
            setActive(false);
        }

        public Text getText() {
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