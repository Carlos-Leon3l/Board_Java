package com.example.board_java.br.com.persistance.Config;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionConfig {
    public static Connection getConnection()
            throws SQLException {
        var url = "jdbc:mysql://localhost/board";
        var user = "root";
        var password = "carlos25";
        var connection = DriverManager.
                getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }
}

