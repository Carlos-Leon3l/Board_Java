package com.example.board_java.br.com.service;

import com.example.board_java.br.com.dto.CardDetails;
import com.example.board_java.br.com.persistance.dao.BoardColumnDAO;
import com.example.board_java.br.com.persistance.dao.BoardDAO;
import com.example.board_java.br.com.persistance.dao.CardDAO;
import com.example.board_java.br.com.persistance.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardQueryService {

    private Connection connection;

    public Optional<CardDetails> findById(final Long id) throws SQLException {
        var dao = new CardDAO(connection);
        return  dao.findById(id);
    }
}
