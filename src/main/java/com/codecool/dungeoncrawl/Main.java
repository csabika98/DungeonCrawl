package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Items.Chest;
import com.codecool.dungeoncrawl.logic.Items.HealingItem;
import com.codecool.dungeoncrawl.logic.Items.Item;
import com.codecool.dungeoncrawl.logic.Items.WeaponItem;
import com.codecool.dungeoncrawl.logic.MapLoader;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import com.codecool.dungeoncrawl.logic.Mapmanager;

import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
    public static boolean halfCtrlSPressed = false;
    Stage currentStage;
    GameMap map = MapLoader.loadMap("/map.txt",30,22);
    Menu menu = new Menu();
    LoadGameMenu load = new LoadGameMenu();

    ListView<String> inventory = new ListView();
    public static double colr;
    public boolean isLight = false;
    Canvas canvas = new Canvas(
            16 * Tiles.TILE_WIDTH,
            16 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label damageLabel = new Label();
    Label shieldLabel = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        //handle window closing by hand
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });


        Scene menuScene = new Scene(menu.createContent());
        //new scene for menu, to switch between the game and ui
        primaryStage.setScene(menuScene);
        updatePlayerState();
        refresh();

        menuScene.setOnKeyPressed(this::menuKey);
        currentStage = primaryStage;
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void menuKey(KeyEvent event) {
        int currentItem = menu.getCurrentItem();
        if (event.getCode() == KeyCode.UP) {
            if (currentItem > 0) {
                menu.getMenuItem(currentItem).setActive(false);
                menu.getMenuItem(--currentItem).setActive(true);
                menu.getMenuItem(currentItem);
                menu.setCurrentItem(currentItem);
            }
        }

        if (event.getCode() == KeyCode.DOWN) {
            if (currentItem < menu.getMenuBox().getChildren().size() - 1) {
                menu.getMenuItem(currentItem).setActive(false);
                menu.getMenuItem(++currentItem).setActive(true);
                menu.getMenuItem(currentItem);
                menu.setCurrentItem(currentItem);
            }
        }

        if (event.getCode() == KeyCode.ENTER) {
            Menu.MenuItem item = menu.getMenuItem(currentItem);
            //read the menu text, 1.getteer for the text object 2.for reading the text object as string format
            switch (item.getText().getText()) {
                case "New game":
                    currentStage.hide();
                    Stage newStage = new Stage();
                    newGame(newStage, null);
                    break;
                case "Load game":
                    currentStage.hide();
                    Stage loadStage = new Stage();
                    loadGame(loadStage);
                    break;
                case "Exit":
                    System.exit(-1);
                    break;
            }
        }
    }

    public void loadKey(KeyEvent event) {
        int currentItem = load.getCurrentItem();
        if (event.getCode() == KeyCode.UP) {
            if (currentItem > 0) {
                load.getMenuItem(currentItem).setActive(false);
                load.getMenuItem(--currentItem).setActive(true);
                load.getMenuItem(currentItem);
                load.setCurrentItem(currentItem);
            }
        }

        if (event.getCode() == KeyCode.DOWN) {
            if (currentItem < load.getMenuBox().getChildren().size() - 1) {
                load.getMenuItem(currentItem).setActive(false);
                load.getMenuItem(++currentItem).setActive(true);
                load.getMenuItem(currentItem);
                load.setCurrentItem(currentItem);
            }
        }

        if (event.getCode() == KeyCode.ENTER) {
            LoadGameMenu.MenuItem player = load.getMenuItem(currentItem);
            //read the menu text, 1.getteer for the text object 2.for reading the text object as string format
            if (player.getText().getText().equals("Back")) {
                currentStage.hide();
                Stage sceneMenu = new Stage();
                menuScene(sceneMenu);
            } else {
                currentStage.hide();
                Stage gameStage = new Stage();
                newGame(gameStage, player);
            }
        }
    }


    private void menuScene(Stage newStage) {
        currentStage = newStage;
        Scene menuScene = new Scene(menu.createContent());
        menuScene.setOnKeyPressed(this::menuKey);
        newStage.setScene(menuScene);
        newStage.show();
    }


    private void loadGame(Stage newStage) {
        currentStage = newStage;
        Scene loadScene = new Scene(load.createContent());
        loadScene.setOnKeyPressed(this::loadKey);
        newStage.setScene(loadScene);
        newStage.show();
    }

    private void newGame(Stage newStage, LoadGameMenu.MenuItem player) {

        if (player == null) {
            map = MapLoader.loadMap("/map.txt",30,22);
            textdialog(newStage);
        } else {
            int playerId = player.getPlayerId();
            PlayerModel playerAttribute = load.Attributes(playerId);
            loadMap(playerAttribute);
            loadPlayer(playerAttribute);
        }
        currentStage = newStage;
        GridPane ui = new GridPane();
//        for(Skeleton skel : map.getSkeletons()){
//            System.out.println(skel.getX());
//            System.out.println(skel.getY());
//        }
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Damage: "), 0, 1);
        ui.add(damageLabel, 1, 1);
        ui.add(new Label("Shield Durability: "), 0, 2);
        ui.add(shieldLabel, 1, 2);

        inventory.setPrefWidth(160);
        inventory.setMaxWidth(160);
        inventory.setPrefHeight(400);

        GridPane.setConstraints(inventory, 0, 3, 1, 1);
        ui.getChildren().add(inventory);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene gameScene = new Scene(borderPane);
        newStage.setScene(gameScene);
        gameScene.setOnKeyPressed(this::savekey);
        newStage.show();

    }

    private void loadMap(PlayerModel playerAttribute) {
        map = MapLoader.loadMap("/map.txt", playerAttribute.getX(),playerAttribute.getY());

    }

    private void loadPlayer(PlayerModel player) {
        map.getPlayer().setShieldDuration(player.getShield());
        map.getPlayer().loadHealt(player.getHp());
        map.getPlayer().loadDagame(player.getDamage());
        updatePlayerState();
        refresh();
    }

    public void savekey(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("W")) {
            map.getPlayer().move(0, -1, map.getPlayer().getisInvisible());
            refresh();
        }
        if (keyEvent.getCode().getName().equals("S")) {
            map.getPlayer().move(0, 1, map.getPlayer().getisInvisible());
            refresh();
        }
        if (keyEvent.getCode().getName().equals("A")) {
            map.getPlayer().move(-1, 0, map.getPlayer().getisInvisible());
            refresh();
        }
        if (keyEvent.getCode().getName().equals("D")) {
            map.getPlayer().move(1, 0, map.getPlayer().getisInvisible());
            refresh();
        }
        if (keyEvent.getCode().getName().equals("E")) {
            if (map.getPlayer().getCell().getItem() != null) {
                pickItem(map.getPlayer().getCell().getItem());
                updatePlayerState();
            } else {
                checkChest();
            }
        }
        if (keyEvent.getCode().getName().equals("Q")) {
            map.getPlayer().setisInvisible(!map.getPlayer().getisInvisible());
            isLight = !isLight;
            updatePlayerState();
            refresh();
        }
        if (keyEvent.getCode().getName().equals("R")) {
            checkEnemy();
            refresh();
        }
        if (keyEvent.getCode().getName().equals("Ctrl")) {
            halfCtrlSPressed = true;
        } else if (keyEvent.getCode().getName().equals("S") && halfCtrlSPressed) {
            halfCtrlSPressed = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you wanna save the game?");
            alert.setTitle("Saving Menu");
            alert.setHeaderText("Saving Menu");
            alert.getButtonTypes().add(ButtonType.CANCEL);
//            alert.getButtonTypes().add(ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);

            if (button == ButtonType.OK) {
//                System.out.println("Ok pressed");
                // SAVING THE GAME
                GameDatabaseManager gdm = new GameDatabaseManager();
                try {
                    gdm.setup();
                    Player p = map.getPlayer();
                    gdm.savePlayer(p);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("canceled");
            }

            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//            alert.show();
        }
    }


    private void checkEnemy() {
        Cell skeletonCell = null;
        Cell nextCellRight = map.getPlayer().getCell().getNeighbor(-1, 0);
        Cell nextCellLeft = map.getPlayer().getCell().getNeighbor(1, 0);
        Cell nextCellUp = map.getPlayer().getCell().getNeighbor(0, -1);
        Cell nextCellDown = map.getPlayer().getCell().getNeighbor(0, 1);

        if (nextCellDown.getActor() instanceof Actor) {
            skeletonCell = nextCellDown;
            attackEnemy(skeletonCell.getX(), skeletonCell.getY());

        } else if (nextCellUp.getActor() instanceof Actor) {
            skeletonCell = nextCellUp;
            attackEnemy(skeletonCell.getX(), skeletonCell.getY());

        } else if (nextCellLeft.getActor() instanceof Actor) {
            skeletonCell = nextCellLeft;
            attackEnemy(skeletonCell.getX(), skeletonCell.getY());
            ;
        } else if (nextCellRight.getActor() instanceof Actor) {
            skeletonCell = nextCellRight;
            attackEnemy(skeletonCell.getX(), skeletonCell.getY());


        }
    }


    private void attackEnemy(int x, int y) {
        map.getCell(x, y).getActor().setDamageHealth(map.getPlayer().getDamage());
        if (map.getCell(x, y).getActor().getHealth() <= 0) {
            map.getCell(x, y).setActor(null);

        }
    }

    public void textdialog(Stage s) {
        s.setTitle("creating textInput dialog");
        TilePane r = new TilePane();
        TextInputDialog td = new TextInputDialog("enter any text");
        td.setHeaderText("enter your name");
        Optional<String> result = td.showAndWait();
        result.ifPresent(string -> {
            map.getPlayer().setName(string);
        });
        s.show();


    }

    private void checkChest() {
        Cell chestCell = null;
        Cell nextCellRight = map.getPlayer().getCell().getNeighbor(-1, 0);
        Cell nextCellLeft = map.getPlayer().getCell().getNeighbor(1, 0);
        Cell nextCellUp = map.getPlayer().getCell().getNeighbor(0, -1);
        Cell nextCellDown = map.getPlayer().getCell().getNeighbor(0, 1);

        if (nextCellDown.getItem() instanceof Chest) {
            chestCell = nextCellDown;
            openChest(chestCell.getX(), chestCell.getY());

        } else if (nextCellUp.getItem() instanceof Chest) {
            chestCell = nextCellUp;
            openChest(chestCell.getX(), chestCell.getY());

        } else if (nextCellLeft.getItem() instanceof Chest) {
            chestCell = nextCellLeft;
            openChest(chestCell.getX(), chestCell.getY());
            ;
        } else if (nextCellRight.getItem() instanceof Chest) {
            chestCell = nextCellRight;
            openChest(chestCell.getX(), chestCell.getY());

        }
    }

    private boolean checkNextLevel() {
        return map.getPlayer().getCell().getTileName().equals("Door");
    }

    private void pickItem(Item item) {
        switch (item.getTileName()) {
            case "Pavise":
                map.getPlayer().setShieldDuration(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updatePlayerState();
                refresh();
                break;
            case "Heater":
                map.getPlayer().setShieldDuration(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updatePlayerState();
                refresh();
                break;
            case "Kite":
                map.getPlayer().setShieldDuration(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updatePlayerState();
                refresh();
                break;
            case "Carrot":
                map.getPlayer().setHealt(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updatePlayerState();
                refresh();
                break;
            case "Steak":
                map.getPlayer().setHealt(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updatePlayerState();
                refresh();
                break;
            case "Apple":
                map.getPlayer().setHealt(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updatePlayerState();
                refresh();
                break;
            case "Long Sword":
                map.getPlayer().getInventory().addItem(new WeaponItem("Long Sword"));
                map.getPlayer().setDamage(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updateInventory();
                updatePlayerState();
                refresh();
                break;
            case "Axe":
                map.getPlayer().getInventory().addItem(new WeaponItem("Axe"));
                map.getPlayer().setDamage(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updateInventory();
                updatePlayerState();
                refresh();
                break;
            case "Bow with arrow":
                map.getPlayer().getInventory().addItem(new WeaponItem("Bow with arrow"));
                map.getPlayer().setDamage(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updateInventory();
                updatePlayerState();
                refresh();
                break;
            case "Sceptre":
                map.getPlayer().getInventory().addItem(new WeaponItem("Sceptre"));
                map.getPlayer().setDamage(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updateInventory();
                updatePlayerState();
                refresh();
                break;
            case "Dagger":
                map.getPlayer().getInventory().addItem(new WeaponItem("Dagger"));
                map.getPlayer().setDamage(item.getValue());
                map.getPlayer().getCell().setItem(null);
                updateInventory();
                updatePlayerState();
                refresh();
                break;
        }
    }

    private void openChest(int x, int y) {
        map.getCell(x, y).setItem(null);
        map.getCell(x, y).setItem(new Chest("Chest_open", map.getCell(x, y)));
        map.getPlayer().getInventory().addItem(new HealingItem("apple"));
        updateInventory();
        refresh();

    }

    private void updatePlayerState() {
        String currentPlayerState;
        if (!map.getPlayer().getisInvisible()) {
            currentPlayerState = getShieldState();
        } else {
            currentPlayerState = "player_invisible";
        }
        map.getPlayer().setTileName(currentPlayerState);
    }

    private String getShieldState() {
        String currentState;
        if (map.getPlayer().getShieldDuration() == 0 && map.getPlayer().getDamage() < 6) {
            currentState = "player_no_shield";
        } else if (map.getPlayer().getShieldDuration() >= 2 && map.getPlayer().getShieldDuration() < 6) {
            currentState = "player_low_shield";
        } else if (map.getPlayer().getShieldDuration() >= 6 && map.getPlayer().getShieldDuration() < 12) {
            currentState = "player_medium_shield";
        } else if (map.getPlayer().getShieldDuration() >= 12 && map.getPlayer().getShieldDuration() < 15) {
            currentState = "player_max_shield";
        } else if (map.getPlayer().getShieldDuration() == 15 && map.getPlayer().getDamage() != 15) {
            currentState = "player_untouchable";
        } else if (map.getPlayer().getShieldDuration() == 15 && map.getPlayer().getDamage() == 15) {
            currentState = "player_king";
        } else {
            currentState = "player";
        }
        return currentState;
    }

    private void refresh() {
        if (checkNextLevel()) {
            Mapmanager mapManager = new Mapmanager();
            mapManager.setMap("/map2.txt");
            map = mapManager.getMap();
        }
        colr = -1.0;
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        List<Skeleton> skeletonList = map.getSkeletons();
        for (Skeleton skeleton : skeletonList) {
            if (skeleton.getHealth() == 5) {
                int skeletonX = skeleton.getX();
                int skeletonY = skeleton.getY();
                int moveX;
                int moveY;
                if (Math.abs(skeletonX - playerX) > Math.abs(skeletonY - playerY)) {
                    if (playerX > skeletonX) {
                        moveX = 1;
                    } else {
                        moveX = -1;
                    }
                    moveY = 0;
                } else {
                    if (playerY > skeletonY) {
                        moveY = 1;
                    } else {
                        moveY = -1;
                    }
                    moveX = 0;
                }
                skeleton.move(moveX, moveY, false);
            }
        }
        context.setFill(Color.rgb(71, 45, 60));
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (!isLight) {
                    try {
                        //try to check each cell and their neighbor if exist
                        Cell nextCell = cell.getNeighbor((x - playerX), (y - playerY));
                        //compare each other and subtract or add
                        cell.setBrightness(nextCell.getBrightness() + 0.13);
                        nextCell.setBrightness(cell.getBrightness() - 0.15);
                        colr = nextCell.getBrightness();
                    } catch (IndexOutOfBoundsException e) {
                        //oopss
                    }
                } else {
                    colr = 0;
                }
                if (cell.getActor() != null) {
                    if (cell.getActor() == map.getPlayer() && !isLight) {
                        colr += 0.1;
                    }
                    Tiles.drawTile(context, cell.getActor(), (x - playerX) + 8, (y - playerY) + 7, colr);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), (x - playerX) + 8, (y - playerY) + 7, colr);
                } else {
                    Tiles.drawTile(context, cell, (x - playerX) + 8, (y - playerY) + 7, colr);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        damageLabel.setText("" + map.getPlayer().getDamage());
        shieldLabel.setText("" + map.getPlayer().getShieldDuration());
    }

    private void updateInventory() {
        inventory.getItems().clear();
        for (Item item : map.getPlayer().getInventory().getItems().keySet()) {
            inventory.getItems().add(item.getTileName());
        }
    }
}
