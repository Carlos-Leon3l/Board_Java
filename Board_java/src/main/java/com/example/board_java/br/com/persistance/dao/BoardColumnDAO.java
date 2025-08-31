package com.example.board_java.br.com.persistance.dao;

import com.example.board_java.br.com.dto.BoardColumnDTO;
import com.example.board_java.br.com.persistance.entity.BoardColumnEntity;
import com.example.board_java.br.com.persistance.entity.BoardColumnKindEnum;
import com.example.board_java.br.com.persistance.entity.CardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO BOARDS_COLUMNS(name, `order`, kind, board_id) values (?,?,?,?)";
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
            if(statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardid) throws SQLException{
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id,name,`order` FROM BOARD_COLUMNS WHERE board_id = ? ORDER BY `order` ";
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardid);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(BoardColumnKindEnum.findById(resultSet.getString("kind")));
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long id) throws SQLException {
        List<BoardColumnDTO> dtos = new ArrayList<>();
        var sql = """
                SELECT bc.id,
                       bc.name,
                       bc.kind,
                (SELECT COUNT(c.id)
                    FROM CARDS c
                    WHERE c.board_column_id = bc.id) cards_amount
                FROM BOARDS_COLUMNS bc 
                WHERE board_id = ?
                ORDER BY `order`
                """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var dto = new BoardColumnDTO(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        BoardColumnKindEnum.findById(resultSet.getString("kind")),
                        resultSet.getInt("cards_amount"));

                dtos.add(dto);
            }
        }
        return dtos;
    }

    public Optional<BoardColumnEntity> findById(final Long boardId) throws SQLException {
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = """
        SELECT bc.name,
                bc.kind,
                c.id,
                c.title,
                c.description
        FROM BOARD_COLUMNS bc
        LEFT JOIN CARDS c
            ON c.board_column_id = bc.id
        WHERE bc.id = ?
        """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var entity = new BoardColumnEntity();
                entity.setName(resultSet.getString("bc.name"));
                entity.setKind(BoardColumnKindEnum.findById(resultSet.getString("bc.kind")));
                do {
                    if(Objects.isNull(resultSet.getString("c.title"))){
                        break;
                    }
                    var card = new CardEntity();
                    card.setId(resultSet.getLong("c.id"));
                    card.setTitle(resultSet.getString("c.name"));
                    card.setDescription(resultSet.getString("c.description"));
                    entity.getCards().add(card);

                } while (resultSet.next());
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }
}
