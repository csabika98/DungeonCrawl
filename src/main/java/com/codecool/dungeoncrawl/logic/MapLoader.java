package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Items.Chest;
import com.codecool.dungeoncrawl.logic.Items.HealingItem;
import com.codecool.dungeoncrawl.logic.Items.WeaponItem;
import com.codecool.dungeoncrawl.logic.Items.ShieldingItem;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

public class MapLoader {
    public static GameMap loadMap(String mapName) {
        Random rand = new Random();
        InputStream is = MapLoader.class.getResourceAsStream(mapName);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        int healthNum = scanner.nextInt();
        int shieldNum = scanner.nextInt();
        int weaponNum = scanner.nextInt();
        int chestNum = scanner.nextInt();

        int allItemNum = healthNum + shieldNum + weaponNum + chestNum;
        int pointNum = scanner.nextInt();
        final int[] itemsPlace = new Random().ints(0, pointNum).distinct().limit(allItemNum).toArray();
        ArrayList<Integer> itemsNumber = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,12));
        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        int pointCounter = 0;
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            if (rand.nextInt(100) < 25) {
                                if (rand.nextInt(3) == 0) {
                                    cell.setType(CellType.TREE);
                                    break;
                                } else if (rand.nextInt(3) == 2) {
                                    cell.setType(CellType.TREE_2);
                                    break;
                                } else if (rand.nextInt(3) == 1) {
                                    cell.setType(CellType.TREE_3);
                                    break;
                                }
                                break;
                            } else if (rand.nextInt(100) < 25) {
                                if (rand.nextInt(3) == 0) {
                                    cell.setType(CellType.GRASS);
                                    break;
                                } else if (rand.nextInt(3) == 2) {
                                    cell.setType(CellType.GRASS_2);
                                    break;
                                } else if (rand.nextInt(3) == 1) {
                                    cell.setType(CellType.GRASS_3);
                                    break;
                                }
                                break;
                            }
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case 'W':
                            cell.setType(CellType.WATER);
                            break;
                        case '}':
                            cell.setType(CellType.WALL_BOTTOM_RIGTH_CORNER);
                            break;
                        case '¤':
                            cell.setType(CellType.DOOR);
                            break;
                        case '{':
                            cell.setType(CellType.WALL_BOTTOM_LEFT_CORNER);
                            break;
                        case '*':
                            cell.setType(CellType.WALL_DOWN);
                            break;
                        case '<':
                            cell.setType(CellType.WALL_UPPER_LEFT_CORNER);
                            break;
                        case '>':
                            cell.setType(CellType.WALL_UPPER_RIGHT_CORNER);
                            break;
                        case '€':
                            cell.setType(CellType.WALL_CONNECTOR_3);
                            break;
                        case 'Đ':
                            cell.setType(CellType.WALL_CONNECTOR_4);
                            break;
                        case '/':
                            cell.setType(CellType.WALL_CONNECTOR_1);
                            break;
                        case 'Ł':
                            cell.setType(CellType.WALL_CONNECTOR_2);
                            break;
                        case '&':
                            cell.setType(CellType.WALL_VERTICAL);
                            break;
                        case '$':
                            cell.setType(CellType.WALL_VERTICAL_LEFT);
                            break;
                        case '.':
                            int currentPointCounter = pointCounter;
                            if (IntStream.of(itemsPlace).anyMatch(z -> z == currentPointCounter)) {
                                int nextItem = itemsNumber.get(rand.nextInt(itemsNumber.size()));
                                switch (nextItem) {
                                    case 1:
                                        cell.setType(CellType.FLOOR);
                                        new HealingItem("Apple", cell);
                                        break;
                                    case 2:
                                        cell.setType(CellType.FLOOR);
                                        new HealingItem("Carrot", cell);
                                        break;
                                    case 3:
                                        cell.setType(CellType.FLOOR);
                                        new HealingItem("Steak", cell);
                                        break;
                                    case 4:
                                        cell.setType(CellType.FLOOR);
                                        new ShieldingItem("Kite", cell);
                                        break;
                                    case 5:
                                        cell.setType(CellType.FLOOR);
                                        new ShieldingItem("Heater", cell);
                                        break;
                                    case 6:
                                        cell.setType(CellType.FLOOR);
                                        new ShieldingItem("Pavise", cell);
                                        break;
                                    case 7:
                                        cell.setType(CellType.FLOOR);
                                        new WeaponItem("Dagger", cell);
                                        break;
                                    case 8:
                                        cell.setType(CellType.FLOOR);
                                        new WeaponItem("Axe", cell);
                                        break;
                                    case 9:
                                        cell.setType(CellType.FLOOR);
                                        new WeaponItem("Bow with arrow", cell);
                                        break;
                                    case 10:
                                        cell.setType(CellType.FLOOR);
                                        new WeaponItem("Long Sword", cell);
                                        break;
                                    case 11:
                                        cell.setType(CellType.FLOOR);
                                        new WeaponItem("Sceptre", cell);
                                        break;
                                    case 12:
                                        cell.setType(CellType.FLOOR);
                                        new Chest("Chest_closed",cell);
                                        break;
                                }
                                itemsNumber.remove((Integer) nextItem);
                                pointCounter++;
                                break;
                            } else {
                                pointCounter++;
                                int randomN = rand.nextInt(100);
                                //random floor pattern
                                if (randomN < 50) {
                                    cell.setType(CellType.EMPTY);
                                    break;
                                }
                                cell.setType(CellType.FLOOR);
                                break;
                            }
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.addSkeleton(new Skeleton(cell));
//                            new Skeleton(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
