package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, shield,damage, x, y) VALUES (?, ?, ?,?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            System.out.println(player.getPlayerName());
            statement.setInt(2, player.getHp());
            System.out.println(player.getHp());
            statement.setInt(3, player.getShield());
            System.out.println(player.getShield());
            statement.setInt(4, player.getDamage());
            System.out.println(player.getDamage());
            statement.setInt(5, player.getX());
            System.out.println(player.getX());
            statement.setInt(6, player.getY());
            System.out.println(player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public List<PlayerModel> getAll() {
        List<PlayerModel> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                PlayerModel player = new PlayerModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7));
                result.add(player);
            }
            return result;
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while reading all players", throwables);
        }
    }
}
