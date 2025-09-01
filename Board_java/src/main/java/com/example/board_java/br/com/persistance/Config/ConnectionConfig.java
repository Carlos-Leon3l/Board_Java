package com.example.board_java.br.com.persistance.Config;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionConfig {
    public static Connection getConnection() throws SQLException {
        var url = "jdbc:mysql://localhost/seu_database"; // coloque o nome do seu database aqui
        var user = "seu_nome"; // coloque o nome de usuario do mysql aqui
        var password = "sua_senha"; // coloquei a senha do seu banco de dados mysql aqui
        // FAÇA O MESMO EM liquibase.properties
        var connection = DriverManager.
                getConnection(url, user, password);
        System.out.println("conexao feita" + connection);
        connection.setAutoCommit(false);
        return connection;
    }
}

