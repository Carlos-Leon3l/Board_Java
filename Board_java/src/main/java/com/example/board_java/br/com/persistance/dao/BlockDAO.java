package com.example.board_java.br.com.persistance.dao;

import com.example.board_java.br.com.persistance.converter.OffSetDateTimeConverter;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@AllArgsConstructor
public class BlockDAO {

    private Connection connection;

    public void block(final String reason, final Long cardId) throws SQLException {
        var sql = "INSERT INTO BLOCKS (block_reason, block_at, card_id) VALUES (?,?,?)";
        try(var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i++, reason);
            statement.setTimestamp(i++, OffSetDateTimeConverter.toTimestamp(OffsetDateTime.now()));
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public void unblock(final String reason, final Long cardId) throws SQLException {
        var sql = "UPDATE BLOCKS SET unblock_at = ?, unblock_reason = ?  WHERE card_id = ? AND unblock_reason IS NULL ";
        try(var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setTimestamp(i++, OffSetDateTimeConverter.toTimestamp(OffsetDateTime.now()));
            statement.setString(i++, reason);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }
}
