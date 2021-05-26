package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private Date date;
    private DataSource dataSource;

    GameStateDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public void add(GameState state) {
        this.date = new Date(System.currentTimeMillis());
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
