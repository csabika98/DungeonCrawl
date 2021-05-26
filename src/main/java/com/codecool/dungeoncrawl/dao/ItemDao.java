package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface ItemDao {
    void add(ItemModel item);
    void update(ItemModel player);
    ItemModel get(int id);
    List<ItemModel> getAll();
}
