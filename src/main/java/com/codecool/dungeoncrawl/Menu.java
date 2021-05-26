package com.codecool.dungeoncrawl;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Menu extends Application {

    private VBox menuBox;
    private int currentItem = 0;

    public int getCurrentItem() {
        return currentItem;
    }

    public VBox getMenuBox() {
        return menuBox;
    }
    private void menuKey(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            if (currentItem > 0) {
                getMenuItem(currentItem).setActive(false);
                getMenuItem(--currentItem).setActive(true);
                getMenuItem(currentItem);
            }
        }

        if (event.getCode() == KeyCode.DOWN) {
            if (currentItem < menuBox.getChildren().size() - 1) {
                getMenuItem(currentItem).setActive(false);
                getMenuItem(++currentItem).setActive(true);
                getMenuItem(currentItem);
            }
        }

        if (event.getCode() == KeyCode.ENTER) {
            MenuItem item = getMenuItem(currentItem);
            //read the menu text, 1.getteer for the text object 2.for reading the text object as string format
            switch (item.getText().getText()) {
                case "New game":
                    System.out.println("new game");
                    //TODO : switch scene to new game
                    break;
                case "Load game":
                    System.out.println("load game");
                    //TODO : switch scene to loaded game
                    break;
                case "Exit":
                    System.exit(-1);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
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

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        stage.setScene(scene);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        scene.setOnKeyPressed(this::menuKey);
        stage.setTitle("Dungeon Crawl");
        stage.show();
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
}