package com.example.board_java.br.com.service;

import com.example.board_java.br.com.persistance.dao.BoardColumnDAO;
import com.example.board_java.br.com.persistance.dao.BoardDAO;
import com.example.board_java.br.com.persistance.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {
    private final Connection connection;

    public Optional<BoardEntity> findbBID(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);

        if(optional.isPresent()){
            var entity =  optional.get();
            entity.setBoardColumns(BoardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
