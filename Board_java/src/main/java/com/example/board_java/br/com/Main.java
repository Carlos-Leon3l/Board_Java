package com.example.board_java.br.com;

import com.example.board_java.br.com.UI.MainMenu;
import com.example.board_java.br.com.persistance.Config.ConnectionConfig;
import com.example.board_java.br.com.persistance.migration.MigrationStrategy;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException{
        try(var connection = ConnectionConfig.getConnection()) {
            new MigrationStrategy(connection).executeMigration();

        }
        new MainMenu().execute();
    }
}
